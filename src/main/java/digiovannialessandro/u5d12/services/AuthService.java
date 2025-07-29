package digiovannialessandro.u5d11.services;

import digiovannialessandro.u5d11.configurations.JWTConfig;
import digiovannialessandro.u5d11.ecxeptions.UnauthorizedException;
import digiovannialessandro.u5d11.entities.Dipendente;
import digiovannialessandro.u5d11.payloads.DipendenteLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;

    @Autowired
    private JWTConfig jwtConfig;

    public String checkCredentialsAndGenerateToken(DipendenteLoginDTO body) {

        Dipendente found = this.dipendentiService.findByEmail(body.email());

        if (found.getPassword().equals(body.password())) {

            String accessToken = jwtConfig.createToken(found);

            return accessToken;
        } else {

            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
