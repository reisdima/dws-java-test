package vincenzo.caio.dws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwsApplication.class, args);
	}

}
