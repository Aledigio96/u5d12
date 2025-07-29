package digiovannialessandro.u5d11.controllers;

import digiovannialessandro.u5d11.ecxeptions.ValidationException;
import digiovannialessandro.u5d11.entities.Viaggio;
import digiovannialessandro.u5d11.enums.Stato;
import digiovannialessandro.u5d11.payloads.ViaggiPayload;
import digiovannialessandro.u5d11.services.ViaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggiService viaggiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody @Validated ViaggiPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList());
        }
        return viaggiService.save(payload);
    }

    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy
    ) {

        return this.viaggiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio getById(@PathVariable int viaggioId) {
        return this.viaggiService.findById(viaggioId);
    }
    @PatchMapping("/{viaggioId}/stato")
    public Viaggio cambiaStato(@PathVariable int id, @RequestParam Stato stato) {
        return viaggiService.cambiamentoStato(id, stato);
    }
    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable int viaggioId) {
        this.viaggiService.findByIdAndDelete(viaggioId);
    }
}
