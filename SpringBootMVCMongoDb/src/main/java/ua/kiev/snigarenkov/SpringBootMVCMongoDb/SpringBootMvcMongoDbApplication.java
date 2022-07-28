// в java пакеты принято писать малыми буквами
package ua.kiev.snigarenkov.SpringBootMVCMongoDb;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.services.StudentService;

@SpringBootApplication
@Log4j2
public class SpringBootMvcMongoDbApplication implements CommandLineRunner {

	// java notation private static final
	private static final Logger LOG = LoggerFactory.getLogger(SpringBootMvcMongoDbApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING : Spring boot application starting");
		log.info("it's log from lombok. we can use this log without declare field LOG, only use annotation @Log4j2 from lombok");
		new SpringApplicationBuilder(SpringBootMvcMongoDbApplication.class)
		.bannerMode(Banner.Mode.OFF)
		.run(args);

		LOG.info("STOPPED  : Spring boot application stopped");
	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING : command line runner");
	}
	
	@Bean
	CommandLineRunner init(StudentService studentService){
		return args -> {
			LOG.info("EXECUTING : studentService init");
			studentService.init();
		};
	}

}
