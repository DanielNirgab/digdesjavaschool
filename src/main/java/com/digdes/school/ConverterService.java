package com.digdes.school;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.digdes.school.Constants.*;

/**
 *
 * This class convert Values
 *
 */
public class ConverterService {

    ParserService parseService;

    public ConverterService(ParserService parseService) {
        this.parseService = parseService;
    }

    /**
     * Point fo enter
     @param request String from main input
     */
    public List<List<Object>> getListOfObjRequest (String request) {
        return new ArrayList<>(divideByValue(parseService.getParseList(request)));
    }

    /**
     * Divide values to new List [COMMANDS] [VALUE] [CONDITION]
    @param objectList {@link ParserService} List of splited value by word
     */
    private List<List<Object>> divideByValue(List<Object> objectList) {

        List<List<Object>> commonList = new ArrayList<>();
        List<Object> commandList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        List<Object> conditionList = new ArrayList<>();
        int indexWhere = -1;

        for (int i = 0; i < objectList.size(); i++) {
            Object indexValue = objectList.get(i);
            Matcher commandMatcher = patternCommand.matcher(indexValue.toString());
            int index = i;

            boolean isWhereCommand = indexValue instanceof String && indexValue.toString().equalsIgnoreCase(COMMAND_WHERE);
            if (isWhereCommand) {
                indexWhere += i;
                index += 1;
                while (index < objectList.size()) {
                    conditionList.add(objectList.get(index));
                    index++;
                }
            }
            if (indexValue instanceof String && commandMatcher.matches()) {


                commandList.add(indexValue);
            } else if (indexWhere < 0) {
                valueList.add(indexValue);
            }
        }

        if (commandList.isEmpty() && valueList.isEmpty() && conditionList.isEmpty()) {
            throw new RuntimeException("BAD REQUEST");
        } else {
            commonList.add(commandList);
            commonList.add(valueList);
            commonList.add(conditionList);
        }

        return commonList;
    }


}
