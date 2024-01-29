package service;

import Exceptions.NotFoundException;
import model.Fattura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payloads.NewFatturaDTO;
import repository.FatturaDAO;

@Service
public class FatturaService {
    @Autowired
    private FatturaDAO fatturaDAO;


    public Fattura findById(Long id) {
        return FatturaDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(Long id) {
        Fattura found = this.findById(id);
        fatturaDAO.delete(found);
    }

    public Fattura save(NewFatturaDTO body) {

        Fattura newFattura = new Fattura();
        newFattura.setData(body.data());
        newFattura.setImporto(body.importo());
        newFattura.setNumero(body.numero());
        newFattura.setId(body.id());
        return fatturaDAO.save(newFattura);
    }

    public Fattura findByIdAndUpdate(Long id, Fattura body) {
        Fattura found = this.findById(id);
        found.setData(body.getData());
        found.setImporto(body.getImporto());
        found.setNumero(body.getNumero());
        return FatturaDAO.save(found);
    }


}