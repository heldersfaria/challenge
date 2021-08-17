package br.com.dextra.potter.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class HouseDTO implements Serializable {

    private String id;

    private List<String> values;

    private String mascot;

    private String founder;

    private String name;

    private String school;

    private String houseGhost;

    private List<String> colors;

    private String headOfHouse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
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

    public String getHouseGhost() {
        return houseGhost;
    }

    public void setHouseGhost(String houseGhost) {
        this.houseGhost = houseGhost;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(String headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseDTO)) return false;
        HouseDTO houseDTO = (HouseDTO) o;
        return Objects.equals(getId(), houseDTO.getId()) && Objects.equals(getValues(), houseDTO.getValues()) && Objects.equals(getMascot(), houseDTO.getMascot()) && Objects.equals(getFounder(), houseDTO.getFounder()) && Objects.equals(getName(), houseDTO.getName()) && Objects.equals(getSchool(), houseDTO.getSchool()) && Objects.equals(getHouseGhost(), houseDTO.getHouseGhost()) && Objects.equals(getColors(), houseDTO.getColors()) && Objects.equals(getHeadOfHouse(), houseDTO.getHeadOfHouse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValues(), getMascot(), getFounder(), getName(), getSchool(), getHouseGhost(), getColors(), getHeadOfHouse());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", values=").append(values);
        sb.append(", mascot='").append(mascot).append('\'');
        sb.append(", founder='").append(founder).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", school='").append(school).append('\'');
        sb.append(", houseGhost='").append(houseGhost).append('\'');
        sb.append(", colors=").append(colors);
        sb.append(", headOfHouse='").append(headOfHouse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
