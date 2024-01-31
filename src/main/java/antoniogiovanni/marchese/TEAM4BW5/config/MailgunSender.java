package antoniogiovanni.marchese.TEAM4BW5.config;

import antoniogiovanni.marchese.TEAM4BW5.model.Cliente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.EmailDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String mailgunApiKey;
    private String mailgunDomainname;

    public MailgunSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                         @Value("${mailgun.domainname}") String mailgunDomainName) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
    }
    public void sendEmail(Cliente cliente, EmailDTO emailDTO){
        //inserito la dipendenza unirest-java nel pom.xml
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", "Carmen Caniglia <carmencaniglia@gmail.com>")
                .queryString("to", cliente.getEmail())
                .queryString("subject", "Registrazione avvenuta con successo!")
                .queryString("text", "Complimenti per esserti registrato!")
                .asJson();
    }
}
