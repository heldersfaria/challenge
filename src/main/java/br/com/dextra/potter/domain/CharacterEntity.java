package br.com.dextra.potter.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "character")
public class CharacterEntity extends BaseDomain implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "br.com.dextra.potter.domain.generator.UniqIdentifierGenerator", parameters = {})
    @GeneratedValue(generator = "generator")
    private String id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 300)
    @Column(name = "school", length = 300, nullable = false)
    private String school;

    @NotNull
    @Size(max = 100)
    @Column(name = "patronus", length = 100, nullable = false)
    private String patronus;

    @NotNull
    @Size(max = 36)
    @Column(name = "house", length = 36, nullable = false)
    private String house;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterEntity)) return false;
        CharacterEntity characterEntity = (CharacterEntity) o;
        return Objects.equals(getId(), characterEntity.getId()) &&
                Objects.equals(getDataCriacao(), characterEntity.getDataCriacao()) &&
                Objects.equals(getIdUsuarioCriacao(), characterEntity.getIdUsuarioCriacao()) &&
                Objects.equals(getDataAlteracao(), characterEntity.getDataAlteracao()) &&
                Objects.equals(getIdUsuarioAlteracao(), characterEntity.getIdUsuarioAlteracao()) &&
                Objects.equals(getName(), characterEntity.getName()) &&
                Objects.equals(getSchool(), characterEntity.getSchool()) &&
                Objects.equals(getPatronus(), characterEntity.getPatronus()) &&
                Objects.equals(getHouse(), characterEntity.getHouse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDataCriacao(),
                getIdUsuarioCriacao(),
                getDataAlteracao(),
                getIdUsuarioAlteracao(),
                getName(),
                getSchool(),
                getPatronus(),
                getHouse());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CharacterEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", school='").append(school).append('\'');
        sb.append(", patronus='").append(patronus).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
