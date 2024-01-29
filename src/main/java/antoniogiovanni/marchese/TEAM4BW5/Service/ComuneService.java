package antoniogiovanni.marchese.TEAM4BW5.Service;

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

@Service
public class ComuneService {

        @Autowired
        private ComuneRepository comuneRepository;

        @Autowired
        private ProvinciaService provinciaService;

        public Comune save(ComuneDTO comuneDTO) {
                Provincia provincia = provinciaService.findById(comuneDTO.getProvincia().getId());
                Comune newComune = new Comune();
                newComune.setProvincia(provincia);
                newComune.setDenominazione(comuneDTO.getDenominazione());
                newComune.setCodiceProvincia(comuneDTO.getCodiceProvincia());
                return comuneRepository.save(newComune);
        }


public Page<Comune> getComune(int page, int size, String orderBy) {

            Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

            return comuneRepository.findAll(pageable);
        }

        public Comune findByIdAndUpdate(int id, ComuneDTO body) {
                Comune found = this.findById(id);

                found.setDenominazione(body.getDenominazione());
                found.setCodiceProvincia(body.getCodiceProvincia());

                if (found.getProvincia().getId() != body.getProvincia().getId()) {
                        Provincia newProvincia = provinciaService.findByName(String.valueOf(body.getProvincia()));
                        found.setProvincia(newProvincia);
                }
                return comuneRepository.save(found);
        }

                public Comune findById(int id) {
                        return comuneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
                }

        public void findByIdAndDelete(int id) {
                Comune found = this.findById(id);
                comuneRepository.delete(found);
        }

        }
