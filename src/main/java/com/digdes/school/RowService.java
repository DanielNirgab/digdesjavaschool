package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;

import static com.digdes.school.Constants.*;

/**
 *
 * Class working with rows from Table
 *
 */
public class RowService {

    ConverterService converterService;
    public final static List<Map<String, Object>> result = new ArrayList<>();
    public final static Map<String, Object> row = new HashMap<>();

    public RowService(ConverterService converterService) {
        this.converterService = converterService;
    }


    private List<List<Object>> getListOfValue(String request){
        if (converterService.getListOfObjRequest(request).get(0).isEmpty()){
            throw new RuntimeException("Your Command is not supported");
        } else {
            return converterService.getListOfObjRequest(request);
        }
    }
    /**
     * Point of enter
     * Check input command and start method of command
     * @param request - String from main input
     */
    public void doCommand (String request) {
        String command = getListOfValue(request).get(0).get(0).toString();
        switch (command) {
            case (COMMAND_INSERT):
                insertToMap(request);
                System.out.println(result);
                break;
            case (COMMAND_UPDATE):
                updateRow(request);
                System.out.println(result);
                break;
            case (COMMAND_SELECT):
                System.out.println(selectRow(request));
                break;
            case (COMMAND_DELETE):
                deleteValue(request);
                System.out.println(result);
                break;
        }
    }
    /**
     * Command DELETE
     * Should Delete row or all list
     * @param request - String from main input
     */
    private void deleteValue(String request) {
        List<List<Object>> listOfValue = getListOfValue(request);

        if (listOfValue.get(2).size() > 0) {

            Set<Integer> listOfSelectedIndex = doWhereCommand(listOfValue.get(2));

            for (int i = 0; i < listOfSelectedIndex.size(); i++) {
                result.remove(result.get(i));
             //   System.out.println("Was DELETE " + result.get(i));
            }
        } else {
            result.clear();
          //  System.out.println("Was DELETE " + "ALL");
        }
    }
    /**
     * Command INSERT
     * Should Insert new row to Table
     * @param request - String from main input
     */
    private Map<String, Object> insertToMap(String request) {
        Map<String, Object> newRow = new HashMap<>();
        List<List<Object>> listOfValue = getListOfValue(request);

            for (int i = 0; i < listOfValue.get(1).size(); i++) {
                String indexValue = listOfValue.get(1).get(i).toString();
                Matcher matcher = patternCommonRow.matcher(indexValue);
                if (matcher.matches()) {
                    newRow.put(indexValue, listOfValue.get(1).get(i + 2));
                }
            }
            row.putAll(newRow);
            result.add(newRow);
        printResult("INSERT", newRow);
        return newRow;
    }
    /**
     * Command UPDATE
     * Should Update rows to new Value
     * Could find row with specific condition by command WHERE and update this row
     * @param request - String from main input
     */
    private Map<String, Object> updateRow(String request) {
        Map<String, Object> updatedRow = new HashMap<>();
        List<List<Object>> listOfValue = getListOfValue(request);

        String keyValue = null;
        Object mapValue = null;
        if (listOfValue.get(2).size() > 0) {

            for (int i = 0; i < listOfValue.get(0).size(); i++) {
                Set<Integer> listOfSelectedIndex = new HashSet<>();
                if (listOfValue.get(0).get(i).toString().equalsIgnoreCase(COMMAND_WHERE)) {

                    try {
                        listOfSelectedIndex = doWhereCommand(listOfValue.get(2));
                    } catch (NullPointerException e) {
                        throw new NullPointerException("Row attribute is not present");
                    }
                    for (int j = 0; j < listOfValue.get(1).size(); j++) {
                        Object valueObject = listOfValue.get(1).get(j);
                        if (valueObject instanceof String) {
                            Matcher matcher = patternCommonRow.matcher(valueObject.toString());
                            if (matcher.matches()) {
                                keyValue = valueObject.toString();
                                mapValue = listOfValue.get(1).get(j + 2);
                            }
                        }
                        if (keyValue != null) {
                            for (Integer ofSelectedIndex : listOfSelectedIndex) {
                                result.get(ofSelectedIndex).put(keyValue, mapValue);
                                printResult("UPDATE", result.get(ofSelectedIndex));
                            }
                        }
                    }
                }
            }
        } else {

            for (int j = 0; j < listOfValue.get(1).size(); j++) {
                Object valueObject = listOfValue.get(1).get(j);
                if (valueObject instanceof String) {
                    Matcher matcher = patternCommonRow.matcher(valueObject.toString());
                    if (matcher.matches()) {
                        keyValue = valueObject.toString();
                        mapValue = listOfValue.get(1).get(j + 2);
                    }
                }
                if (keyValue != null) {
                    for (Map<String, Object> stringObjectMap : result) {
                        stringObjectMap.put(keyValue, mapValue);
                    }
                }
            }
        }

        return updatedRow;
    }

