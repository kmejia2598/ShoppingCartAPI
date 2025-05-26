package org.shoppingcart;

import org.shoppingcart.component.MemoryDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoppingCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApplication.class, args);
    }

    /** Definition of a CommandLineRunner bean to store client information directly from the https://fakestoreapi.com/ API once the Spring application context is fully initialized. */
    @Bean
    public CommandLineRunner commandLineRunner(MemoryDB memoryDB) {
        return args -> {
            memoryDB.addAllClients();
            memoryDB.addAllProducts();
        };
    }
}