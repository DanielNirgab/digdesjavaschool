package com.digdes.school;

import java.util.List;
import java.util.Map;

public interface IJavaSchoolStarter {
    List<Map<String,Object>> execute(String request) throws Exception;
}
