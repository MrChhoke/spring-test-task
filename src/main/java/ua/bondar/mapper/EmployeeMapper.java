package ua.bondar.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.bondar.entity.Department;
import ua.bondar.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    private final static String FIELD_NAME_EMPLOYEE_ID = "empID";
    private final static String FIELD_NAME_EMPLOYEE_NAME = "empName";
    private final static String FIELD_NAME_EMPLOYEE_IS_ACTIVE = "empActive";

    private final static String FIELD_NAME_DEPARTMENT_ID = "deptID";
    private final static String FIELD_NAME_DEPARTMENT_NAME = "deptName";

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Employee.builder()
                .id(rs.getInt(FIELD_NAME_EMPLOYEE_ID))
                .name(rs.getString(FIELD_NAME_EMPLOYEE_NAME))
                .isActive(rs.getByte(FIELD_NAME_EMPLOYEE_IS_ACTIVE) == 1)
                .department(Department.builder()
                            .id(rs.getInt(FIELD_NAME_DEPARTMENT_ID))
                            .name(rs.getString(FIELD_NAME_DEPARTMENT_NAME))
                            .build())
                .build();
    }
}
