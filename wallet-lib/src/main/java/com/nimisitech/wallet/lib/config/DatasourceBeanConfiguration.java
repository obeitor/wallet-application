package com.nimisitech.wallet.lib.config;



import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.nimisitech.wallet.lib")
@EntityScan(basePackages = "com.nimisitech.wallet.lib")
@EnableTransactionManagement
public class DatasourceBeanConfiguration {

}
