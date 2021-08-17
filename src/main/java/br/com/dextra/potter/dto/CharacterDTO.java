package br.com.dextra.potter.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class CharacterDTO extends AuditDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 300)
    private String school;

    @NotNull
    @Size(max = 100)
    private String patronus;

    @NotNull
    @Size(max = 36)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterDTO)) return false;
        CharacterDTO that = (CharacterDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(school, that.school) &&
                Objects.equals(patronus, that.patronus) &&
                Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, school, patronus, house);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CharacterDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", school='").append(school).append('\'');
        sb.append(", patronus='").append(patronus).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
