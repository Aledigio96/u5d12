package digiovannialessandro.u5d11.entities;

import digiovannialessandro.u5d11.enums.Stato;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dataPartenza;
    private String destinazione;
    @Enumerated(EnumType.STRING)
    private Stato stato;

    public Viaggio() {
    }

    public Viaggio(LocalDate dataPartenza, String destinazione, Stato stato) {
        this.dataPartenza = dataPartenza;
        this.destinazione = destinazione;
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(LocalDate dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}
