package service;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.ValidationException;
import repo.Repository;

import java.time.LocalDateTime;

/**
 * Service responsabil cu orchestrarea operațiunilor legate de relațiile de prietenie dintre utilizatori.
 */
public class PrietenieService {
    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenii;

    public PrietenieService(Repository<Long, Utilizator> repoUtilizatori, Repository<Tuple<Long, Long>, Prietenie> repoPrietenii) {
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenii = repoPrietenii;
        for (Prietenie p : repoPrietenii.findAll()) {
            Utilizator u1 = repoUtilizatori.findOne(p.getIdUser1());
            Utilizator u2 = repoUtilizatori.findOne(p.getIdUser2());

            if (u1 != null && u2 != null) {
                if (!u1.getFriends().contains(u2)) {
                    u1.addFriend(u2);
                }
                if (!u2.getFriends().contains(u1)) {
                    u2.addFriend(u1);
                }
            }
        }
    }

    /**
     * Creează și salvează o legătură de prietenie între doi utilizatori existenți.
     * @param idUser1 ID-ul primului utilizator.
     * @param idUser2 ID-ul celui de-al doilea utilizator.
     */
    public void addPrietenie(Long idUser1, Long idUser2) {
        Utilizator u1 = repoUtilizatori.findOne(idUser1);
        Utilizator u2 = repoUtilizatori.findOne(idUser2);

        if (u1 == null || u2 == null) {
            throw new ValidationException("Unul sau ambii utilizatori nu există în rețea!");
        }

        Prietenie prietenie = new Prietenie(idUser1, idUser2, LocalDateTime.now());

        repoPrietenii.save(prietenie);

        u1.addFriend(u2);
        u2.addFriend(u1);
    }

    /**
     * Elimină o relație de prietenie între doi utilizatori și actualizează listele lor interne.
     * @param idUser1 ID-ul primului utilizator.
     * @param idUser2 ID-ul celui de-al doilea utilizator.
     */
    public void removePrietenie(Long idUser1, Long idUser2) {
        Utilizator u1 = repoUtilizatori.findOne(idUser1);
        Utilizator u2 = repoUtilizatori.findOne(idUser2);

        if (u1 == null || u2 == null) {
            throw new ValidationException("Eroare: Unul dintre utilizatori nu există în baza de date!");
        }

        boolean stersDinLista1 = u1.getFriends().remove(u2);
        boolean stersDinLista2 = u2.getFriends().remove(u1);

        if (!stersDinLista1 || !stersDinLista2) {
            throw new ValidationException("Eroare: Acești doi utilizatori nu sunt prieteni!");
        }

        Tuple<Long, Long> idPrietenieDeSters = new Tuple<>(Math.min(idUser1, idUser2), Math.max(idUser1, idUser2));
        Prietenie stearsa = repoPrietenii.delete(idPrietenieDeSters);

        if (stearsa == null) {
            throw new ValidationException("Eroare critică: Prietenia nu a fost găsită în Repository!");
        }
    }

    public Iterable<Prietenie> getAllPrietenii() {
        return repoPrietenii.findAll();
    }
}
