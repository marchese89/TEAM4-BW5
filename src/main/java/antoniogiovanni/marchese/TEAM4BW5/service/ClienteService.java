package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ClienteDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IndirizzoService indirizzoService;

    public Cliente creaNuovoCliente(ClienteDTO clienteDTO) {

        Cliente nuovoCliente = new Cliente();
        nuovoCliente.setRagioneSociale(clienteDTO.ragioneSociale());
        nuovoCliente.setPartitaIva(clienteDTO.partitaIva());
        nuovoCliente.setEmail(clienteDTO.email());
        nuovoCliente.setDataInserimento(LocalDate.now());
        nuovoCliente.setFatturatoAnnuale(clienteDTO.fatturatoAnnuale());
        nuovoCliente.setPec(clienteDTO.pec());
        nuovoCliente.setTelefono(clienteDTO.telefono());
        if (clienteDTO.emailContatto() != null) {
            nuovoCliente.setEmailContatto(clienteDTO.emailContatto());
        }
        if (clienteDTO.nomeContatto() != null) {
            nuovoCliente.setNomeContatto(clienteDTO.nomeContatto());
        }
        if (clienteDTO.cognomeContatto() != null) {
            nuovoCliente.setCognomeContatto(clienteDTO.cognomeContatto());
        }
        
        if (clienteDTO.telefonoContatto() != null) {
            nuovoCliente.setTelefonoContatto(clienteDTO.telefonoContatto());
        }
        nuovoCliente.setTipo(clienteDTO.tipo());

        return clienteRepository.save(nuovoCliente);
    }

    public Page<Cliente> getClienti(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAll(pageable);
    }

    public Cliente findById(long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + id));
    }

    public Cliente findByIdAndUpdate(long id, Cliente body) {
        Cliente evento = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + id));


        return clienteRepository.save(evento);
    }

    public void findByIdAndDelete(long id) {
        Cliente found = this.findById(id);
        clienteRepository.delete(found);
    }

}