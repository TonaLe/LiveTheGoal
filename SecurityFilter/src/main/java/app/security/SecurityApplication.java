package app.security;

import app.security.Event.Consumer.SecurityKafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

    @Autowired  
    private SecurityKafkaConsumer securityKafkaConsumer;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
