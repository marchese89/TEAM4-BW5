package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ComuneDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ResponseDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comuni")
public class ComuneController {
    @Autowired
    private ComuneService comuneService;

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Comune> getComuni(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return comuneService.getComune(page, size, orderBy);
    }
    @GetMapping("/byProvincia/{idProvincia}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<Comune> getComuniByProvincia(@PathVariable Long idProvincia) {
        return comuneService.getByProvincia(idProvincia);
    }

    @GetMapping("/{idComune}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Comune getComuneById(@PathVariable Long idComune){
        return comuneService.findById(idComune);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Comune createComune(Comune comune){
        return comuneService.save(comune);
    }

    @PutMapping("/{idComune}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ComuneDTO modifyComune( ComuneDTO comuneDTO,@PathVariable Long idComune){
        Comune found = comuneService.findById(idComune);

        Comune comune = comuneService.findByIdAndUpdate(idComune,comuneDTO);
        return new ComuneDTO(comuneDTO.codiceProvincia(), comuneDTO.denominazione());
    }
    @DeleteMapping("/{idComune}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComune(@PathVariable Long idComune){
        Comune found = comuneService.findById(idComune);
        comuneService.findByIdAndDelete(idComune);
    }

    @GetMapping("/idProvincia/{idComune}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseDTO getProvinciaByIdComune(@PathVariable Long idComune){
        return new ResponseDTO(comuneService.getIdProvByComune(idComune));
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllComuni(){
        jdbcTemplate.execute("DELETE FROM public.comune WHERE id >= 1");
        jdbcTemplate.execute("ALTER SEQUENCE comune_id_seq RESTART WITH 1");
    }
}
