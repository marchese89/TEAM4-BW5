package antoniogiovanni.marchese.TEAM4BW5;

import antoniogiovanni.marchese.TEAM4BW5.enums.TipoCliente;
import antoniogiovanni.marchese.TEAM4BW5.enums.TipoIndirizzo;
import antoniogiovanni.marchese.TEAM4BW5.payloads.ClienteDTO;
import antoniogiovanni.marchese.TEAM4BW5.payloads.NewIndirizzoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
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

	private Long clienteCreato = null;
	private Long indirizzoCreato = null;
	@Test
	void loginOK() throws Exception {
		String requestBody = "{\"email\": \"marchese@hotmail.com\",\"password\":\"1234\"}";

				given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/auth/login")
				.then()
				.statusCode(200)
				.body(matchesRegex("\\{\"token\":\".{1,}\"\\}"));
	}
	@Test
	void loginNo() throws Exception {
		String requestBody = "{\"email\": \"marchese@hotmail.com\",\"password\":\"1\"}";

		given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/auth/login")
				.then()
				.statusCode(401);
				//.body(matchesRegex("\\{\"token\":\".{1,}\"\\}"));
	}
	@Test
	void creaCliente() throws JsonProcessingException {
		String requestBody = objectMapper.writeValueAsString(
				new ClienteDTO("Ragione Sociale", "23458765674",
						"email@email.it", 1000.00,
						"email@pec.it", "7653990887",
						"email@email.it", "Nome Contatto",
						"Cognome Contatto", "7653990887",
						TipoCliente.SRL));

		Response response = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/cliente");

				response.then().assertThat().statusCode(201);
				response.then().assertThat().body(matchesRegex("\\{\"id\":.*\\}"));
		JsonNode jsonNode = objectMapper.readTree(response.body().asString());

		clienteCreato = Long.parseLong(jsonNode.get("id").toString());

	}

	@Test
	void creaClienteNo() {
		String requestBody = "{}";

		Response response = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/cliente");
				response.then().assertThat().statusCode(400);
	}

	@Test
	void getTuttiClienti(){
		Response response = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.when()
				.get("http://localhost:3001/cliente");
		response.then().assertThat().statusCode(200);
		System.out.println(response.body().asString());
	}

	@Test
	void creaIndirizzo() throws JsonProcessingException {
		String requestBody = objectMapper.writeValueAsString(
				new NewIndirizzoDTO("via aaa",
						"1/A",
						"Localita",
						45622,
						7903,
						1,
						TipoIndirizzo.SEDE_OPERATIVA));

		Response response = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/indirizzi");
		response.then().assertThat().statusCode(anyOf(equalTo(401), equalTo(201)));
		System.out.println("Status Code: "+response.statusCode());
	}
	@Test
	void creareGetAndDeleteIndirizzo() throws JsonProcessingException {
		//creo un nuovo indirizzo
		String requestBody = objectMapper.writeValueAsString(
				new NewIndirizzoDTO("via aaa",
						"1/A",
						"Localita",
						45622,
						7903,
						1,
						TipoIndirizzo.SEDE_OPERATIVA));

		Response response = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("http://localhost:3001/indirizzi");
		response.then().assertThat().statusCode(anyOf(equalTo(401), equalTo(201)));
		//prendo l'indirizzo appena creato
		if(response.statusCode() == 201) {
			Long idIndirizzo = objectMapper.readTree(response.body().asString()).get("id").asLong();
			System.out.println("indirizzo appena creato: " + idIndirizzo);
			Response response2 = given()
					.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
					.contentType("application/json")
					.when()
					.get("http://localhost:3001/indirizzi/" + idIndirizzo);
			response2.then().assertThat().statusCode(200);

		//cancello l'indirizzo appena creato
		Response response3 = given()
				.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
				.contentType("application/json")
				.when()
				.delete("http://localhost:3001/indirizzi/"+idIndirizzo);
		response3.then().assertThat().statusCode(204);
		}
		if(response.statusCode() == 401){
			//prendo tutti gli indirizzi
			Response response3 = given()
					.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
					.contentType("application/json")
					.when()
					.get("http://localhost:3001/indirizzi" );
			response3.then().assertThat().statusCode(200);
			JsonNode jsonNode = objectMapper.readTree(response3.body().asString()).get("content");
			//elimino il primo indirizzo
			Response response4 = given()
					.header("Authorization", "Bearer " + bearerToken) // Aggiungi il token di autenticazione Bearer
					.contentType("application/json")
					.when()
					.delete("http://localhost:3001/indirizzi/"+jsonNode.get(0).get("id").asLong());
			response4.then().assertThat().statusCode(204);
		}

	}

}
