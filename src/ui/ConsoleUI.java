package ui;

import service.PrietenieService;
import service.ReteaService;
import service.UtilizatorService;
import java.util.Scanner;

/**
 * Clasa responsabilă cu interacțiunea cu utilizatorul prin intermediul consolei.
 * Reprezintă stratul de prezentare (UI) al aplicației.
 */
public class ConsoleUI {
    private UtilizatorService utilizatorService;
    private PrietenieService prietenieService;
    private ReteaService reteaService;
    private Scanner scanner;

    /**
     * Instanțiază interfața consolă cu service-urile necesare.
     * @param utilizatorService Service-ul pentru gestionarea utilizatorilor.
     * @param prietenieService Service-ul pentru gestionarea prieteniilor.
     * @param reteaService Service-ul pentru operațiuni pe graful rețelei.
     */
    public ConsoleUI(UtilizatorService utilizatorService, PrietenieService prietenieService, ReteaService reteaService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.reteaService = reteaService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Pornește bucla principală a meniului din consolă și așteaptă comenzile utilizatorului.
     */
    public void run() {
        while (true) {
            System.out.println("\n--- TOY SOCIAL NETWORK ---");
            System.out.println("1. Adaugă utilizator");
            System.out.println("2. Adaugă prietenie");
            System.out.println("3. Afișează numărul de comunități");
            System.out.println("4. Afișează cea mai sociabilă comunitate");
            System.out.println("5. Șterge utilizator");
            System.out.println("6. Șterge prietenie");
            System.out.println("7. Afișează toți utilizatorii");
            System.out.println("0. Ieșire");
            System.out.print("Alege o opțiune: ");

            String optiune = scanner.nextLine();

            try {
                switch (optiune) {
                    case "1": adaugaUtilizator(); break;
                    case "2": adaugaPrietenie(); break;
                    case "3": System.out.println("Număr comunități: " + reteaService.getNumarComunitati()); break;
                    case "4": System.out.println("Cea mai sociabilă comunitate: " + reteaService.getCeaMaiSociabilaComunitate()); break;
                    case "5": stergeUtilizator(); break;
                    case "6": stergePrietenie(); break;
                    case "7": afiseazaUtilizatori(); break;
                    case "0": System.out.println("Se închide rețeaua..."); return;
                    default: System.out.println("Opțiune invalidă!");
                }
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    /**
     * Preia datele de la tastatură și adaugă un nou utilizator în sistem.
     */
    private void adaugaUtilizator() {
        System.out.print("Prenume: ");
        String prenume = scanner.nextLine();
        System.out.print("Nume: ");
        String nume = scanner.nextLine();
        utilizatorService.addUtilizator(prenume, nume);
        System.out.println("Utilizator adăugat cu succes!");
    }

    /**
     * Preia ID-urile de la tastatură și creează o relație de prietenie.
     */
    private void adaugaPrietenie() {
        System.out.print("ID Utilizator 1: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID Utilizator 2: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        prietenieService.addPrietenie(id1, id2);
        System.out.println("Prietenie adăugată cu succes!");
    }

    /**
     * Preia ID-ul de la tastatură și șterge utilizatorul corespunzător din sistem.
     */
    private void stergeUtilizator() {
        System.out.print("ID Utilizator de șters: ");
        Long id = Long.parseLong(scanner.nextLine());
        utilizatorService.removeUtilizator(id);
        System.out.println("Utilizator șters cu succes!");
    }

    /**
     * Preia ID-urile de la tastatură și distruge relația de prietenie dintre cei doi utilizatori.
     */
    private void stergePrietenie() {
        System.out.print("ID Utilizator 1: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID Utilizator 2: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        prietenieService.removePrietenie(id1, id2);
        System.out.println("Prietenie ștearsă cu succes!");
    }

    /**
     * Afișează în consolă lista tuturor utilizatorilor înregistrați în rețea.
     */
    private void afiseazaUtilizatori() {
        System.out.println("\n--- Lista Utilizatori ---");
        for (domain.Utilizator u : utilizatorService.getAllUtilizatori()) {
            System.out.println(u);
        }
    }
}
