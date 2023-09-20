package org.parsingbot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String authorisation;
    private Boolean isSubscribed;
    private long chatId;
    private LocalDateTime nextSendDate;
    private long nextSendDateDelaySeconds;
    private String state;
    private String vacancyName;
    private long numberOfVacancies;
    private String keywords;
    @ManyToMany
    @JoinTable(
            name = "users_vacancies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private List<Vacancy> userVacancies;

    public void addVacancy(Vacancy vacancy) {
        userVacancies.add(vacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (chatId != user.chatId) return false;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + (int) (chatId ^ (chatId >>> 32));
        return result;
    }
}