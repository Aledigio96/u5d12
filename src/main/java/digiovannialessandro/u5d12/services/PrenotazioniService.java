package digiovannialessandro.u5d11.services;

import digiovannialessandro.u5d11.ecxeptions.BadRequestException;
import digiovannialessandro.u5d11.ecxeptions.NotFoundException;
import digiovannialessandro.u5d11.entities.Dipendente;
import digiovannialessandro.u5d11.entities.Prenotazione;
import digiovannialessandro.u5d11.entities.Viaggio;
import digiovannialessandro.u5d11.payloads.PrenotazioniPayload;
import digiovannialessandro.u5d11.repositories.DipendentiRepository;
import digiovannialessandro.u5d11.repositories.PrenotazioniRepository;
import digiovannialessandro.u5d11.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private ViaggiRepository viaggiRepository;
    @Autowired
    private DipendentiRepository dipendentiRepository;

    public Prenotazione save(PrenotazioniPayload payload) {

        boolean alreadyExists = prenotazioniRepository.existsByDipendente_IdAndDataDiRichiesta(
                 payload.dipendenteId(), payload.dataDiRichiesta()
        );

        if (alreadyExists) {
            throw new BadRequestException("L'utente ha già una prenotazione per questa data.");
        }
        Viaggio viaggio = viaggiRepository.findById(payload.viaggioId())
                .orElseThrow(() -> new BadRequestException("Viaggio non trovato"));

        Dipendente dipendente = dipendentiRepository.findById(payload.dipendenteId())
                .orElseThrow(() -> new BadRequestException("Dipendente non trovato"));

        Prenotazione prenotazione = new Prenotazione(payload.dataDiRichiesta(),viaggio,dipendente);

        return prenotazioniRepository.save(prenotazione);
    }
    public Prenotazione findById(int prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }
    public Page<Prenotazione> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.prenotazioniRepository.findAll(pageable);
    }
    public void findByIdAndDelete(int prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prenotazioniRepository.delete(found);
    }
}
