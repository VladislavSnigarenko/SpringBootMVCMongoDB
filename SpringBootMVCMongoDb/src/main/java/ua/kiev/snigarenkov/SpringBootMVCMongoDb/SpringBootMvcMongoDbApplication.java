package ua.kiev.snigarenkov.SpringBootMVCMongoDb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.services.StudentService;

@SpringBootApplication
public class SpringBootMvcMongoDbApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(SpringBootMvcMongoDbApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING : Spring boot application starting");
		new SpringApplicationBuilder(SpringBootMvcMongoDbApplication.class)
		.bannerMode(Banner.Mode.OFF)
		.run(args);

		LOG.info("STOPPED  : Spring boot application stopped");
	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING : command line runner");
	}

}
