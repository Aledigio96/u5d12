package digiovannialessandro.u5d11.services;


import digiovannialessandro.u5d11.ecxeptions.BadRequestException;
import digiovannialessandro.u5d11.ecxeptions.NotFoundException;
import digiovannialessandro.u5d11.ecxeptions.ValidationException;
import digiovannialessandro.u5d11.entities.Dipendente;
import digiovannialessandro.u5d11.entities.Viaggio;
import digiovannialessandro.u5d11.payloads.DipendentiPayload;
import digiovannialessandro.u5d11.repositories.DipendentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class DipendentiService {
    @Autowired
    private DipendentiRepository dipendentiRepository;


    public Dipendente save(DipendentiPayload payload) {

        this.dipendentiRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });


        Dipendente newDipendente = new Dipendente(payload.email(), payload.surname(), payload.name(), payload.username(), payload.password());



        Dipendente savedDipendente = this.dipendentiRepository.save(newDipendente);

        return savedDipendente;
    }
    public Page<Dipendente> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.dipendentiRepository.findAll(pageable);
    }

    public Dipendente findById(int dipendenteId) {
        return this.dipendentiRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public void findByIdAndDelete(int dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendentiRepository.delete(found);
    }
    public Dipendente findByEmail(String email) {
        return this.dipendentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }
}
