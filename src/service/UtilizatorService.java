package service;
import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.ValidationException;
import repo.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsabil cu operațiunile legate de utilizatori.
 */
public class UtilizatorService {
    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenii;

    public UtilizatorService(Repository<Long, Utilizator> repoUtilizatori, Repository<Tuple<Long, Long>, Prietenie> repoPrietenii) {
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenii = repoPrietenii;
    }

    public void addUtilizator(String prenume, String nume) {
        Utilizator u = new Utilizator(prenume, nume);
        long newId = 1L;
        for (Utilizator existingUser : repoUtilizatori.findAll()) {
            if (existingUser.getID() != null && existingUser.getID() >= newId) {
                newId = existingUser.getID() + 1;
            }
        }
        u.setID(newId);

        repoUtilizatori.save(u);
    }

    public void removeUtilizator(Long id) {
        Utilizator u = repoUtilizatori.findOne(id);
        if (u == null) {
            throw new ValidationException("Eroare: Utilizatorul nu există în baza de date!");
        }

        List<Tuple<Long, Long>> prieteniiDeSters = new ArrayList<>();
        for (Prietenie p : repoPrietenii.findAll()) {
            if (p.getIdUser1().equals(id) || p.getIdUser2().equals(id)) {
                prieteniiDeSters.add(p.getID());
            }
        }
        for (Tuple<Long, Long> idPrietenie : prieteniiDeSters) {
            repoPrietenii.delete(idPrietenie);
        }

        for (Utilizator prieten : u.getFriends()) {
            prieten.getFriends().remove(u);
        }

        repoUtilizatori.delete(id);
    }

    public Iterable<Utilizator> getAllUtilizatori() {
        return repoUtilizatori.findAll();
    }
}
