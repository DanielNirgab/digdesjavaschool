package com.digdes.school.ex1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static com.digdes.school.ex1.Constants.*;

public class JavaSchoolStarter extends Converter implements IJavaSchoolStarter {

    private List<Map<String, Object>> result = new ArrayList<>();
    private Map<String, Object> row = new HashMap<>();

    public JavaSchoolStarter() {

    }

    @Override
    public List<Map<String, Object>> execute(String request) throws Exception {
        checkCommand(validation(request));
        return result;
    }

    private String[] validation(String input) {
        String command;
        if (input.toUpperCase().matches(COMMAND_DELETE) || input.toUpperCase().matches(COMMAND_SELECT)) {
            return new String[]{input};
        } else {
            if (input.contains(COMMAND_DELETE) || input.contains(COMMAND_DELETE.toLowerCase()) ||
                    input.contains(COMMAND_SELECT) || input.contains(COMMAND_SELECT.toLowerCase())) {
                if (input.contains(" ")) {
                    command = input.substring(0, input.indexOf(" "));
                } else {
                    command = input;
                }
            } else {
                command = input.substring(0, input.indexOf("'") - 1);
            }
            if (input.contains(COMMAND_WHERE) || input.contains(COMMAND_WHERE.toLowerCase())) {
                String values = input.substring((input.indexOf("'") - 1));
                String[] splited = values.split(",| where | WHERE");
                String[] arrayCommand = new String[splited.length + 1];
                arrayCommand[0] = command;
                System.arraycopy(splited, 0, arrayCommand, 1, arrayCommand.length - 1);
                return arrayCommand;
            } else {
                String values = input.substring(input.indexOf("'") - 1);
                String[] splited = values.split(",");
                String[] arrayCommand = new String[splited.length + 1];
                arrayCommand[0] = command;
                System.arraycopy(splited, 0, arrayCommand, 1, arrayCommand.length - 1);

                return arrayCommand;
            }
        }
    }

    private Map<String, Object> checkCommand(String[] values) throws DataFormatException {
        if (values[0].equalsIgnoreCase(COMMAND_INSERT)) {
            row.put("'id'", null);
            row.put("'lastName'", null);
            row.put("'age'", null);
            row.put("'cost'", null);
            row.put("'active'", null);
            row.putAll(convertInsert(values));
            result.add(convertInsert(values));
            print();
            return row;
        } else if (values[0].equalsIgnoreCase(COMMAND_UPDATE) && !result.isEmpty()) {
            Map<String, Object> updatedRow = new HashMap<>();
                if (values[values.length-1].contains("OR") || values[values.length-1].contains("or") ||
                        values[values.length-1].contains("AND") || values[values.length-1].contains("and")) {
                        convertUpdate(values, result);
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        updatedRow = convertUpdate(values, result);
                        Map<String, Object> finalUpdatedRow = updatedRow;
                        Map<String, Object> EqualKeyValues = result.get(i).entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().equals(finalUpdatedRow.get(e.getKey()))));
                        if (EqualKeyValues.containsValue(true)) {
                            result.set(i, updatedRow);
                        }
                    }
                }
            print();
        } else if (values[0].equalsIgnoreCase(COMMAND_DELETE) && !result.isEmpty()) {
            Map<String, Object> updatedRow = new HashMap<>();

            if (values.length == 1) {
                result.clear();
                print();
            } else if (values[values.length-1].contains("OR") || values[values.length-1].contains("or") ||
                    values[values.length-1].contains("AND") || values[values.length-1].contains("and")) {
                convertUpdate(values, result);
            } else {
                updatedRow.putAll(convertUpdate(values, result));
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).containsValue(updatedRow.get("'id'"))) {
                        result.remove(i);
                    }
                }
                print();
            }

        } else if (values[0].equalsIgnoreCase(COMMAND_SELECT)) {
            if (values.length == 1) {
                print();
                return row;
            } if (values[values.length-1].contains("OR") || values[values.length-1].contains("or") ||
                    values[values.length-1].contains("AND") || values[values.length-1].contains("and")) {
                    convertUpdate(values, result);

            } else {
                Map<String, Object> updatedRow = new HashMap<>(convertUpdate(values, result));
                row.putAll(updatedRow);
                System.out.println(row);
            }
        }
        return row;
    }

    private Map<String, Object> condition(String[] values) {
        String[] condition = new String[values.length];
        Map<String, Object> conditionMap = new HashMap<>();
        if (values[values.length - 1].contains("and") || values[values.length - 1].contains("AND")) {
            condition = values[values.length - 1].split("and | AND");
        }
        for (String s : condition) {
            String conditionValue = s.replaceAll("\\s", "");
            String valueSubstring = conditionValue.substring(conditionValue.indexOf("=") + 1);
            String keySubstring = conditionValue.substring(0, conditionValue.indexOf("="));
            if (keySubstring.contains("'id'")) {
                conditionMap.put(keySubstring, Long.parseLong(valueSubstring));
            }
            if (keySubstring.contains("'age'")) {
                conditionMap.put(keySubstring, Long.parseLong(valueSubstring));
            }
            if (keySubstring.contains("'cost'")) {
                conditionMap.put(keySubstring, Double.parseDouble(valueSubstring));
            }
            if (keySubstring.contains("'active'")) {
                conditionMap.put(keySubstring, Boolean.parseBoolean(valueSubstring));
            }
            if (keySubstring.contains("'lastName'")) {
                conditionMap.put(keySubstring, valueSubstring);
            }
        }

        return conditionMap;
    }

    private void print() {
        System.out.println(result);
        System.out.println();
    }
}

