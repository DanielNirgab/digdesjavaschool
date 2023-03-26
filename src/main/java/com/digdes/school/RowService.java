package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static com.digdes.school.Constants.*;

public class RowService {

    ConverterService converterService;
    private final static List<Map<String, Object>> result = new ArrayList<>();
    private final static Map<String, Object> row = new HashMap<>();

    public RowService(ConverterService converterService) {
        this.converterService = converterService;
    }

    public Map<String, Object> getRowOfObjRequest(String request) {
        insertToMap(request);
        updateRow(request);
        selectRow(request);
        System.out.println(selectRow(request));
        return row;
    }

    private Map<String, Object> insertToMap(String request) {
        Map<String, Object> newRow = new HashMap<>();
        List<List<Object>> listOfValue = converterService.getListOfObjRequest(request);

        if (listOfValue.get(0).get(0).toString().equalsIgnoreCase(COMMAND_INSERT)) {
            for (int i = 0; i < listOfValue.get(1).size(); i++) {
                String indexValue = listOfValue.get(1).get(i).toString();
                Matcher matcher = patternCommonRow.matcher(indexValue);
                if (matcher.matches()) {
                    newRow.put(indexValue, listOfValue.get(1).get(i + 2));
                }
            }
            row.putAll(newRow);
            result.add(newRow);
        }
        return newRow;
    }

    private Map<String, Object> updateRow(String request) {
        Map<String, Object> updateRow = new HashMap<>();
        List<List<Object>> listOfValue = converterService.getListOfObjRequest(request);

        String keyValue = null;
        Object mapValue = null;
        if (listOfValue.get(2).size() > 0) {

            for (int i = 0; i < listOfValue.get(0).size(); i++) {
                if (listOfValue.get(0).get(i).toString().equalsIgnoreCase(COMMAND_WHERE)) {
                    // Если команда WHERE
                    // Выполнить метод с разбором по операторам сравнения - передать в метод listOfValue.get(2), где содержатся условия поиска
                    // Получаем индексы подходящих под условия строк, изменяем строку на новые значения
                    List<Integer> listOfSelectedIndex = doWhereCommand(listOfValue.get(2));
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
                            }
                        }
                    }
                }
            }
        } else {
            // Обновить все записи
            for (int j = 0; j < listOfValue.get(1).size(); j++) {
                Object valueObject = listOfValue.get(1).get(j);
                if (valueObject instanceof String) {
                    Matcher matcher = patternCommonRow.matcher(valueObject.toString());
                    if (matcher.matches()) {
                        keyValue = valueObject.toString();
                        mapValue = listOfValue.get(1).get(j + 2);
                    }
                }
            }
            if (keyValue != null) {
                for (Map<String, Object> stringObjectMap : result) {
                    stringObjectMap.put(keyValue, mapValue);
                }
            }
        }
        return updateRow;
    }

    private List<Map<String, Object>> selectRow(String request) {

        List<List<Object>> listOfValue = converterService.getListOfObjRequest(request);
        List<Map<String, Object>> selectedRow = new ArrayList<>();
        if (listOfValue.get(2).size() > 0) {

            List<Integer> listOfSelectedIndex = doWhereCommand(listOfValue.get(2));

            for (int i = 0; i < listOfSelectedIndex.size(); i++) {
                selectedRow.add(result.get(i));
            }
        } else {
            selectedRow.addAll(result);
        }
        return selectedRow;
    }

    private List<Integer> doWhereCommand(List<Object> condition) {
        // 1) Если содрежит операторы OR | AND -> выполнить метод по операторам сравнения между двумя значениями;
        // ИНАЧЕ 2) выполнить метод по операторам сравнения
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

    private List<Integer> doOperation(List<Map<String, Object>> mapList, List<String> operationList) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {

            for (int j = 0; j < mapList.size(); j++) {

                for (Map.Entry<String, Object> entry : mapList.get(j).entrySet()) {
                    Object objectValue = mapList.get(j).get(entry.getKey());

                    if (Operators.getByOperator(operationList.get(j)).operation(result.get(i).get(entry.getKey()), objectValue)) {
                        // Выводит строку, которую нашёл в записи, необходимо сохранить в новую строку и вернуть
                        indexList.add(i);
                    }
                }
            }
        }
        return indexList;
    }

}
