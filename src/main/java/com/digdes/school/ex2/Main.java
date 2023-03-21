package com.digdes.school.ex2;

import com.digdes.school.ex1.JavaSchoolStarter;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
//            List<Map<String,Object>> result1 = starter.execute("INSERT " +
//                    "VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
//            List<Map<String,Object>> result10 = starter.execute("insert " +
//                    "VALUES 'lastName' = 'Сидоров' , 'id'=4, 'age'=35, 'active'=true");
//            List<Map<String,Object>> result2 = starter.execute("UPDATE" +
//                    "VALUES 'active'=false, 'cost'=10.1 WHERE 'id'= 3");
//            List<Map<String,Object>> result3 = starter.execute("SELECT");
            ParserService parserService = new ParserService();
//            parserService.parseValue(parserService.slpitString("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true"));
//            parserService.parseValue(parserService.slpitString("UPDATE VALUES 'active' = false, 'cost'=10.1 WHERE 'id'=3"));
//            parserService.slpitString("DELETE WHERE 'id'=3");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}