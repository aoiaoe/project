package com.cz.stream;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestEntity {

    private Integer id;

    private String aid;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return id.equals(that.id) &&
                aid.equals(that.aid) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aid, name);
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", aid='" + aid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
