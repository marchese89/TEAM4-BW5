package antoniogiovanni.marchese.TEAM4BW5.controller;

import antoniogiovanni.marchese.TEAM4BW5.exceptions.NotFoundException;
import antoniogiovanni.marchese.TEAM4BW5.model.Comune;
import antoniogiovanni.marchese.TEAM4BW5.model.Provincia;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ProvinciaDTO;
import antoniogiovanni.marchese.TEAM4BW5.service.ComuneService;
import antoniogiovanni.marchese.TEAM4BW5.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/uploadCsv")
public class UploadCSVController {


    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    private HashMap<String,String> corrispProvince;

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public UploadCSVController(){
        corrispProvince = new HashMap<>();
        corrispProvince.put("Vibo Valentia","Vibo-Valentia");
        corrispProvince.put("Reggio Calabria","Reggio-Calabria");
        corrispProvince.put("Ascoli Piceno","Ascoli-Piceno");
        corrispProvince.put("Pesaro e Urbino","Pesaro-Urbino");
        corrispProvince.put("Forlì-Cesena","Forli-Cesena");
        corrispProvince.put("Reggio nell'Emilia", "Reggio-Emilia");
        corrispProvince.put("La Spezia","La-Spezia");
        corrispProvince.put("Bolzano/Bozen","Bolzano");
        corrispProvince.put("Monza e della Brianza","Monza-Brianza");
        corrispProvince.put("Valle d'Aosta/Vallée d'Aoste","Aosta");
        corrispProvince.put("Verbano-Cusio-Ossola","Verbania");
    }

    @PostMapping("/comuni")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSVComuni(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            return;
        }
        jdbcTemplate.execute("DELETE FROM public.comune WHERE id >= 1");
        jdbcTemplate.execute("ALTER SEQUENCE comune_id_seq RESTART WITH 1");
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

                        String nomeComune = columns[2];
                        String provincia = columns[3];
                        Provincia provincia1 = null;
                        try {
                            provincia1 = provinciaService.findByName(provincia);
                        } catch (NotFoundException e) {
//                            System.out.println("provincia non presente");
//                            System.out.println(provincia);
                            try {
                                provincia1 = provinciaService.findByName(corrispProvince.get(provincia));
                            }catch (NotFoundException e2){
                                provinciaService.save(new ProvinciaDTO("",provincia,""));
                                provincia1 = provinciaService.findByName(corrispProvince.get(provincia));
                                //throw new NotFoundException("provincia non trovata"+provincia);
                            }

                        }
                        if (provincia1 != null) {
                            Comune comune = new Comune();
                            comune.setProvincia(provincia1);
                            comune.setDenominazione(nomeComune);
                            comune.setProgressivoDelComune(progressivoComune != null? progressivoComune:-1);
                            comuneService.save(comune);
                        }else{
                            System.out.println("provincia null");
                        }

                }

        } catch (IOException e) {
            return;
        }
        //cancelliamo le province senza comuni
        jdbcTemplate.execute("DELETE FROM provincia WHERE id NOT IN (SELECT DISTINCT provincia FROM Comune)");
    }
    @PostMapping("/province")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSVProvince(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return;
        }
        jdbcTemplate.execute("DELETE FROM public.provincia WHERE id >= 1");
        jdbcTemplate.execute("ALTER SEQUENCE provincia_id_seq RESTART WITH 1");
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
