package digiovannialessandro.u5d12.payloads;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PrenotazioniPayload (
        @NotNull(message = "La data di richiesta è obbligatoria")
        LocalDate dataDiRichiesta,
        int viaggioId,
        int dipendenteId,
        String nota

) {
}
