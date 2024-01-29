package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.Service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ComuneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comuni")
public class ComuneController {
    @Autowired
    private ComuneService comuneService;

    @GetMapping
    public Page<Comune> getEventi(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return comuneService.getComune(page, size, orderBy);
    }

    @GetMapping("/{idComune}")
    public Comune getComuneById(@PathVariable int idComune){
        return comuneService.findById(idComune);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComuneDTO createComune(ComuneDTO comuneDTO){
        Comune comune = comuneService.save(comuneDTO);
        return new ComuneDTO(comune.getProgressivoDelComune());
    }

    @PutMapping("/{idComune}")
    public ComuneDTO modifyComune( ComuneDTO comuneDTO,@PathVariable int idComune){
        Comune found = comuneService.findById(idComune);

        Comune comune = comuneService.findByIdAndUpdate(idComune,comuneDTO);
        return new ComuneDTO(comune.getProgressivoDelComune());
    }
    @DeleteMapping("/{idIndirizzo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComune(@PathVariable int idComune){
        Comune found = comuneService.findById(idComune);
        comuneService.findByIdAndDelete(idComune);
    }

}
