import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



public class Test {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8090/hello";
        ResponseEntity<String> toto
                = restTemplate.getForEntity(fooResourceUrl + "/", String.class);
        // assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("la réponse de l'appel de l'API est" + toto.getBody());

    }

}
