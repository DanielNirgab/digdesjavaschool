package com.digdes.school;

import com.digdes.school.JavaSchoolStarter;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            ParserService parserService = new ParserService();
            ConverterService converterService = new ConverterService(parserService);
            RowService rowService = new RowService(converterService);

//            converterService.getListOfObjRequest("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
//            converterService.getListOfObjRequest("update values 'active' = false, 'cost'=10.1 WHERE 'id'=3 OR 'age' = 40");
//            converterService.getListOfObjRequest("SELECT");

            rowService.getRowOfObjRequest("insert VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=35, 'active'=true");
            rowService.getRowOfObjRequest("insert VALUES 'lastName' = 'Сидоров' , 'id'=4, 'age'=40, 'active'=true");
            rowService.getRowOfObjRequest("update values 'age' = 99");
//            rowService.getRowOfObjRequest("update values 'age' = 99 where 'id'=3 and 'age'=40");


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}