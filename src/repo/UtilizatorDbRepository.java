package repo;


import domain.Utilizator;
import domain_validators.Validator;
import exceptions.ValidationException;

/**
 * Schelet pentru persistența în baza de date (Laboratorul 4).
 */
public class UtilizatorDbRepository implements Repository<Long, Utilizator> {

    public UtilizatorDbRepository(Validator<Utilizator> validator, String url, String user, String password) {
        // Aici se va inițializa conexiunea JDBC în Lab 4
    }

    @Override
    public Utilizator findOne(Long id) { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Iterable<Utilizator> findAll() { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Utilizator save(Utilizator entity) throws ValidationException { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Utilizator delete(Long id) { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Utilizator update(Utilizator entity) { throw new UnsupportedOperationException("Not implemented yet!"); }
}
