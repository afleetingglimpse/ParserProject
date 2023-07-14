package org.parsingbot.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String vacancyName;
    private String vacancyLink;
    private String vacancyDescription;

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyName='" + vacancyName + '\'' +
                ", vacancyLink='" + vacancyLink + '\'' +
                '}';
    }
}
