package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            List<Map<String, Object>> result1 = starter.execute("INSERT " +
                    "VALUES 'lastName' = 'Федотов' , 'id'= 3, 'age'=40, 'active'= true");
            List<Map<String, Object>> result20 = starter.execute("INSERT " +
                    "VALUES 'lastName' = 'Сидоров' , 'id'= 4, 'age'=35, 'active'= true");
            List<Map<String, Object>> result2 = starter.execute("UPDATE " +
                    "VALUES 'active'=false, 'cost'=10.1");
            List<Map<String, Object>> result3 = starter.execute("SELECT WHERE 'age' >= 30 AND 'lastName' like '%Ф%'");
            List<Map<String, Object>> result4 = starter.execute("DELETE where 'id'=3");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}