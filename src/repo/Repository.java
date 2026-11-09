package repo;

import domain.Entity;
import exceptions.ValidationException;

/**
 * Interfața care definește operațiunile standard CRUD (Create, Read, Update, Delete) pentru persistența datelor.
 * @param <ID> Tipul identificatorului entității.
 * @param <E> Tipul entității gestionate de repository.
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Caută o entitate după ID.
     * @param id ID-ul entității căutate.
     * @return Entitatea găsită sau null dacă nu există.
     */
    E findOne(ID id);

    /**
     * Returnează toate entitățile stocate.
     * @return Un obiect iterabil conținând toate entitățile.
     */
    Iterable<E> findAll();

    /**
     * Salvează o entitate în dicționar.
     * @param entity Entitatea care trebuie salvată.
     * @return null dacă salvarea a reușit, sau entitatea dacă aceasta există deja.
     * @throws ValidationException dacă entitatea este invalidă.
     */
    E save(E entity) throws ValidationException;

    /**
     * Șterge o entitate pe baza ID-ului.
     * @param id ID-ul entității care trebuie ștearsă.
     * @return Entitatea ștearsă sau null dacă ID-ul nu există.
     */
    E delete(ID id);

    /**
     * Actualizează o entitate existentă.
     * @param entity Entitatea cu datele noi.
     * @return null dacă actualizarea a reușit, sau entitatea dacă nu a fost găsită pentru actualizare.
     */
    E update(E entity);
}
