package antoniogiovanni.marchese.TEAM4BW5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Scanner;

@RestController
@RequestMapping("/uploadCsv")
public class UploadCSVController {
    @PostMapping
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
                String nomeComune = columns[2];
                String provincia = columns[3];
                System.out.println(line);
            }

        } catch (IOException e) {
            return;
        }
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCSVProvince(@RequestParam("file") MultipartFile file) {
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
                System.out.println(line);
            }

        } catch (IOException e) {
            return;
        }
    }
}
