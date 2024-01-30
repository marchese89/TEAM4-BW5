package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/uploadCsv")
public class UploadCSVController {


    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @PostMapping("/comuni")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSVComuni(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            return;
        }
        try (Scanner scanner = new Scanner(file.getInputStream())) {
            scanner.nextLine();//togliamo la prima riga
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(";");
                Long progressivoComune = null;
                try {
                    progressivoComune = Long.parseLong(columns[1]);
                } catch (NumberFormatException e) {

                }
                    if (progressivoComune != null) {
                        String nomeComune = columns[2];
                        String provincia = columns[3];
                        Provincia provincia1 = null;
                        try {
                            provincia1 = provinciaService.findByName(provincia);
                        } catch (NotFoundException e) {

                        }
                        if (provincia1 != null) {
                            Comune comune = new Comune();
                            comune.setProvincia(provincia1);
                            comune.setDenominazione(nomeComune);
                            comune.setProgressivoDelComune(progressivoComune);
                            comuneService.save(comune);
                        }
                    }
                }

        } catch (IOException e) {
            return;
        }
    }
    @PostMapping("/province")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSVProvince(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return;
        }

        try (Scanner scanner = new Scanner(file.getInputStream())) {
            scanner.nextLine();//togliamo la prima riga
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(";");
                String sigla = columns[0];
                String provincia = columns[1];
                String regione = columns[2];
                provinciaService.save(new ProvinciaDTO(sigla,provincia,regione));
            }

        } catch (IOException e) {
            return;
        }
    }
}