    /**
     * Command SELECT
     * Should Select row/rows from Table
     * Could find row with specific condition by command WHERE
     * @param request - String from main input
     */
    private List<Map<String, Object>> selectRow(String request) {

        List<List<Object>> listOfValue = getListOfValue(request);
        List<Map<String, Object>> selectedRow = new ArrayList<>();
        if (listOfValue.get(2).size() > 0) {

            Set<Integer> listOfSelectedIndex = doWhereCommand(listOfValue.get(2));
            for (int i = 0; i < listOfSelectedIndex.size(); i++) {
                selectedRow.add(result.get(i));
              //  System.out.println("Was SELECT " + result.get(i));
            }
        } else {
            selectedRow.addAll(result);
           // System.out.println("Was SELECT " + "ALL");
        }
        return selectedRow;
    }
    /**
     * Command WHERE
     * Should find row/rows from Table by condition
     * @param condition - Specific condition after WHERE command
     */
    private Set<Integer> doWhereCommand(List<Object> condition) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> operatorList = new ArrayList<>();
        if (condition.size() > 3) {
            for (int i = 0; i < condition.size(); i++) {
                if (condition.get(i).toString().equalsIgnoreCase("AND") || condition.get(i).toString().equalsIgnoreCase("OR")) {
                    Map<String, Object> condition1 = new HashMap<>();
                    String keyValue1 = condition.get(i - 3).toString();
                    Object mapValue1 = condition.get(i - 1);
                    String operator1 = condition.get(i - 2).toString();

                    Map<String, Object> condition2 = new HashMap<>();
                    String keyValue2 = condition.get(i + 1).toString();
                    Object mapValue2 = condition.get(i + 3);
                    String operator2 = condition.get(i + 2).toString();

                    condition1.put(keyValue1, mapValue1);
                    condition2.put(keyValue2, mapValue2);
                    mapList.add(condition1);
                    mapList.add(condition2);
                    operatorList.add(operator1);
                    operatorList.add(operator2);
                }
            }
        } else {
            Map<String, Object> condition1 = new HashMap<>();
            String keyValue1 = null;
            Object mapValue1 = null;
            String operator1 = null;
            for (Object o : condition) {
                if (o instanceof String) {
                    Matcher matcherKeyRow = patternCommonRow.matcher(o.toString());
                    Matcher matcherCommand = patternOperation.matcher(o.toString());
                    if (matcherKeyRow.matches()) {
                        keyValue1 = o.toString();
                    } else if (matcherCommand.matches()) {
                        operator1 = o.toString();
                    } else {
                        mapValue1 = o.toString();
                    }
                } else {
                    mapValue1 = o;
                }
            }

            condition1.put(keyValue1, mapValue1);
            mapList.add(condition1);
            operatorList.add(operator1);
        }
        return doOperation(mapList, operatorList);
    }
    /**
     * Operators
     * Compare 2 value
     * @param mapList - Condition for compare
     * @param operationList - List of operators in condition
     */
    private Set<Integer> doOperation(List<Map<String, Object>> mapList, List<String> operationList) {
        Set<Integer> indexList = new HashSet<>();
        for (int j = 0; j < mapList.size(); j++) {

            for (int i = 0; i < result.size(); i++) {

                for (Map.Entry<String, Object> entry : mapList.get(j).entrySet()) {
                    Object objectValue = mapList.get(j).get(entry.getKey());
                    if (Operators.getByOperator(operationList.get(j)).operation(result.get(i).get(entry.getKey()), objectValue)) {
                            indexList.add(i);
                    }
                }
            }
        }
        return indexList;
    }

    private void printResult (String command, Map<String, Object> row) {
       // System.out.println("Was " + command + " " + row);
    }

}
