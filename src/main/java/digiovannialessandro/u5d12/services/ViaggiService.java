package digiovannialessandro.u5d12.services;

import digiovannialessandro.u5d12.ecxeptions.NotFoundException;
import digiovannialessandro.u5d12.entities.Dipendente;
import digiovannialessandro.u5d12.entities.Prenotazione;
import digiovannialessandro.u5d12.entities.Viaggio;
import digiovannialessandro.u5d12.enums.Stato;
import digiovannialessandro.u5d12.payloads.ViaggiPayload;
import digiovannialessandro.u5d12.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggiService {

    @Autowired
    private ViaggiRepository viaggiRepository;

    public Viaggio save(ViaggiPayload payload) {
        Viaggio viaggio = new Viaggio(payload.dataPartenza(), payload.destinazione(), payload.stato());
        return viaggiRepository.save(viaggio);
    }

    public Page<Viaggio> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.viaggiRepository.findAll(pageable);
    }

    public Viaggio findById(int viaggioId) {
        return this.viaggiRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }
    public Viaggio cambiamentoStato(int id, Stato stato) {
        Viaggio found = findById(id);
        found.setStato(stato);
        Viaggio statoMod = viaggiRepository.save(found);
        return statoMod;
    }
    public void findByIdAndDelete(int viaggioId) {
        Viaggio found = this.findById(viaggioId);
        this.viaggiRepository.delete(found);
    }
}
