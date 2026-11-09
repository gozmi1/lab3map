1. FUNCTIONAREA FACTORY-ULUI SI TRECEREA LA BAZA DE DATE
Aplicatia foloseste sablonul Simple Factory (prin clasa Singleton `RepositoryFactory`) pentru a crea instantele repository-urilor. Factory-ul citeste setarea `repository.type` din fisierul `config.properties`.
Pentru a trece la persistenta in baza de date in Laboratorul 4, NU va fi necesara nicio modificare in clasele de Service, UI sau Main. Singurii pasi necesari vor fi:
- Implementarea metodelor JDBC in clasele deja existente `UtilizatorDbRepository` si `PrietenieDbRepository`.
- Modificarea in `config.properties` a variabilei `repository.type=DATABASE`.

2. TRATAREA FISIERULUI LIPSA SI A LINIILOR CORUPTE
- Fisier lipsa: Este interceptata exceptia `FileNotFoundException` in `AbstractFileRepository`. Aplicatia nu crapa, ci porneste cu un cache in memorie gol. Fisierul fizic va fi creat automat de `BufferedWriter` la prima operatiune de `save`.
- Linii corupte: In metoda `loadData()`, parsarea fiecarei linii se face intr-un bloc try-catch. Daca apare o exceptie (e.g., `ValidationException`, `ArrayIndexOutOfBoundsException`, `DateTimeParseException`), eroarea este afisata in consola (informand utilizatorul despre randul corupt), dar bucla `while` isi continua executia (`continue`). S-a ales aceasta abordare (in loc de oprirea aplicatiei) pentru a asigura rezilienta sistemului: o singura linie corupta introdusa manual in fisier nu ar trebui sa blocheze accesul la restul datelor valide.

3. POZITIONAREA VALIDARII
Validarea (apelul `validator.validate(entity)`) a fost plasata in Repository (mai exact, in metoda `save` din `InMemoryRepository`, de unde este mostenita si in varianta cu fisiere).
Argumentare: Repository-ul este ultimul "gatekeeper" (paznic) al datelor. Plasand validarea aici, ne asiguram ca nicio entitate invalida nu poate fi scrisa in memorie sau pe disc, indiferent de cine apeleaza metoda (Service, scripturi de test, importuri automate etc.). Daca am fi pus validarea in Service, ar fi existat riscul ca un alt modul sa apeleze direct Repository-ul si sa corupa datele.

4. PRINCIPII SOLID RESPECTATE
- SRP (Single Responsibility Principle): Fiecare clasa are o singura responsabilitate. De exemplu, `UtilizatorFileRepository` se ocupa STRICT de transformarea unui obiect in string si invers; nu face validari, nu contine logica de business.
- OCP (Open/Closed Principle): `AbstractFileRepository` este inchis pentru modificare (logica de citire/scriere este batuta in cuie), dar deschis pentru extensie (prin suprascrierea metodelor `extractEntity` in subclase).
- LSP (Liskov Substitution Principle): Obiectele de tip `UtilizatorFileRepository` sau `InMemoryRepository` pot fi substituite oriunde este ceruta interfata `Repository<Long, Utilizator>`, comportamentul programului ramanand corect.
- DIP (Dependency Inversion Principle): Modulele de nivel inalt (Service) depind doar de abstractii (interfata `Repository`), nu de implementarile concrete (nu exista niciun `new UtilizatorFileRepository()` in Service sau Main).

5. SABLOANE DE PROIECTARE (DESIGN PATTERNS) FOLOSITE
- Factory (Simple Factory): `RepositoryFactory` ascunde complexitatea deciziei de instantiere.
- Template Method: Implementat in `AbstractFileRepository`. Metodele `loadData` si `writeToFile` ofera "scheletul" algoritmului, in timp ce pasii specifici (`extractEntity` si `createEntityAsString`) sunt delegati subclaselor.
- Strategy: Implementat prin interfata `Validator<T>`. Algoritmul de validare poate fi schimbat la runtime (injectand un `UtilizatorValidator` normal sau unul "Strict") fara a modifica repository-ul.
- Decorator: Folosit in lucrul cu fisierele Java IO (ex. `BufferedReader` decoreaza `FileReader` pentru a adauga functionalitatea de citire linie cu linie / buffering).

6. SIMPLE FACTORY VS ABSTRACT FACTORY (Discutie OCP)
Abordarea curenta (Simple Factory cu instructiune `switch`) incalca teoretic principiul OCP. Daca adaugam un nou tip de persistenta (de ex. XML), trebuie sa modificam fizic codul din `RepositoryFactory` pentru a adauga un nou `case`.
O abordare de tip Abstract Factory ar respecta OCP: am avea o interfata `RepositoryFactory` si mai multe implementari (`FileRepositoryFactory`, `DbRepositoryFactory`, `XmlRepositoryFactory`). Adaugarea unui format nou s-ar face creand o clasa noua (extensie), fara a atinge/modifica fabricile existente. Totusi, pentru complexitatea acestui stadiu al proiectului, un Simple Factory este suficient de clar si mentenabil.