package demo.web;

import demo.web.filestorage.DefauleUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableConfigurationProperties({DefauleUser.class})
@MapperScan({"demo.web.mapper"})
@EnableTransactionManagement
@EnableCaching
public class WebApplication extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}

