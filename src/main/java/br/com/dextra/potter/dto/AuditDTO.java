package br.com.dextra.potter.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AuditDTO implements Serializable {

    private LocalDateTime dataCriacao;

    private String idUsuarioCriacao;

    private LocalDateTime dataAlteracao;

    private String idUsuarioAlteracao;

    public String getIdUsuarioCriacao() {
        return idUsuarioCriacao;
    }

    public void setIdUsuarioCriacao(String idUsuarioCriacao) {
        this.idUsuarioCriacao = idUsuarioCriacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getIdUsuarioAlteracao() {
        return idUsuarioAlteracao;
    }

    public void setIdUsuarioAlteracao(String idUsuarioAlteracao) {
        this.idUsuarioAlteracao = idUsuarioAlteracao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public abstract Object getId();

    public abstract void setId(String id);
}
