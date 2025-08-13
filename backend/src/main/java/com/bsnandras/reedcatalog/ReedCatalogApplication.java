package com.bsnandras.reedcatalog;

import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.AlreadyBoundException;

@SpringBootApplication
public class ReedCatalogApplication implements CommandLineRunner {
    @Value("${VENDOR_USERNAME}")
    private String vendorUsername;

    @Value("${VENDOR_INITIAL_BALANCE}")
    private int vendorInitialBalance;

    @Autowired
    private PartnerService partnerService;

    public static void main(String[] args) {
        SpringApplication.run(ReedCatalogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Partner admin = Partner.builder()
                .name(vendorUsername)
                .balance(vendorInitialBalance)
                .build();

        try {
            partnerService.addPartner(admin);
        } catch (IllegalArgumentException e) {
            // If the admin partner already exists, we can ignore this exception
            if (!(e.getCause() instanceof AlreadyBoundException)) {
                throw e;
            }
        }
    }
}
