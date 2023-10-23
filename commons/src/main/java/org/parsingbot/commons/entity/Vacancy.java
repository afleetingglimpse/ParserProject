package org.parsingbot.commons.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String vacancyName;
    private String vacancyLink;
    private String vacancyDescription;

    @ManyToMany(mappedBy = "userVacancies")
    private List<User> vacancyFollowers;

    public void addUser(User user) {
        vacancyFollowers.add(user);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyName='" + vacancyName + '\'' +
                ", vacancyLink='" + vacancyLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacancy vacancy = (Vacancy) o;

        return Objects.equals(vacancyLink, vacancy.vacancyLink);
    }

    @Override
    public int hashCode() {
        return vacancyLink != null ? vacancyLink.hashCode() : 0;
    }
}