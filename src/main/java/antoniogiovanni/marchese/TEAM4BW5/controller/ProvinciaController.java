package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.BadRequestException;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/province")
public class ProvinciaController {
    @Autowired private ComuneService comuneService;
    @Autowired private ProvinciaService provinciaService;

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Provincia> getProvince(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) {
        return provinciaService.getProvincia(page, size, orderBy);
    }
    @GetMapping("/noPage")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<Provincia> getProvinceNoPage() {
        return provinciaService.findAllNoPage();
    }
    @GetMapping("/{idProvincia}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Provincia getProvinciaById(@PathVariable Long idProvincia){
        return provinciaService.findById(idProvincia);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Provincia createProvincia(@RequestBody @Validated ProvinciaDTO provinciaDTO, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        }
        return provinciaService.save(provinciaDTO);
    }

    @PutMapping("/{idProvincia}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Provincia modifyProvincia( @RequestBody ProvinciaDTO provinciaDTO,@PathVariable Long idProvincia){
        Provincia found = provinciaService.findById(idProvincia);
        return provinciaService.findByIdAndUpdate(idProvincia, provinciaDTO);
    }
    @DeleteMapping("/{idProvincia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteProvincia(@PathVariable Long idProvincia){
        Provincia found = provinciaService.findById(idProvincia);
        provinciaService.findByIdAndDelete(idProvincia);
    }
    @DeleteMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllProvince(){
        jdbcTemplate.execute("DELETE FROM public.provincia WHERE id >= 1");
        jdbcTemplate.execute("ALTER SEQUENCE provincia_id_seq RESTART WITH 1");
    }
}
