package antoniogiovanni.marchese.TEAM4BW5;

import antoniogiovanni.marchese.TEAM4BW5.enums.TipoCliente;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ClienteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Team4Bw5ApplicationTests {

	private ObjectMapper objectMapper = new ObjectMapper();

	private String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzA2NjE4ODc0LCJleHAiOjE3MDcyMjM2NzR9.MFpCpjjxb814BLP0cTQ6ey7FBuR2etwGiEkMX3GAKHQ";
	@Test
	void login() throws Exception {
		String requestBody = "{\"email\": \"marchese@hotmail.com\",\"password\":\"1234\"}";

				given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/auth/login")
				.then()
				.statusCode(200)
				.body(matchesRegex("\\{\"token\":\".{1,}\"\\}"));
	}@Test
	void creaCliente() throws JsonProcessingException {
		String requestBody = objectMapper.writeValueAsString(
				new ClienteDTO("Ragione Sociale", "23458765674",
						"email@email.it", 1000.00,
						"email@pec.it", "7653990887",
						"email@email.it", "Nome Contatto",
						"Cognome Contatto", "7653990887",
						TipoCliente.SRL));

		given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/cliente")
				.then()
				.statusCode(201)
				.body(matchesRegex("\\{\"id\":.*\\}"));
	}

}
