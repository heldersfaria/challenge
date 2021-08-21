package br.com.dextra.potter.builder;


import br.com.dextra.potter.domain.BaseDomain;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;

import static br.com.dextra.potter.utils.ConstantUtils.ID_UNIQUE_IDENTIFIER;
import static java.time.LocalDateTime.now;

public abstract class BaseBuilderIT<T extends BaseDomain> {
    T object;

    public EntityManager entityManager;

    T build() {

        if (object.getDataCriacao() == null) {
            object.setDataCriacao(now());
        }

        if (StringUtils.isEmpty(object.getIdUsuarioCriacao())) {
            object.setIdUsuarioCriacao(ID_UNIQUE_IDENTIFIER);
        }

        return object;
    }

    T build(T object) {

        this.object.setDataCriacao(object.getDataCriacao());
        this.object.setIdUsuarioCriacao(object.getIdUsuarioCriacao());
        this.object.setDataAlteracao(object.getDataAlteracao());
        this.object.setIdUsuarioAlteracao(object.getIdUsuarioAlteracao());

        return object;
    }

    public final T buildPersistFlushDetach() {
        return buildAnd().persistAnd().flushAnd().detach();
    }

    public final T buildPersist() {
        return buildAnd().persist();
    }

    public final T buildPersistFlush() {
        return buildAnd().persistAnd().flush();
    }

    public final BaseBuilderIT<T> buildAnd() {
        build();
        return this;
    }

    public final BaseBuilderIT<T> buildAnd(T object) {
        build(object);
        return this;
    }

    public final BaseBuilderIT<T> persistAnd() {
        persist();
        return this;
    }

    public final  BaseBuilderIT<T> flushAnd() {
        flush();
        return this;
    }

    public T persist() {
        if(entityManager!= null) {
            entityManager.persist(object);
        }
        return object;
    }

    public T detach() {
        if(entityManager!= null) {
            entityManager.detach(object);
        }
        return object;
    }

    public T flush() {
        if(entityManager!= null) {
            entityManager.flush();
        }
        return object;
    }

    final void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <D> D toDto() {
        return null;
    }
}
