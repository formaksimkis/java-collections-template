package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text).stream().flatMapToInt(o -> IntStream.of(o.length())).sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return getWords(text).size();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return getUniqueWords(text).size();
    }

    @Override
    public List<String> getWords(String text) {
        text = (text == null) ? "" : text;
        return Pattern.compile("\\W+")
                .splitAsStream(text)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return getWords(text).stream()
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> eachWordCountMap = new HashMap<>();
        getWords(text).stream().forEach(o -> eachWordCountMap.put(o, eachWordCountMap.getOrDefault(o, 0) + 1));
        return eachWordCountMap;
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return direction.equals(Direction.ASC) ?
                getWords(text).stream().sorted(Comparator.comparing(o -> o.length())).collect(Collectors.toList())
                : getWords(text).stream().sorted(Comparator.comparing(o -> -o.length())).collect(Collectors.toList());
    }
}
