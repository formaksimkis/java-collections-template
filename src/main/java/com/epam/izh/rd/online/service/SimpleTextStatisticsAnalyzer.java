package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Совет:
 * Начните с реализации метода {@link SimpleTextStatisticsAnalyzer#getWords(String)}.
 * Затем переиспользуйте данный метод при реализации других.
 * <p>
 * При необходимости, можно создать внутри данного класса дополнительные вспомогательные приватные методы.
 */
public class SimpleTextStatisticsAnalyzer implements TextStatisticsAnalyzer {

    /**
     * Необходимо реализовать функционал подсчета суммарной длины всех слов (пробелы, знаким препинания итд не считаются).
     * Например для текста "One, I - tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countSumLengthOfWords(String text) {
        AtomicInteger counterLenghtAllWords = new AtomicInteger();
        List<String> allWordsList = getWords(text);
        allWordsList.iterator().forEachRemaining(o -> counterLenghtAllWords.addAndGet(o.length()));
        return counterLenghtAllWords.get();
    }

    /**
     * Необходимо реализовать функционал подсчета количества слов в тексте.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 7.
     *
     * @param text текст
     */
    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).size();
    }

    /**
     * Необходимо реализовать функционал подсчета количества уникальных слов в тексте (с учетом регистра).
     * Например для текста "One, two, three, three - one, tWo, tWo!!" - данный метод должен вернуть 5.
     * param text текст
     */
    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text).size();
    }

    /**
     * Необходимо реализовать функционал получения списка слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "three", "one", "tWo", "tWo"}
     *
     * @param text текст
     */
    @Override
    public List<String> getWords(String text) {
        text = (text == null) ? "" : text;
        List<String> allWordsList = new ArrayList<>();
        Matcher matcherOnlyWords = Pattern.compile("\\w+").matcher(text);
        while (matcherOnlyWords.find()) {
            allWordsList.add(matcherOnlyWords.group());
        }
        return allWordsList;
    }

    /**
     * Необходимо реализовать функционал получения списка уникальных слов из текста.
     * Пробелы, запятые, точки, кавычки и другие знаки препинания являются разделителями слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должен вернуться список :
     * {"One", "two", "three", "one", "tWo"}
     *
     * @param text текст
     */
    @Override
    public Set<String> getUniqueWords(String text) {
        List<String> allWordsList = getWords(text);
        Set<String> uniqueWordsSet = new HashSet<>();
        Iterator<String> iterAllWords = allWordsList.iterator();
        iterAllWords.forEachRemaining(o -> uniqueWordsSet.add(o));
        return uniqueWordsSet;
    }

    /**
     * Необходимо реализовать функционал подсчета количества повторений слов.
     * Например для текста "One, two, three, three - one, tWo, tWo!!" должны вернуться результаты :
     * {"One" : 1, "two" : 1, "three" : 2, "one" : 1, "tWo" : 2}
     *
     * @param text текст
     */
    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        List<String> allWordsList = getWords(text);
        Map<String, Integer> eachWordCountMap = new HashMap<>();
        Iterator<String> iterAllWords = allWordsList.iterator();
        iterAllWords.forEachRemaining(o ->
                eachWordCountMap.put(o, eachWordCountMap.get(o) == null ? 1 : eachWordCountMap.get(o) + 1));
        return eachWordCountMap;
    }

    /**
     * Необходимо реализовать функционал вывода слов из текста в отсортированном виде (по длине) в зависимости от параметра direction.
     * Например для текста "Hello, Hi, mother, father - good, cat, c!!" должны вернуться результаты :
     * ASC : {"c", "Hi", "cat", "good", "Hello", "father", "mother"}
     * DESC : {"mother", "father", "Hello", "good", "cat", "Hi", "c"}
     *
     * @param text текст
     */
    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        List<String> sortWordsByLength = getWords(text);
        if (direction.equals(Direction.ASC)) {
            sortWordsByLength.sort(Comparator.comparing(o -> o.length()));
        } else {
            sortWordsByLength.sort(Comparator.comparing(o -> -o.length()));
        }
        return sortWordsByLength;
    }
}
