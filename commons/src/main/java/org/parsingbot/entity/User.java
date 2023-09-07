package org.parsingbot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String authorisation;
    private String isSubscribed;
    private int chatId;

    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_vacancies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> userVacancies;

    public void addVacancy(Vacancy vacancy) {
        userVacancies.add(vacancy);
    }
}