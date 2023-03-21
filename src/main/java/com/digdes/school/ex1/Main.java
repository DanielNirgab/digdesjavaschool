package com.digdes.school.ex1;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            List<Map<String,Object>> result1 = starter.execute("insert " +
                    "VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
            List<Map<String,Object>> result10 = starter.execute("insert " +
                    "VALUES 'lastName' = 'Сидоров' , 'id'=4, 'age'=35, 'active'=true");
            List<Map<String,Object>> result2 = starter.execute("UPDATE" +
                    " VALUES 'active'=false, 'cost'=10.1 WHERE 'id'= 3 OR 'age' = 35");
//            List<Map<String,Object>> result3 = starter.execute("SELECT");


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}