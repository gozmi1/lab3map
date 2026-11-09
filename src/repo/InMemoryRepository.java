package repo;

import domain.Entity;
import domain_validators.Validator;
import exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementare a interfeței Repository care stochează datele local, în memoria RAM, folosind un Map.
 * @param <ID> Tipul identificatorului.
 * @param <E> Tipul entității.
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Validator<E> validator;
    private Map<ID, E> entities;

    /**
     * @param validator Validatorul specific entității gestionate de repository.
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap<>();
    }

    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if (entity == null) {
            throw new IllegalArgumentException("Entitatea nu poate fi null!");
        }
        validator.validate(entity);

        if (entities.containsKey(entity.getID())) {
            return entity;
        } else {
            entities.put(entity.getID(), entity);
            return null;
        }
    }

    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID-ul nu poate fi null!");
        }
        return entities.remove(id);
    }

    @Override
    public E update(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entitatea nu poate fi null!");
        }
        validator.validate(entity);

        if (entities.containsKey(entity.getID())) {
            entities.put(entity.getID(), entity);
            return null;
        }
        return entity;
    }
}
