package org.abraham.kanbantaskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication(
//        exclude = {
//        org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration.class
//}
)
@EnableKafka
public class KanbanTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbanTaskManagerApplication.class, args);
    }
}
