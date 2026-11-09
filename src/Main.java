import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import domain_validators.PrietenieValidator;
import domain_validators.UtilizatorValidator;
import domain_validators.Validator;
import factory.RepositoryFactory;
import repo.Repository;
import service.PrietenieService;
import service.ReteaService;
import service.UtilizatorService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        try {

            Validator<Utilizator> utilizatorValidator = new UtilizatorValidator();
            Validator<Prietenie> prietenieValidator = new PrietenieValidator();

            RepositoryFactory factory = RepositoryFactory.getInstance();
            Repository<Long, Utilizator> repoUtilizatori = factory.createUtilizatorRepository(utilizatorValidator);
            Repository<Tuple<Long, Long>, Prietenie> repoPrietenii = factory.createPrietenieRepository(prietenieValidator);

            UtilizatorService utilizatorService = new UtilizatorService(repoUtilizatori, repoPrietenii);
            PrietenieService prietenieService = new PrietenieService(repoUtilizatori, repoPrietenii);
            ReteaService reteaService = new ReteaService(utilizatorService);

            ConsoleUI ui = new ConsoleUI(utilizatorService, prietenieService, reteaService);
            ui.run();

        } catch (Exception e) {
            System.out.println("Eroare critică la inițializarea aplicației: " + e.getMessage());
        }
    }
}