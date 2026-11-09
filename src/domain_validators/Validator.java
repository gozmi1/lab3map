package domain_validators;

import exceptions.ValidationException;
/**
 * Interfață generică pentru implementarea șablonului de proiectare Strategy (Strategy Pattern) în validare.
 * @param <T> Tipul entității care urmează să fie validată.
 */
public interface Validator<T> {
    /**
     * Validează atributele entității date.
     * @param entity Entitatea care trebuie validată.
     * @throws ValidationException dacă entitatea nu îndeplinește regulile de validare.
     */
    void validate(T entity) throws ValidationException;
}
