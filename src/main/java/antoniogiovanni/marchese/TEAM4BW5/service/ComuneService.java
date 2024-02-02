package antoniogiovanni.marchese.TEAM4BW5.service;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ComuneDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {

        @Autowired
        private ComuneRepository comuneRepository;

        @Autowired
        private ProvinciaService provinciaService;

        public Comune save(ComuneDTO comuneDTO) {
                Comune comune = new Comune();
                comune.setProgressivoDelComune(comuneDTO.progressivoDelComune());
                comune.setProvincia(provinciaService.findById(comuneDTO.idProvincia()));
                comune.setDenominazione(comune.getDenominazione());
                return comuneRepository.save(comune);
        }

        public Comune save(Comune comune) {
                return comuneRepository.save(comune);
        }


        public Page<Comune> getComune(int page, int size, String orderBy) {

            Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

            return comuneRepository.findAll(pageable);
        }

        public Comune findByIdAndUpdate(long id, ComuneDTO body) {
                Comune found = this.findById(id);

                found.setDenominazione(body.denominazione());
                Provincia provincia = provinciaService.findById(body.idProvincia());
                found.setProvincia(provincia);
                return comuneRepository.save(found);
        }

        public Comune findById(long id) {
                return comuneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        }

        public void findByIdAndDelete(long id) {
                Comune found = this.findById(id);
                comuneRepository.delete(found);
        }

        public List<Comune> getByProvincia(Long idProvincia){
                return comuneRepository.getComuniByProvincia(idProvincia);
        }

        public Long getIdProvByComune(Long idComune){
                Comune found = comuneRepository.findById(idComune).orElseThrow(() -> new NotFoundException(idComune));
                return found.getProvincia().getId();
        }

}
