package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/province")
public class ProvinciaController {
    @Autowired private ComuneService comuneService;
    @Autowired private ProvinciaService provinciaService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Provincia> getProvince(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) {
        return provinciaService.getProvincia(page, size, orderBy);
    }
    @GetMapping("/{idProvincia}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Provincia getProvinciaById(@PathVariable Long idProvincia){
        return provinciaService.findById(idProvincia);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Provincia createProvincia(@RequestBody ProvinciaDTO provinciaDTO){
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
}
