package digiovannialessandro.u5d12.controllers;

import digiovannialessandro.u5d12.ecxeptions.ValidationException;

import digiovannialessandro.u5d12.entities.Prenotazione;

import digiovannialessandro.u5d12.payloads.PrenotazioniPayload;

import digiovannialessandro.u5d12.payloads.RespPrenotazioniDTO;
import digiovannialessandro.u5d12.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
    public RespPrenotazioniDTO save(@RequestBody @Validated PrenotazioniPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Prenotazione newPrenotazione = this.prenotazioniService.save(payload);
            return new RespPrenotazioniDTO(newPrenotazione.getId());
        }

    }
    @GetMapping
    public Page<Prenotazione> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy
    ) {

        return this.prenotazioniService.findAll(page, size, sortBy);
    }


    @GetMapping("/{prenotazioneId}")
    public Prenotazione getById(@PathVariable int prenotazioneId) {
        return this.prenotazioniService.findById(prenotazioneId);
    }
    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable int prenotazioneId) {
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }
}
