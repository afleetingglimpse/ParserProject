package org.parsingbot.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.parsingbot.entity.Vacancy;
import org.parsingbot.service.VacancyBrowser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HhVacancyBrowser implements VacancyBrowser {
    private static final String LINK_KEY = "href";
    private static final String VACANCY_BODY_CLASS = "vacancy-serp-item-body";
    private static final String VACANCY_TITLE_CLASS = "serp-item__title";
    private static final String URL_TEMPLATE = "https://hh.ru/search/vacancy?text=%s&from=suggest_post&area=1&page=";

    @Override
    public List<Vacancy> browse(String vacancyToSearch, int numberOfVacancies, Predicate<Vacancy> filter)
            throws IOException {

        int page = 0;
        List<Vacancy> vacancies = new ArrayList<>();
        String url = String.format(URL_TEMPLATE, vacancyToSearch);

        while (vacancies.size() < numberOfVacancies) {
            Document doc = Jsoup.connect(url + page).get();
            Elements vacanciesElements = doc.getElementsByClass(VACANCY_BODY_CLASS);
            if (vacanciesElements.isEmpty()) {
                return vacancies;
            }

            for (Element vacancy : vacanciesElements) {
                if (vacancies.size() < numberOfVacancies) {
                    // получение основной информации о вакансии
                    Elements mainElements = vacancy.getElementsByClass(VACANCY_TITLE_CLASS);
                    String vacancyName = mainElements.text();
                    String link = mainElements.attr(LINK_KEY);

                    // создание объекта вакансии и заполнение основных полей
                    Vacancy temp = new Vacancy();
                    temp.setVacancyName(vacancyName);
                    temp.setVacancyLink(link);

                    if (filter != null) {
                        if (filter.test(temp)) {
                            vacancies.add(temp);
                        }
                    } else {
                        vacancies.add(temp);
                    }
                }
            }
            page++;
        }
        return vacancies;
    }
}
