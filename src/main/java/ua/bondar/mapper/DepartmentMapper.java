package ua.bondar.mapper;


import org.springframework.jdbc.core.RowMapper;
import ua.bondar.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    private final static String FIELD_NAME_DEPARTMENT_ID = "deptID";
    private final static String FIELD_NAME_DEPARTMENT_NAME = "deptName";

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Department.builder()
                .id(rs.getInt(FIELD_NAME_DEPARTMENT_ID))
                .name(rs.getString(FIELD_NAME_DEPARTMENT_NAME))
                .build();
    }
}
