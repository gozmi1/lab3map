package domain;

import java.time.LocalDateTime;

/**
 * Entitatea care reprezintă o relație de prietenie între doi utilizatori.
 * ID-ul este de tip Tuple<Long, Long> și este normalizat (id minim primul).
 */
public class Prietenie implements Entity<Tuple<Long, Long>> {
    private Tuple<Long, Long> id;
    private Long idUser1;
    private Long idUser2;
    private LocalDateTime date;

    /**
     * Creează o nouă prietenie.
     * @param idUser1 ID-ul primului utilizator.
     * @param idUser2 ID-ul celui de-al doilea utilizator.
     * @param date Data la care a fost legată prietenia.
     */
    public Prietenie(Long idUser1, Long idUser2, LocalDateTime date) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.date = date;

        // Normalizare ID (id-ul mai mic este mereu pe partea stângă)
        if (idUser1 < idUser2) {
            this.id = new Tuple<>(idUser1, idUser2);
        } else {
            this.id = new Tuple<>(idUser2, idUser1);
        }
    }

    @Override
    public Tuple<Long, Long> getID() { return id; }

    @Override
    public void setID(Tuple<Long, Long> id) { this.id = id; }

    public Long getIdUser1() { return idUser1; }
    public Long getIdUser2() { return idUser2; }
    public LocalDateTime getDate() { return date; }

    public void setIdUser1(Long idUser1) { this.idUser1 = idUser1; }
    public void setIdUser2(Long idUser2) { this.idUser2 = idUser2; }
    public void setDate(LocalDateTime date) { this.date = date; }
}


