package com.digdes.school;

import java.util.*;

public class Converter implements IConverter {
    private long parseLong;
    private double parseDouble;
    private boolean parseBoolean;
    public Converter() {
    }

    @Override
    public Map<String, Object> convertInsert(String[] values) {

        return new HashMap<>(subValues(values));
    }

    @Override
    public Map<String, Object> convertUpdate(String[] values, List<Map<String, Object>> result) {
        String[] newValues = new String[values.length-1];
        Map<String, Object> row = new HashMap<>();
        System.arraycopy(values, 0, newValues, 0, newValues.length);
        if (values[values.length-1].contains("and") || values[values.length-1].contains("AND")) {
            String[] condition = values[values.length-1].split("and | AND");

        } else if (values[values.length-1].contains("or") || values[values.length-1].contains("OR")) {
            String[] condition = values[values.length-1].split("or | OR");

        } else {
            String condition = values[values.length-1].replaceAll("\\s", "");
            String valueSubstring = condition.substring(condition.indexOf("=") + 1);
            String keySubstring = condition.substring(0, condition.indexOf("="));
            //Получить индекс Map, в котором есть ключ равный keySubstring и заменить значения ключей в этой мапе на newValues
            for (Map<String, Object> map : result) {
                if (map.get(keySubstring) instanceof Long ){
                    parseLong = Long.parseLong(valueSubstring);
                    if (map.containsKey(keySubstring) && map.containsValue(parseLong) && (keySubstring.matches("'id'") || keySubstring.matches("'age'"))){
                        row.putAll(map);
                        row.putAll(subValues(newValues));
                    }
                } else if (map.get(keySubstring) instanceof Double && keySubstring.matches("'cost'")){
                    parseDouble = Double.parseDouble(valueSubstring);
                    if (map.containsKey(keySubstring) && map.containsValue(parseDouble)){

                        row.putAll(map);
                        row.putAll(subValues(newValues));
                    }
                } else if (map.get(keySubstring) instanceof Boolean){
                    if (valueSubstring.matches("true")|| valueSubstring.matches("false")) {
                        parseBoolean = Boolean.parseBoolean(valueSubstring);
                        if (map.containsKey(keySubstring) && map.containsValue(parseBoolean)) {
                            row.putAll(map);
                            row.putAll(subValues(newValues));
                        }
                    } else {
                        System.out.println("EXCEPTION");
                    }
                } else if (map.get(keySubstring) instanceof String && keySubstring.matches("'lastName'")) {
                    if (map.containsKey(keySubstring) && map.containsValue(valueSubstring)){
                        row.putAll(map);
                        row.putAll(subValues(newValues));
                    }
                } else {
                    System.out.println("EXCEPTION");
                }

            }
        }
        return row;
    }

    @Override
    public Map<String, Object> subValues (String[] values) {
        String[] converted = new String[values.length];
        Map<String, Object> convertedMap = new HashMap<>();
        for (int i = 1; i < values.length; i++) {
            converted[i] = values[i].replaceAll("\\s", "");
            String valueSubstring = converted[i].substring(converted[i].indexOf("=") + 1);
            String keySubstring = converted[i].substring(0, converted[i].indexOf("="));
            if (keySubstring.equalsIgnoreCase("'id'")) {
                parseLong = Long.parseLong(valueSubstring);
                convertedMap.put(keySubstring, parseLong);
            }
            if (keySubstring.equalsIgnoreCase("'lastName'")) {
                convertedMap.put(keySubstring, valueSubstring);
            }
            if (keySubstring.equalsIgnoreCase("'age'")) {
                parseLong = Long.parseLong(valueSubstring);
                convertedMap.put(keySubstring, parseLong);
            }
            if (keySubstring.equalsIgnoreCase("'cost'")) {
                parseDouble = Double.parseDouble(valueSubstring);
                convertedMap.put(keySubstring, parseDouble);
            }
            if (keySubstring.equalsIgnoreCase("'active'")) {
                parseBoolean = Boolean.parseBoolean(valueSubstring);
                convertedMap.put(keySubstring, parseBoolean);
            }
        }
        return convertedMap;
    }
}
