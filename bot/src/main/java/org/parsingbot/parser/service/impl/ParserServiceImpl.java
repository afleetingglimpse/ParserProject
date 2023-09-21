package org.parsingbot.parser.service.impl;

import lombok.RequiredArgsConstructor;
import org.parsingbot.entity.Event;
import org.parsingbot.entity.User;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.parser.service.ParserService;
import org.parsingbot.service.Parser;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.bot.utils.VacancyPredicates;
import org.parsingbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private final Parser parser;
    private final VacancyService vacancyService;

    @Override
    public List<SendMessage> getVacanciesMessageList(Event event) {
        return getVacancies(event).stream()
                .map(vacancy -> BotUtils.createMessage(event.getChatId(), vacancy.getVacancyLink()))
                .collect(Collectors.toList());
    }

    @Override
    public SendMessage getVacanciesSingleMessage(Event event) {
        List<String> vacanciesLinks = getVacancies(event).stream().map(Vacancy::getVacancyLink).toList();
        return BotUtils.createMessage(event.getChatId(), String.join("\n", vacanciesLinks));
    }

    private List<Vacancy> getVacancies(Event event) {
        User user = event.getUser();

        String vacancyName = user.getVacancyName();
        long numberOfVacancies = user.getNumberOfVacancies();
        String keywords = user.getKeywords();

        List<Vacancy> userVacancies = vacancyService.getVacanciesByUser(user);

        return parser.parse(
                vacancyName,
                (int) numberOfVacancies,
                VacancyPredicates.uniqueVacancy(userVacancies));
    }
}
