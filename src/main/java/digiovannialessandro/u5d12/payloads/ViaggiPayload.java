package digiovannialessandro.u5d11.payloads;

import digiovannialessandro.u5d11.enums.Stato;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggiPayload (
         @NotNull(message = "La data di partenza è obbligatoria")
         LocalDate dataPartenza,
         @NotEmpty(message = "La data di destinazione è obbligatoria")
         @Size(min = 2,max = 15)
         String destinazione,
         @NotNull(message = "Lo stato del viaggio è obbligatorio")
         Stato stato){
}
