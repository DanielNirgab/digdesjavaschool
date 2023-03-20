package com.digdes.school;

import java.util.List;
import java.util.Map;

public interface IConverter {
    Map<String, Object> convertInsert(String[] values);

    Map<String, Object> convertUpdate(String[] values, List<Map<String, Object>> result);

    Map<String, Object> subValues(String[] values);
}
