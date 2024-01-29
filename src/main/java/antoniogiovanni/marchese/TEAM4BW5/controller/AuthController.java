package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.Service.AuthService;
import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Utente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UserLoginDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UserLoginResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    antoniogiovanni.marchese.TEAM4BW5.service.UtenteService utenteService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String accessToken = authService.authenticateUser(body);
        return new UserLoginResponseDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO createUser(@RequestBody @Validated UtenteDTO newUserPayload, BindingResult validation) {
        // Per completare la validazione devo in qualche maniera fare un controllo del tipo: se ci sono errori -> manda risposta con 400 Bad Request
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().toList().toString()); // L'eccezione arriverà agli error handlers tra i quali c'è quello che manderà la risposta con status code 400
        } else {
            Utente newUser = utenteService.save(newUserPayload);

            return new ResponseDTO(newUser.getId());
        }
    }
}
