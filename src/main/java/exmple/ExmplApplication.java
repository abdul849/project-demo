package exmple;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExmplApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExmplApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper()
	{
		return  new ModelMapper();
	}
}
