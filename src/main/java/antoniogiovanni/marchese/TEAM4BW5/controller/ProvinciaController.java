package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.Service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.Service.ProvinciaService;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/province")
public class ProvinciaController {
    @Autowired private ComuneService comuneService;
    @Autowired private ProvinciaService provinciaService;

    @GetMapping
    public Page<Provincia> getProvince(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) {
        return provinciaService.getProvincia(page, size, orderBy);
    }
    @GetMapping("/{idProvincia}")
    public Provincia getProvinciaById(@PathVariable UUID idProvincia){
        return provinciaService.findById(idProvincia);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProvinciaDTO createProvincia(@RequestBody ProvinciaDTO provinciaDTO){
        Provincia provincia = provinciaService.save(provinciaDTO);
        return new ProvinciaDTO(provincia.getId());
    }

    @PutMapping("/{idProvincia}")
    public ProvinciaDTO modifyProvincia( @RequestBody ProvinciaDTO provinciaDTO,@PathVariable UUID idProvincia){
        Provincia found = provinciaService.findById(idProvincia);

        Provincia provincia = provinciaService.findByIdAndUpdate(idProvincia, provinciaDTO);
        return new ProvinciaDTO(provincia.getId());
    }
    @DeleteMapping("/{idProvincia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvincia(@PathVariable UUID idProvincia){
        Provincia found = provinciaService.findById(idProvincia);
        provinciaService.findByIdAndDelete(idProvincia);
    }
}
