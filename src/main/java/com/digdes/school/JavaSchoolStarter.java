package com.digdes.school;

import java.util.*;

import static com.digdes.school.Constants.*;

public class JavaSchoolStarter extends Converter implements IJavaSchoolStarter {

    private List<Map<String, Object>> result = new ArrayList<>();
    private Map<String, Object> row = new HashMap<>();

    public JavaSchoolStarter() {

    }

    @Override
    public List<Map<String, Object>> execute(String request) throws Exception {
        checkCommand(validation(request));
        print();
        return result;
    }

    private String[] validation(String input) {
        String command;
        if (input.matches(COMMAND_DELETE) || input.matches(COMMAND_SELECT)){
            System.out.println("VALIDATION: IF DELETE/SELECT");
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

    private Map<String, Object> checkCommand(String[] values) {
        if (values[0].equalsIgnoreCase(COMMAND_INSERT)) {
            row.put("'id'", null);
            row.put("'lastName'", null);
            row.put("'age'", null);
            row.put("'cost'", null);
            row.put("'active'", null);
            row.putAll(convertInsert(values));
            result.add(convertInsert(values));
            return row;
        } else if (values[0].equalsIgnoreCase(COMMAND_UPDATE) && !result.isEmpty()) {
            Map<String, Object> updatedRow = new HashMap<>(convertUpdate(values, result));
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).containsValue(updatedRow.get("'id'"))) {
                    result.set(i, updatedRow);
                }
            }
        } else if (values[0].equalsIgnoreCase(COMMAND_DELETE) && !result.isEmpty()) {
            Map<String, Object> updatedRow = new HashMap<>();
            if (values.length == 1) {
                result.clear();
            } else {
                updatedRow.putAll(convertUpdate(values, result));
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).containsValue(updatedRow.get("'id'"))) {
                        result.remove(i);
                    }
                }
            }

        } else if (values[0].equalsIgnoreCase(COMMAND_SELECT)) {
            if (values.length == 1) {
            } else {
                Map<String, Object> updatedRow = new HashMap<>(convertUpdate(values, result));
                row.putAll(updatedRow);
                System.out.println(row);
            }
        }
        return row;
    }

    private void print () {

    }
}

