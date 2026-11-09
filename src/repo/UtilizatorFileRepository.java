package repo;
import domain.Utilizator;
import domain_validators.Validator;

/**
 * Repository for reading and writing Utilizator entities to a text file.
 */
public class UtilizatorFileRepository extends AbstractFileRepository<Long, Utilizator> {

    public UtilizatorFileRepository(Validator<Utilizator> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    protected Utilizator extractEntity(String line) {
        String[] attributes = line.split(";", -1);
        if (attributes.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Linia nu conține exact 3 atribute separate prin punct și virgulă.");
        }
        Long id = Long.parseLong(attributes[0]);
        String prenume = attributes[1];
        String nume = attributes[2];

        Utilizator u = new Utilizator(prenume, nume);
        u.setID(id);
        return u;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getID() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}
