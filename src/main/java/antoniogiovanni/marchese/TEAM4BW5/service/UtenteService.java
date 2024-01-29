package antoniogiovanni.marchese.TEAM4BW5.service;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Utente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UtenteDTO;
import antoniogiovanni.marchese.TEAM4BW5.repository.UtenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UtenteDAO utenteDAO;
    public Page<Utente> getUsers(int page, int size, String orderBy){
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return utenteDAO.findAll(pageable);
    }

    public Utente save(UtenteDTO body){
        utenteDAO.findByEmail(body.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email "+utente.getEmail()+ " è già in uso!");
        });
        Utente nuovoUtente = new Utente();
        nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + nuovoUtente.getNome() + "+" + nuovoUtente.getCognome());
        nuovoUtente.setNome(body.nome());
        nuovoUtente.setCognome(body.cognome());
        nuovoUtente.setEmail(body.email());
        nuovoUtente.setUsername(body.username());
        nuovoUtente.setPassword(body.password());
        return utenteDAO.save(nuovoUtente);
    }

    public Utente findById(long id){
        return utenteDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public void findByIdAndDelete(long id){
        Utente found = this.findById(id);
        utenteDAO.delete(found);
    }

    public Utente findbyIdAndUpdate(long id, Utente body){
        Utente found = this.findById(id);
        found.setCognome(body.getCognome());
        found.setNome(body.getNome());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        return utenteDAO.save(found);

    }
}