package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProvinciaService {
    @Autowired
    ProvinciaRepository provinciaRepository;


    public Page<Provincia> getProvincia(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return provinciaRepository.findAll(pageable);
    }

    public Provincia save(ProvinciaDTO provinciaDTO) {
        Provincia newProvincia = new Provincia();
        newProvincia.setSigla(provinciaDTO.getSigla());
        newProvincia.setProvincia(provinciaDTO.getProvincia());
        newProvincia.setRegione(provinciaDTO.getRegione());
        return provinciaRepository.save(newProvincia);
    }

    public Provincia findByName(String provincia) {
        return provinciaRepository.findByName(provincia).orElseThrow(() -> new NotFoundException(provincia));
    }
    public Provincia findById(Long id) {
        return provinciaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Provincia findByIdAndUpdate(Long id, ProvinciaDTO body) {

        Provincia found = this.findById(id);
        found.setProvincia(body.getProvincia());
        found.setRegione(body.getRegione());
        found.setSigla(body.getSigla());
        return provinciaRepository.save(found);
    }

    public void findByIdAndDelete(Long id) {
        Provincia found = this.findById(id);
        provinciaRepository.delete(found);
    }
}
