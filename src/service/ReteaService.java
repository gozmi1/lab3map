package service;

import domain.Utilizator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service responsabil cu analiza structurii rețelei de socializare folosind algoritmi pe grafuri.
 */
public class ReteaService {
    private UtilizatorService utilizatorService;

    /**
     * Instanțiază service-ul de rețea.
     * @param utilizatorService Service-ul de utilizatori necesar pentru extragerea nodurilor grafului.
     */
    public ReteaService(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    /**
     * Calculează numărul total de comunități (componente conexe) din rețeaua de socializare.
     * @return Numărul de comunități identificate.
     */
    public int getNumarComunitati() {
        Iterable<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        Set<Long> vizitat = new HashSet<>();
        int numarComunitati = 0;

        for (Utilizator u : utilizatori) {
            if (!vizitat.contains(u.getID())) {
                numarComunitati++;
                dfs(u, vizitat);
            }
        }
        return numarComunitati;
    }

    /**
     * Parcurgere în adâncime (DFS) pentru marcarea nodurilor dintr-o componentă conexă.
     * @param u Utilizatorul curent (nodul sursă).
     * @param vizitat Setul de ID-uri ale utilizatorilor deja vizitați.
     */
    private void dfs(Utilizator u, Set<Long> vizitat) {
        vizitat.add(u.getID());
        for (Utilizator prieten : u.getFriends()) {
            if (!vizitat.contains(prieten.getID())) {
                dfs(prieten, vizitat);
            }
        }
    }

    /**
     * Identifică și returnează comunitatea (componenta conexă) care conține cel mai lung lanț de prietenii.
     * @return O listă cu utilizatorii care formează cea mai sociabilă comunitate.
     */
    public List<Utilizator> getCeaMaiSociabilaComunitate() {
        Iterable<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        Set<Long> vizitatGlobal = new HashSet<>();

        List<Utilizator> ceaMaiSociabila = new ArrayList<>();
        int maxPathLength = -1;

        for (Utilizator u : utilizatori) {
            if (!vizitatGlobal.contains(u.getID())) {
                List<Utilizator> comunitateCurenta = new ArrayList<>();
                extrageComunitate(u, vizitatGlobal, comunitateCurenta);

                int pathLength = getLongestPathInComponent(comunitateCurenta);
                if (pathLength > maxPathLength) {
                    maxPathLength = pathLength;
                    ceaMaiSociabila = comunitateCurenta;
                }
            }
        }
        return ceaMaiSociabila;
    }

    /**
     * Extrage toți membrii unei comunități pornind de la un nod sursă.
     * @param u Utilizatorul de pornire.
     * @param vizitat Setul global de noduri vizitate.
     * @param comunitate Lista în care sunt salvați membrii componentei curente.
     */
    private void extrageComunitate(Utilizator u, Set<Long> vizitat, List<Utilizator> comunitate) {
        vizitat.add(u.getID());
        comunitate.add(u);
        for (Utilizator prieten : u.getFriends()) {
            if (!vizitat.contains(prieten.getID())) {
                extrageComunitate(prieten, vizitat, comunitate);
            }
        }
    }

    /**
     * Calculează lungimea celui mai lung drum (lanț de prietenii) dintr-o comunitate dată.
     * @param comunitate Lista de utilizatori care formează comunitatea.
     * @return Lungimea maximă a drumului identificat.
     */
    private int getLongestPathInComponent(List<Utilizator> comunitate) {
        int max = 0;
        for (Utilizator u : comunitate) {
            Set<Long> vizitatLocal = new HashSet<>();
            int currentMax = dfsLongestPath(u, vizitatLocal);
            if (currentMax > max) {
                max = currentMax;
            }
        }
        return max;
    }

    /**
     * Aplică algoritmul DFS cu Backtracking pentru a găsi cel mai lung drum pornind de la un nod specific.
     * @param u Utilizatorul de pornire.
     * @param vizitat Setul local de noduri vizitate pe drumul curent.
     * @return Adâncimea maximă (lungimea drumului) de la nodul sursă.
     */
    private int dfsLongestPath(Utilizator u, Set<Long> vizitat) {
        vizitat.add(u.getID());
        int maxDepth = 0;

        for (Utilizator prieten : u.getFriends()) {
            if (!vizitat.contains(prieten.getID())) {
                int depth = dfsLongestPath(prieten, vizitat);
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            }
        }

        vizitat.remove(u.getID());
        return maxDepth + 1;
    }
}
