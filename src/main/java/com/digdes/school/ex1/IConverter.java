package com.digdes.school.ex1;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

public interface IConverter {
    Map<String, Object> convertInsert(String[] values);

    Map<String, Object> convertUpdate(String[] values, List<Map<String, Object>> result) throws DataFormatException;

    Map<String, Object> subValues(String[] values);
}
