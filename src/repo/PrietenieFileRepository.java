package repo;

import domain.Prietenie;
import domain.Tuple;
import domain_validators.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Repository for reading and writing Prietenie entities to a text file.
 */
public class PrietenieFileRepository extends AbstractFileRepository<Tuple<Long, Long>, Prietenie> {

    public PrietenieFileRepository(Validator<Prietenie> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Prietenie extractEntity(String line) {
        String[] attributes = line.split(";", -1);
        if (attributes.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Linia nu conține exact 3 atribute separate prin punct și virgulă.");
        }

        try {
            Long id1 = Long.parseLong(attributes[0]);
            Long id2 = Long.parseLong(attributes[1]);
            LocalDateTime date = LocalDateTime.parse(attributes[2]);
            return new Prietenie(id1, id2, date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data nu este în format ISO corect (ex: 2026-10-19T10:30).");
        }
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getIdUser1() + ";" + entity.getIdUser2() + ";" + entity.getDate().toString();
    }
}
