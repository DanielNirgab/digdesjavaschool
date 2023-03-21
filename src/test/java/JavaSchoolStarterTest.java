import com.digdes.school.ex1.JavaSchoolStarter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarterTest {
    JavaSchoolStarter starter = new JavaSchoolStarter();

    List<Map<String, Object>> actual;

    @BeforeEach
    public void startInsert() throws Exception {
        actual= starter.execute("insert " +
                "VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
    }


    public Map<String, Object> valueInsertTests() {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("'id'", 3L);
        test1.put("'lastName'", "'Федоров'");
        test1.put("'age'", 40L);
        test1.put("'active'", true);
        return test1;
    }
    public Map<String, Object> value2InsertTests() {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("'id'", 4L);
        test1.put("'lastName'", "'Сидоров'");
        test1.put("'age'", 35L);
        test1.put("'active'", true);
        return test1;
    }
    public Map<String, Object> valueUpdateTests() {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("'id'", 3L);
        test1.put("'lastName'", "'Федоров'");
        test1.put("'age'", 40L);
        test1.put("'active'", false);
        test1.put("'cost'", 10.1d);
        return test1;
    }


    @Test
    public void insertCommandTest() throws Exception {
        List<Map<String, Object>> expected = new ArrayList<>();
        expected.add(valueInsertTests());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateCommandTest() throws Exception {
        actual = starter.execute("UPDATE " +
                "VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3");
        List<Map<String, Object>> expected = new ArrayList<>();
        expected.add(valueUpdateTests());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void selectCommandTest() throws Exception {
        actual = starter.execute("SELECT");
        List<Map<String,Object>> expected2 = new ArrayList<>();
        expected2.add(valueInsertTests());

        assertThat(actual).isEqualTo(expected2);
    }
    @Test
    public void selectWhereCommandTest() throws Exception {
        actual = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3");
        actual = starter.execute("SELECT WHERE 'cost'= 10.1");
        List<Map<String,Object>> expected2 = new ArrayList<>();
        expected2.add(valueUpdateTests());

        assertThat(actual).isEqualTo(expected2);
    }

    @Test
    public void deleteCommandTest() throws Exception {
        actual = starter.execute("DELETE");
        List<Map<String,Object>> expected2 = new ArrayList<>();

        assertThat(actual).isEqualTo(expected2);
    }


}
