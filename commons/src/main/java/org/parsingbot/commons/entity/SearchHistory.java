package org.parsingbot.commons.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.util.Objects;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "search_history")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String vacancyName;
    private Integer numberOfVacancies;
    private String keywords;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchHistory that = (SearchHistory) o;

        if (!Objects.equals(vacancyName, that.vacancyName)) return false;
        if (!Objects.equals(numberOfVacancies, that.numberOfVacancies))
            return false;
        return Objects.equals(keywords, that.keywords);
    }

    @Override
    public int hashCode() {
        int result = vacancyName != null ? vacancyName.hashCode() : 0;
        result = 31 * result + (numberOfVacancies != null ? numberOfVacancies.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        return result;
    }
}
