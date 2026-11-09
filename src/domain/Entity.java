package domain;

import java.io.Serializable;

/**
 * Interfața de bază pentru toate entitățile din sistemul rețelei de socializare.
 * @param <ID> Tipul de date al identificatorului unic al entității (ex: Long, String).
 */
public interface Entity<ID> extends Serializable {
    /**
     * Returnează identificatorul unic al entității.
     * @return ID-ul entității.
     */
    ID getID();

    /**
     * Setează identificatorul unic al entității.
     * @param id Noul ID care va fi atribuit.
     */
    void setID(ID id);
}
