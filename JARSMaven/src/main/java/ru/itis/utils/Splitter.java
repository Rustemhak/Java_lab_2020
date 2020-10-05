package ru.itis.utils;

import com.beust.jcommander.converters.IParameterSplitter;

import java.util.*;

public class Splitter implements IParameterSplitter {
    public List<String> split(String value) {
        return Arrays.asList(value.split(","));
    }
}
