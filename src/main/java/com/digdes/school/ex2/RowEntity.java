package com.digdes.school.ex2;

import java.util.Objects;

public class RowEntity {
    Long id;
    String lastName;
    Long age;
    Double cost;
    Boolean active;

    public RowEntity() {

    }

    public RowEntity(Long id, String lastName, Long age, Double cost, Boolean active) {
        this.id = id;
        this.lastName = lastName;
        this.age = age;
        this.cost = cost;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowEntity rowEntity = (RowEntity) o;
        return Objects.equals(id, rowEntity.id) && Objects.equals(lastName, rowEntity.lastName) && Objects.equals(age, rowEntity.age) && Objects.equals(cost, rowEntity.cost) && Objects.equals(active, rowEntity.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, age, cost, active);
    }

    @Override
    public String toString() {
        return "RowEntity{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", cost=" + cost +
                ", active=" + active +
                '}';
    }
}
