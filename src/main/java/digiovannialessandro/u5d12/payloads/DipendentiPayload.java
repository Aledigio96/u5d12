package digiovannialessandro.u5d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendentiPayload (
        @NotEmpty
        @Size(min = 2,max = 30,message = "L'username deve essere di lunghezza compresa tra i 2 e i 30 caratteri ")
        String username,
        @NotEmpty
        @Size(min = 2,max = 30,message = "il nome deve essere di lunghezza compresa tra i 2 e i 30 caratteri ")
        String name,
        @NotEmpty
        @Size(min = 2,max = 30,message = "il cognome deve essere di lunghezza compresa tra i 2 e i 30 caratteri ")
        String surname,
        @NotEmpty(message = "L'indirizzo email è obbligatorio")
        @Email
        String email,
        String urlImg,
        String password
     ){}
