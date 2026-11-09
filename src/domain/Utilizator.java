package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entitatea principală care reprezintă un nod (utilizator) în rețeaua de socializare.
 */
public class Utilizator implements Entity<Long>{
    private Long id;
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;

    /**
     * Instanțiază un nou utilizator cu liste de prieteni goale.
     * @param firstName Prenumele utilizatorului.
     * @param lastName Numele de familie al utilizatorului.
     * */
    public Utilizator(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.friends= new ArrayList<>();
    }
    @Override
    public Long getID() {
        return id;
    }

    @Override
    public void setID(Long id) {
        this.id=id;
    }

    /**
     * @return Prenumele utilizatorului.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return Numele de familie al utilizatorului.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param firstName Noul prenume al utilizatorului.
     */
    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    /**
     * @param lastName Noul nume de familie al utilizatorului.
     */
    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    /**
     * @return Lista internă cu referințe către prietenii utilizatorului.
     */
    public List<Utilizator> getFriends() {
        return this.friends;
    }

    /**
     * Adaugă un nou prieten în lista utilizatorului.
     * @param u Utilizatorul care va fi adăugat.
     */
    public void addFriend(Utilizator u) {
        friends.add(u);
    }

    /**
     * Șterge un prieten din lista utilizatorului.
     * @param u Utilizatorul care va fi eliminat.
     */
    public void removeFriend(Utilizator u) {
        friends.remove(u);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override

    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

