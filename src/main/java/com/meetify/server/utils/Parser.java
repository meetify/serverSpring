package com.meetify.server.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * com.meetify.server.utils
 * Created by kr3v on 08.10.2016.
 */
public class Parser {
    public static HashSet<Long> setFromString(String s) {
        HashSet<Long> answer = new HashSet<>();
        if (!s.equals(""))
            Arrays.asList(s.split(",")).forEach(s1 -> answer.add(Long.parseLong(s1)));
        return answer;
    }
}
