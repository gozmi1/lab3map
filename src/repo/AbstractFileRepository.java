package repo;

import domain.Entity;
import domain_validators.Validator;
import exceptions.RepositoryException;
import exceptions.ValidationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Abstract generic repository that persists data to a text file.
 * Extends InMemoryRepository and uses the Template Method pattern for entity extraction/creation.
 *
 * @param <ID> the type of the entity's identifier.
 * @param <E> the type of the entity, extending Entity.
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String filename;

    /**
     * Constructs the file repository and loads data from the specified file.
     * @param validator the validator used for entities.
     * @param filename the path to the data file.
     */
    public AbstractFileRepository(Validator<E> validator, String filename) {
        super(validator);
        this.filename = filename;
        loadData();
    }

    /**
     * Extracts an entity from a formatted string line.
     * @param line the line read from the file.
     * @return the newly created entity.
     */
    protected abstract E extractEntity(String line);

    /**
     * Formats an entity into a string for file storage.
     * @param entity the entity to format.
     * @return the formatted string.
     */
    protected abstract String createEntityAsString(E entity);

    /**
     * Loads all valid entities from the file into the memory cache.
     * Corrupted lines are ignored and reported to the standard output.
     */
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    E entity = extractEntity(line);
                    super.save(entity);
                } catch (ValidationException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Warning: Corrupted line ignored in file " + filename + " at line " + lineNumber + ". Reason: " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Warning: File " + filename + " not found. A new file will be created upon first save.");
        } catch (IOException e) {
            throw new RepositoryException("Error reading from file: " + filename, e);
        }
    }

    /**
     * Writes all entities from memory back to the file.
     */
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (E entity : super.findAll()) {
                writer.write(createEntityAsString(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error writing to file: " + filename, e);
        }
    }

    @Override
    public E save(E entity) throws ValidationException {
        E e = super.save(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        if (e != null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if (e == null) {
            writeToFile();
        }
        return e;
    }
}
