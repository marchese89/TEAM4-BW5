package service;

import exceptions.NotFoundException;
import model.Fattura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payloads.NewFatturaDTO;
import repository.FatturaRepository;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;


    public Fattura findById(Long id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(Long id) {
        Fattura found = this.findById(id);
        fatturaRepository.delete(found);
    }

    public Fattura save(NewFatturaDTO body) {

        Fattura newFattura = new Fattura();
        newFattura.setData(body.data());
        newFattura.setImporto(body.importo());
        newFattura.setNumero(body.numero());
        newFattura.setId(body.id());
        return fatturaRepository.save(newFattura);
    }

    public Fattura findByIdAndUpdate(Long id, Fattura body) {
        Fattura found = this.findById(id);
        found.setData(body.getData());
        found.setImporto(body.getImporto());
        return fatturaRepository.save(found);
    }


}