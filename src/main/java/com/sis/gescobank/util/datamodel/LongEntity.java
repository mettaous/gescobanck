package com.sis.gescobank.util.datamodel;

import jakarta.persistence.*;

import java.util.Objects;

import static java.util.Objects.isNull;

@MappedSuperclass
public abstract class LongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        LongEntity that = (LongEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getId() + ")";
    }

    public String toStringForLog() {
        return this.toString();
    }

    public String toStringForException() {
        return this.toString();
    }
}
