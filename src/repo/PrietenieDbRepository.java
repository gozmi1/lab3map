package repo;

import domain.Prietenie;
import domain.Tuple;
import domain_validators.Validator;
import exceptions.ValidationException;

/**
 * Schelet pentru persistența în baza de date (Laboratorul 4).
 */
public class PrietenieDbRepository implements Repository<Tuple<Long, Long>, Prietenie> {

    public PrietenieDbRepository(Validator<Prietenie> validator, String url, String user, String password) {
        // Aici se va inițializa conexiunea JDBC în Lab 4
    }

    @Override
    public Prietenie findOne(Tuple<Long, Long> id) { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Iterable<Prietenie> findAll() { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Prietenie save(Prietenie entity) throws ValidationException { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Prietenie delete(Tuple<Long, Long> id) { throw new UnsupportedOperationException("Not implemented yet!"); }

    @Override
    public Prietenie update(Prietenie entity) { throw new UnsupportedOperationException("Not implemented yet!"); }
}
