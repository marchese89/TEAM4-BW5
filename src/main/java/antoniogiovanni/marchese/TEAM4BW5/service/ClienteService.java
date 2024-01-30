package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
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

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return clienteRepository.findAll(pageable);
    }

    public Cliente findById(long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + id));
    }

    public Cliente findByIdAndUpdate(long id, ClienteDTO body) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con ID: " + id));
        cliente.setRagioneSociale(body.ragioneSociale());
        cliente.setPartitaIva(body.partitaIva());
        cliente.setEmail(body.email());
        cliente.setDataUltimoContatto(LocalDate.now());
        cliente.setFatturatoAnnuale(body.fatturatoAnnuale());
        cliente.setPec(body.pec());
        cliente.setTelefono(body.telefono());
        if (body.emailContatto() != null) {
            cliente.setEmailContatto(body.emailContatto());
        }
        if (body.nomeContatto() != null) {
            cliente.setNomeContatto(body.nomeContatto());
        }
        if (body.cognomeContatto() != null) {
            cliente.setCognomeContatto(body.cognomeContatto());
        }

        if (body.telefonoContatto() != null) {
            cliente.setTelefonoContatto(body.telefonoContatto());
        }
        cliente.setTipo(body.tipo());

        return clienteRepository.save(cliente);
    }

    public void findByIdAndDelete(long id) {
        Cliente found = this.findById(id);
        clienteRepository.delete(found);
    }

    //----------------------------------------------- ORDER BY NOMECONTATTO ASC

    public Page<Cliente> getClienteSortedByNomeContattoAsc(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return clienteRepository.findAllByOrderByNomeContattoAsc(pageable);
    }

//    public List<Cliente> getClienteSortedByNomeContatto() {
//        return clienteRepository.findAll(Sort.by("nomeContatto"));
//    }


    //----------------------------------------------- ORDER BY NOMECONTATTO DESC

    public Page<Cliente> getClienteSortedByNomeContattoDesc(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        return clienteRepository.findAllByOrderByNomeContattoDesc(pageable);
    }

    //----------------------------------------------- ORDER BY FATTURATOANNUALE ASC

    public Page<Cliente> getClienteSortedByFatturatoAnnualeAsc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByFatturatoAnnualeAsc(pageable);
    }

    //----------------------------------------------- ORDER BY FATTURATOANNUALE DESC

    public Page<Cliente> getClienteSortedByFatturatoAnnualeDesc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByFatturatoAnnualeDesc(pageable);
    }

    //----------------------------------------------- ORDER BY DATAINSERIMENTO ASC

    public Page<Cliente> getClienteSortedByDataInserimentoAsc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByDataInserimentoAsc(pageable);
    }

    //----------------------------------------------- ORDER BY DATAINSERIMENTO DESC

    public Page<Cliente> getClienteSortedByDataInserimentoDesc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByDataInserimentoDesc(pageable);
    }

    //----------------------------------------------- ORDER BY DATAULTIMOCONTATTO ASC

    public Page<Cliente> getClienteSortedByDataUltimoContattoAsc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByDataUltimoContattoAsc(pageable);
    }

    //----------------------------------------------- ORDER BY DATAULTIMOCONTATTO DESC

    public Page<Cliente> getClienteSortedByDataUltimoContattoDesc(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAllByOrderByDataUltimoContattoDesc(pageable);
    }

    //-----------------------FILTER
    //----------------------------------------------- FILTER BY FATTURATO MAGGIORE

    public Page<Cliente> findByFatturatoAnnualeGreaterThanEqual(Double fatturato, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return clienteRepository.findByFatturatoAnnualeGreaterThanEqual(fatturato, pageable);
    }

    //----------------------------------------------- FILTER BY FATTURATO MINORE
    public Page<Cliente> findByFatturatoAnnualeLessThanEqual(Double fatturato, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return clienteRepository.findByFatturatoAnnualeLessThanEqual(fatturato, pageable);
    }

    //----------------------------------------------- FILTER BY INSERIMENTO AFTER

    public Page<Cliente> findByDataInserimentoAfter(LocalDate dataInserimento, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findByDataInserimentoAfter(dataInserimento, pageable);
    }
}
