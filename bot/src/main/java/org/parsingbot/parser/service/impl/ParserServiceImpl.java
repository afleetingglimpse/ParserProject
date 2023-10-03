package org.parsingbot.parser.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.parser.utils.VacancyPredicates;
import org.parsingbot.service.Parser;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private static final String VACANCY_NAME_PARAMETER = "vacancyName";
    private static final String NUMBER_OF_VACANCIES_PARAMETER = "numberOfVacancies";
    private static final String KEYWORDS_PARAMETER = "keywords";

    private final Parser parser;
    private final VacancyService vacancyService;

    @Override
    public List<SendMessage> getVacanciesMessageList(Event event) {
        return getVacanciesMessageListFromVacanciesList(event.getChatId(), getVacancies(event));
    }

    @Override
    public List<SendMessage> getVacanciesMessageList(User user, Map<String, String> parsingParameters) {
        return getVacanciesMessageListFromVacanciesList(user.getChatId(), getVacancies(user, parsingParameters));
    }

    @Override
    public SendMessage getVacanciesSingleMessage(Event event) {
        List<String> vacanciesLinks = getVacancies(event).stream().map(Vacancy::getVacancyLink).toList();
        return BotUtils.createMessage(event.getChatId(), String.join("\n", vacanciesLinks));
    }

    private List<SendMessage> getVacanciesMessageListFromVacanciesList(Long chatId, List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(vacancy -> BotUtils.createMessage(chatId, vacancy.getVacancyLink()))
                .collect(Collectors.toList());
    }

    public List<Vacancy> getVacancies(User user, Map<String, String> parsingParameters) {
        String vacancyName = parsingParameters.get(VACANCY_NAME_PARAMETER);
        long numberOfVacancies = Long.parseLong(parsingParameters.get(NUMBER_OF_VACANCIES_PARAMETER));
        String keywords = parsingParameters.get(KEYWORDS_PARAMETER);

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);
        List<Vacancy> vacancies = parser.parse(vacancyName, (int) numberOfVacancies, VacancyPredicates.uniqueVacancy(userVacancies));
        user.addAllVacancies(vacancies);

        vacancyService.save(vacancies);
        return vacancies;
    }

    public List<Vacancy> getVacancies(Event event) {
        User user = event.getUser();
        Map<String, String> parsingParameters = Map.of(
                VACANCY_NAME_PARAMETER, user.getVacancyName(),
                NUMBER_OF_VACANCIES_PARAMETER, String.valueOf(user.getNumberOfVacancies()),
                KEYWORDS_PARAMETER, user.getKeywords()
        );
        return getVacancies(user, parsingParameters);
    }
}
