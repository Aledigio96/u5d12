package digiovannialessandro.u5d11.payloads;

import jakarta.validation.constraints.NotEmpty;
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
