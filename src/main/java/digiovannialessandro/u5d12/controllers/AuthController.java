package digiovannialessandro.u5d12.controllers;

import digiovannialessandro.u5d12.ecxeptions.ValidationException;
import digiovannialessandro.u5d12.entities.Dipendente;
import digiovannialessandro.u5d12.payloads.DipendenteLoginDTO;
import digiovannialessandro.u5d12.payloads.DipendentiLoginRespDTO;
import digiovannialessandro.u5d12.payloads.DipendentiPayload;
import digiovannialessandro.u5d12.payloads.RespDipendentiDTO;
import digiovannialessandro.u5d12.services.AuthService;
import digiovannialessandro.u5d12.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendentiService dipendentiService;



    @PostMapping("/login")
    public DipendentiLoginRespDTO login(@RequestBody DipendenteLoginDTO body) {
        String accessToken = authService.checkCredentialsAndGenerateToken(body);
        return new DipendentiLoginRespDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RespDipendentiDTO save(@RequestBody @Validated DipendentiPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            //validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Dipendente newDipendente = this.dipendentiService.save(payload);
            return new RespDipendentiDTO(newDipendente.getId());
        }

    }
}
