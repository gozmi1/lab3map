package domain_validators;

import domain.Prietenie;
import exceptions.ValidationException;

/**
 * Clasa responsabilă cu validarea regulilor de business pentru o entitate de tip Prietenie.
 */
public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if (entity.getIdUser1().equals(entity.getIdUser2())) {
            throw new ValidationException("ID-urile nu pot fi identice. Un utilizator nu poate fi prieten cu el însuși!");
        }
    }
}