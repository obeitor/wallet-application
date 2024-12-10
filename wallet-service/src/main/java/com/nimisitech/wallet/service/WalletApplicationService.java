package com.nimisitech.wallet.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nimisitech.wallet")
@OpenAPIDefinition(info = @Info(title = "Wallet APIs", version = "1.0", description = "Wallet API Information"))
public class WalletApplicationService {

    public static void main(String[] args) {
        SpringApplication.run(WalletApplicationService.class,args);
    }

}
