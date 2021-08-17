package br.com.dextra.potter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("br.com.dextra.potter.repository.h2")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
