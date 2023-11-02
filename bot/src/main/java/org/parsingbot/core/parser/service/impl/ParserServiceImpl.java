package org.parsingbot.core.parser.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.SearchHistory;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.entity.Vacancy;
import org.parsingbot.core.parser.service.ParserService;
import org.parsingbot.core.parser.utils.VacancyPredicates;
import org.parsingbot.parser.service.Parser;
import org.parsingbot.commons.service.VacancyService;
import org.parsingbot.core.util.BotUtils;
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

        List<Vacancy> userVacancies = user.getUserVacancies();
        List<Vacancy> vacancies = parser.parse(
                vacancyName,
                (int) numberOfVacancies,
                VacancyPredicates.uniqueAndNonAddVacancy(userVacancies));
        user.addAllVacancies(vacancies);

        vacancyService.save(vacancies);
        return vacancies;
    }

    public List<Vacancy> getVacancies(Event event) {
        User user = event.getUser();
        String vacancyName = "Java";
        String numberOfVacancies = String.valueOf(5);
        String keywords = "sdfsd";
        if (!user.getSearchHistories().isEmpty()) {
            SearchHistory searchHistory = user.getSearchHistories().get(user.getSearchHistories().size() - 1);
            vacancyName = searchHistory.getVacancyName();
            numberOfVacancies = String.valueOf(searchHistory.getNumberOfVacancies());
            keywords = searchHistory.getKeywords();
        }
        Map<String, String> parsingParameters = Map.of(
                VACANCY_NAME_PARAMETER, vacancyName,
                NUMBER_OF_VACANCIES_PARAMETER, numberOfVacancies,
                KEYWORDS_PARAMETER, keywords
        );
        return getVacancies(user, parsingParameters);
    }
}
