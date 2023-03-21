package com.digdes.school.ex1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

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
    public Map<String, Object> convertUpdate(String[] values, List<Map<String, Object>> result) throws DataFormatException {
        String[] newValues = new String[values.length-1];
        Map<String, Object> updatedRow = new HashMap<>();
        System.arraycopy(values, 0, newValues, 0, newValues.length);

        if (values[values.length-1].contains("and") || values[values.length-1].contains("AND")) {
            String[] condition = values[values.length-1].split("and | AND");
            checkCondition(result, newValues, updatedRow, condition);
        } else if (values[values.length-1].contains("or") || values[values.length-1].contains("OR")) {
            String[] condition = values[values.length-1].split("or | OR");
            checkCondition(result, newValues, updatedRow, condition);
        } else {
            String condition = values[values.length-1].replaceAll("\\s", "");
            updatedRow.putAll(mapConverter(result, newValues, condition));
        }
        return updatedRow;
    }

    private void checkCondition(List<Map<String, Object>> result, String[] newValues, Map<String, Object> updatedRow, String[] condition) throws DataFormatException {
        for (int i =0; i < condition.length; i++) {
            String conditionValue = condition[i].replaceAll("\\s", "");
            updatedRow.putAll(mapConverter(result, newValues, conditionValue));
            Map<String, Object> EqualKeyValues = result.get(i).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().equals(updatedRow.get(e.getKey()))));
            if (EqualKeyValues.containsValue(true)) {
                result.set(i, updatedRow);
                break;
            }
            System.out.println("UPDATED ROW" + updatedRow);
        }
    }

    private Map<String, Object> mapConverter(List<Map<String, Object>> result, String[] newValues, String conditionValue) throws DataFormatException {
        Map<String, Object> row = new HashMap<>();
        String valueSubstring = conditionValue.substring(conditionValue.indexOf("=") + 1);
        String keySubstring = conditionValue.substring(0, conditionValue.indexOf("="));
        for (Map<String, Object> map : result) {
            if (map.get(keySubstring) instanceof Long ){
                parseLong = Long.parseLong(valueSubstring);
                if (map.containsKey(keySubstring) && map.containsValue(parseLong) &&
                        ((keySubstring.matches("'id'") || keySubstring.matches("'age'")))){
                    row.putAll(map);
                    row.putAll(subValues(newValues));
                }
            } else if (map.get(keySubstring) instanceof Double){
                parseDouble = Double.parseDouble(valueSubstring);
                if (map.containsValue(parseDouble)){
                    row.putAll(map);
                    row.putAll(subValues(newValues));
                } else {
                    throw new DataFormatException();
                }
            } else if (map.get(keySubstring) instanceof Boolean){
                if (valueSubstring.matches("true")|| valueSubstring.matches("false")) {
                    parseBoolean = Boolean.parseBoolean(valueSubstring);
                    if (map.containsKey(keySubstring) && map.containsValue(parseBoolean)) {
                        row.putAll(map);
                        row.putAll(subValues(newValues));
                    }
                } else {
                    throw new DataFormatException();
                }
            } else if (map.get(keySubstring) instanceof String && keySubstring.matches("'lastName'")) {
                if (map.containsKey(keySubstring) && map.containsValue(valueSubstring)){
                    row.putAll(map);
                    row.putAll(subValues(newValues));
                }
            } else if (map.get(keySubstring)!=null){
                throw new DataFormatException();
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
