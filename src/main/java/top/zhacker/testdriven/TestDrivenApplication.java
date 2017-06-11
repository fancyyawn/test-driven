package top.zhacker.testdriven;

import com.google.common.eventbus.EventBus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TestDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestDrivenApplication.class, args);
	}
	
	@Bean
	public EventBus eventBus(){
		return new EventBus();
	}
}
