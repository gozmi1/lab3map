package factory;
import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import domain_validators.Validator;
import exceptions.RepositoryException;
import repo.InMemoryRepository;
import repo.PrietenieDbRepository;
import repo.PrietenieFileRepository;
import repo.Repository;
import repo.UtilizatorDbRepository;
import repo.UtilizatorFileRepository;

import java.io.InputStream;
import java.util.Properties;

/**
 * Factory responsabil cu instanțierea Repository-urilor pe baza fișierului de configurare.
 * Implementat folosind șablonul Singleton.
 */
public class RepositoryFactory {
    private static RepositoryFactory instance = null;
    private Properties properties;

    private RepositoryFactory() {
        properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RepositoryException("Fișierul config.properties nu a fost găsit în resources!");
            }
            properties.load(is);
        } catch (Exception e) {
            throw new RepositoryException("Eroare la încărcarea configurărilor", e);
        }
    }

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }
        return instance;
    }

    public Repository<Long, Utilizator> createUtilizatorRepository(Validator<Utilizator> validator) {
        String typeStr = properties.getProperty("repository.type", "MEMORY");
        RepositoryType type = RepositoryType.valueOf(typeStr.toUpperCase());

        switch (type) {
            case MEMORY:
                return new InMemoryRepository<>(validator);
            case FILE:
                String file = properties.getProperty("utilizatori.file", "data/utilizatori.txt");
                return new UtilizatorFileRepository(validator, file);
            case DATABASE:
                return new UtilizatorDbRepository(validator, properties.getProperty("db.url"),
                        properties.getProperty("db.user"), properties.getProperty("db.password"));
            default:
                throw new RepositoryException("Tip de repository necunoscut!");
        }
    }

    public Repository<Tuple<Long, Long>, Prietenie> createPrietenieRepository(Validator<Prietenie> validator) {
        String typeStr = properties.getProperty("repository.type", "MEMORY");
        RepositoryType type = RepositoryType.valueOf(typeStr.toUpperCase());

        switch (type) {
            case MEMORY:
                return new InMemoryRepository<>(validator);
            case FILE:
                String file = properties.getProperty("prietenii.file", "data/prietenii.txt");
                return new PrietenieFileRepository(validator, file);
            case DATABASE:
                return new PrietenieDbRepository(validator, properties.getProperty("db.url"),
                        properties.getProperty("db.user"), properties.getProperty("db.password"));
            default:
                throw new RepositoryException("Tip de repository necunoscut!");
        }
    }
}
