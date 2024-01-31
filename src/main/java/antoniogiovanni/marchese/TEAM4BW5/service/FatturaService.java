package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.enums.StatoFattura;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.model.Fattura;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewFatturaDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteService clienteService;


    public Page<Fattura> getFattura(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return fatturaRepository.findAll(pageable);
    }


    public Fattura findById(Long id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(Long id) {
        Fattura found = this.findById(id);
        fatturaRepository.delete(found);
    }

    public Fattura save(NewFatturaDTO body) {
        Cliente cliente = clienteService.findById(body.idCliente());
        Fattura newFattura = new Fattura();
        newFattura.setCliente(clienteService.findById(body.idCliente()));
        newFattura.setData(body.data());
        newFattura.setNumero(fatturaRepository.findAll().stream().count() + 1);
        newFattura.setImporto(body.importo());
        newFattura.setStatoFattura(body.statoFattura());
        cliente.setDataUltimoContatto(LocalDate.now());
        return fatturaRepository.save(newFattura);
    }

    public Fattura findByIdAndUpdate(Long id, NewFatturaDTO body) {
        Cliente cliente = clienteService.findById(body.idCliente());
        Fattura found = this.findById(id);
        found.setCliente(clienteService.findById(body.idCliente()));
        found.setData(body.data());
        found.setImporto(body.importo());
        found.setStatoFattura(body.statoFattura());
        cliente.setDataUltimoContatto(LocalDate.now());
        return fatturaRepository.save(found);
    }

    public List<Fattura> getFatturaFiltered(Long clienteId, int anno, StatoFattura statoFattura, LocalDate data, LocalDate inizio, LocalDate fine, Double importoMin, Double importoMax, Integer year, Double minImporto, Double maxImporto) {
        LocalDate startOfYear = (year != null) ? LocalDate.of(year, 1, 1) : null;
        LocalDate endOfYear = (year != null) ? LocalDate.of(year, 12, 31) : null;

        return (clienteId != null && anno != 0) ?
                fatturaRepository.findByClienteAndDataBetweenOrderByDataAsc(clienteService.findById(clienteId), LocalDate.of(anno, 1, 1), LocalDate.of(anno, 12, 31))
                : (clienteId != null) ?
                fatturaRepository.findByClienteOrderByDataAsc(clienteService.findById(clienteId))
                : (statoFattura != null) ?
                fatturaRepository.findByStatoFatturaOrderByDataAsc(statoFattura)
                : (data != null) ?
                fatturaRepository.findByDataOrderByDataAsc(data)
                : (startOfYear != null && endOfYear != null) ?
                fatturaRepository.findByDataBetween(startOfYear, endOfYear)
                : (inizio != null && fine != null) ?
                (importoMin != null && importoMax != null) ?
                        fatturaRepository.findByDataBetweenAndImportoBetweenOrderByDataAsc(inizio, fine, importoMin, importoMax)
                        : fatturaRepository.findByDataBetweenOrderByDataAsc(inizio, fine)
                : (minImporto != null && maxImporto != null) ?
                fatturaRepository.findByImportoBetween(minImporto, maxImporto)
                : fatturaRepository.findAll();
    }

//    public List<Fattura> trovaFattureAnnuali(Long clienteId, int anno) {
//        Cliente cliente = clienteService.findById(clienteId);
//        LocalDate inizioAnno = LocalDate.of(anno, 1, 1);
//        LocalDate fineAnno = LocalDate.of(anno, 12, 31);
//
//        return fatturaRepository.findByClienteAndDataBetweenOrderByDataAsc(cliente, inizioAnno, fineAnno);
//
//    }
//
//    public List<Fattura> trovaFatturePerCliente(Long clienteId) {
//        Cliente cliente = clienteService.findById(clienteId);
//        return fatturaRepository.findByClienteOrderByDataAsc(cliente);
//    }
//
//    public List<Fattura> trovaFatturePerStato(StatoFattura statoFattura) {
//        return fatturaRepository.findByStatoFatturaOrderByDataAsc(statoFattura);
//    }
//
//    public List<Fattura> trovaFatturePerData(LocalDate data) {
//        return fatturaRepository.findByDataOrderByDataAsc(data);
//    }
//
//    public List<Fattura> trovaFatturePerIntervalloData(LocalDate inizio, LocalDate fine) {
//        return fatturaRepository.findByDataBetweenOrderByDataAsc(inizio, fine);
//    }
//
//    public List<Fattura> trovaFatturePerIntervalloDataEImporto(LocalDate inizio, LocalDate fine, Double importoMin, Double importoMax) {
//        return fatturaRepository.findByDataBetweenAndImportoBetweenOrderByDataAsc(inizio, fine, importoMin, importoMax);
//    }
//
//    public List<Fattura> getFattureByYear(Integer year) {
//        LocalDate startOfYear = LocalDate.of(year, 1, 1);
//        LocalDate endOfYear = LocalDate.of(year, 12, 31);
//        return fatturaRepository.findByDataBetween(startOfYear, endOfYear);
//    }
//
//    public List<Fattura> getFattureByImportoRange(Double minImporto, Double maxImporto) {
//        return fatturaRepository.findByImportoBetween(minImporto, maxImporto);
//    }
// vedi

}
