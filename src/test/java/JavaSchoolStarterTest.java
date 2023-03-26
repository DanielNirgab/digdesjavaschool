import com.digdes.school.JavaSchoolStarter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarterTest {
    JavaSchoolStarter starter = new JavaSchoolStarter();
    List<Map<String, Object>> actualList = new ArrayList<>();
    List<Map<String, Object>> expected = new ArrayList<>();


    @BeforeEach
    public void input() {
        actualList = starter.execute("INSERT " +
                "VALUES 'lastName' = 'Федотов' , 'id'= 3, 'age'=40, 'active'= true");
        actualList = starter.execute("INSERT " +
                "VALUES 'lastName' = 'Сидоров' , 'id'= 4, 'age'=35, 'active'= true");

    }

    @Test
    public void insertTest() {
        expected.add(valueInsertTests());
        expected.add(value2InsertTests());

        assertThat(actualList).isEqualTo(expected);
    }

    @Test
    public void update_With_Where_Test() {
        actualList = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3");

        expected.add(valueUpdateTests());
        expected.add(value2InsertTests());

        assertThat(actualList).isEqualTo(expected);
    }
    @Test
    public void update_All_Test() {
        actualList = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1");

        expected.add(valueUpdateTests());
        expected.add(value2UpdateTests());

        assertThat(actualList).isEqualTo(expected);
    }
    @Test
    public void select_With_Where_Test () {

        actualList = starter.execute("SELECT where 'id'=3");

        expected.add(valueInsertTests());
        expected.add(value2InsertTests());

        assertThat(actualList).isEqualTo(expected);
    }
    @Test
    public void select_All_Test () {

        actualList = starter.execute("SELECT");

        expected.add(valueInsertTests());
        expected.add(value2InsertTests());

        assertThat(actualList).isEqualTo(expected);
    }

    @Test
    public void delete_All_Test () {
        actualList = starter.execute("DELETE");
        assertThat(actualList).isEqualTo(expected);
    }

    @Test
    public void delete_With_Where_Test () {
        actualList = starter.execute("DELETE WHERE 'id'=3");
        expected.add(value2InsertTests());
        assertThat(actualList).isEqualTo(expected);
    }



    public Map<String, Object> valueInsertTests() {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("'id'", 3L);
        test1.put("'lastName'", "'Федотов'");
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
        test1.put("'lastName'", "'Федотов'");
        test1.put("'age'", 40L);
        test1.put("'active'", false);
        test1.put("'cost'", 10.1d);
        return test1;
    }
    public Map<String, Object> value2UpdateTests() {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("'id'", 4L);
        test1.put("'lastName'", "'Сидоров'");
        test1.put("'age'", 35L);
        test1.put("'active'", false);
        test1.put("'cost'", 10.1d);
        return test1;
    }
}
