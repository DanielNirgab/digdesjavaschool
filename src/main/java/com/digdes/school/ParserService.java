package com.digdes.school;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.digdes.school.Constants.*;

/**
 *
 * Class parsing input String, divide and convert values to new Type
 *
 */

public class ParserService {

    public ParserService() {
    }

    public List<Object> getParseList (String request) {
        return parseValue(splitString(request));
    }

    /**
     * Split main String to parts by pattern {@link Constants}
     * @param request - String from main input
     */
    private List<String> splitString(String request) {
        Matcher matcher = patternAllObj.matcher(request);
        List<String> splitedRequest = new ArrayList<>();

        while (matcher.find()){
            splitedRequest.add(matcher.group());
        }
        return splitedRequest;
    }

    /**
     * Split values to List of value with LIFO method by patterns
     * @param value - Splited String by word
     */
    private List<Object> parseValue (List<String> value) {
        List<Object> parsedList = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            String nextValue = value.get(i);
            if (i+1 < value.size()) {
                nextValue = value.get(i+1);
            }

            Matcher matcherOperation = patternOperation.matcher(nextValue);
            Matcher matcherLong = patternLong.matcher(value.get(i));
            Matcher matcherDouble = patternDouble.matcher(value.get(i));
            Matcher matcherBoolean = patternBoolean.matcher(value.get(i));
            Matcher matcherString = patternString.matcher(value.get(i));
            Matcher matcherCommand = patternCommand.matcher(value.get(i));


            if (matcherLong.matches() && matcherOperation.matches()) {
                try {
                    parsedList.add(value.get(i));
                    parsedList.add(value.get(i+1));
                    parsedList.add(Long.parseLong(value.get(i+2)));
                    i += 2;
                } catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("Value empty for " + value.get(i));
                }
            } else if (matcherDouble.matches() && matcherOperation.matches()) {
                try {
                    parsedList.add(value.get(i));
                    parsedList.add(value.get(i+1));
                    parsedList.add(Double.parseDouble(value.get(i+2)));
                    i += 2;
                }  catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("Value empty for " + value.get(i));
                }
            } else if (matcherBoolean.matches() && matcherOperation.matches()){
                String falseString = "false";
                String trueString = "true";
                if (falseString.equalsIgnoreCase(value.get(i+2)) || trueString.equalsIgnoreCase(value.get(i+2))) {
                    parsedList.add(value.get(i));
                    parsedList.add(value.get(i+1));
                    parsedList.add(Boolean.parseBoolean(value.get(i + 2)));
                    i += 2;
                } else {
                    throw new RuntimeException("BAD REQUEST" + "['active' value should be Boolean]");
                }
            } else if (matcherString.matches() && matcherOperation.matches()) {
                parsedList.add(value.get(i));
            } else {
                parsedList.add(value.get(i));
            }
        }

        return parsedList;
    }

}
