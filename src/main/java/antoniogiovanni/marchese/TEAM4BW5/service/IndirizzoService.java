package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Indirizzo;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewIndirizzoDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private ComuneService comuneService;

    @Autowired
    private ClienteService clienteService;

    public Page<Indirizzo> getIndirizzi(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return indirizzoRepository.findAll(pageable);
    }

    public Indirizzo save(NewIndirizzoDTO indirizzoDTO) {
        Indirizzo newIndirizzo = new Indirizzo();
        Comune comune = comuneService.findById(indirizzoDTO.idComune());
        Cliente cliente = clienteService.findById(indirizzoDTO.idCliente());
        newIndirizzo.setCap(indirizzoDTO.cap());
        newIndirizzo.setVia(indirizzoDTO.via());
        newIndirizzo.setComune(comune);
        newIndirizzo.setCliente(cliente);
        newIndirizzo.setLocalita(indirizzoDTO.localita());
        newIndirizzo.setNumeroCivico(indirizzoDTO.numeroCivico());
        return indirizzoRepository.save(newIndirizzo);
    }

    public Indirizzo findById(long id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) {
        Indirizzo found = this.findById(id);
        indirizzoRepository.delete(found);
    }

    public Indirizzo findByIdAndUpdate(long id, NewIndirizzoDTO indirizzoDTO) {
        Indirizzo found = this.findById(id);
        Comune comune = comuneService.findById(indirizzoDTO.idComune());
        Cliente cliente = clienteService.findById(indirizzoDTO.idCliente());
        found.setCap(indirizzoDTO.cap());
        found.setVia(indirizzoDTO.via());
        found.setComune(comune);
        found.setCliente(cliente);
        found.setLocalita(indirizzoDTO.localita());
        found.setNumeroCivico(indirizzoDTO.numeroCivico());
        return indirizzoRepository.save(found);
    }

}
