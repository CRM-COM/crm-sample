package crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

import crm.config.MemberCRMStream;
import crm.config.MemberStream;
import crm.config.OrganisationStream;

@SpringBootApplication
@EnableBinding({MemberStream.class, OrganisationStream.class, MemberCRMStream.class})
@EnableConfigurationProperties
@EnableScheduling
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
