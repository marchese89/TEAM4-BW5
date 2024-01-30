package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Utente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UtenteDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UtenteResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy){
        return utenteService.getUsers(page, size, orderBy);
    }
    @GetMapping("/{id}")
    public Utente findById(@PathVariable long id){
        return utenteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteResponseDTO createUser(@RequestBody @Validated UtenteDTO newUserPayload, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
            Utente nuovoUtente = utenteService.save(newUserPayload);
            return new UtenteResponseDTO(nuovoUtente.getId());

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findByIdAndUpdate(@PathVariable long id, @RequestBody Utente updateUserPayload){
        return utenteService.findbyIdAndUpdate(id,updateUserPayload);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable long id){
        utenteService.findByIdAndDelete(id);
    }

    //Endpoint per upload immagini
    @PostMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable long id) throws IOException {
        return utenteService.uploadPicture(file);
    }
}
