package br.com.dextra.potter.domain.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

import static java.util.UUID.randomUUID;

public class UniqIdentifierGenerator implements IdentifierGenerator, Configurable {

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return randomUUID().toString();
    }
}
