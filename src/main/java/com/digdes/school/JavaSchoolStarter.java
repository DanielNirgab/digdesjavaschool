package com.digdes.school;


import java.util.List;
import java.util.Map;

import static com.digdes.school.Constants.COMMAND_INSERT;

public class JavaSchoolStarter extends RowService  {


    public JavaSchoolStarter(){
        super(new ConverterService(new ParserService()));
    }

    public List<Map<String,Object>> execute(String request) {

        try {
            doCommand(request);
            System.out.print("RESULT: ");
            System.out.println(result + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }












}