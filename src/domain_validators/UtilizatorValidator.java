package domain_validators;

import domain.Utilizator;
import exceptions.ValidationException;

/**
 * Clasa responsabilă cu validarea datelor pentru un obiect de tip Utilizator.
 */
public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator u) throws ValidationException {
        if(u.getFirstName()==null || u.getFirstName().trim().isEmpty()) {
            throw new ValidationException("Prenumele utilizatorului nu poate fi vid!");
        }

        if(u.getLastName()==null || u.getLastName().trim().isEmpty()) {
            throw new ValidationException("Prenumele utilizatorului nu poate fi vid!");
        }

    }
}
