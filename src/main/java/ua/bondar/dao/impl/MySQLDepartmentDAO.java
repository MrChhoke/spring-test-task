package ua.bondar.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.bondar.dao.DepartmentDAO;
import ua.bondar.entity.Department;
import ua.bondar.exception.DuplicateDepartmentException;
import ua.bondar.mapper.DepartmentMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Component
@Slf4j
public class MySQLDepartmentDAO implements DepartmentDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String QUERY_FIND_BY_ID = "SELECT * FROM departments WHERE deptID = ?";
    private final static String QUERY_FIND_BY_NAME = "SELECT * FROM departments WHERE deptName = ?";
    private final static String QUERY_FIND_ALL = "SELECT * FROM departments";
    private final static String QUERY_FIND_ALL_WITH_LIMIT = "SELECT * FROM departments LIMIT ? OFFSET ?";
    private final static String QUERY_INSERT_DEPARTMENT = "INSERT INTO departments(DEPTNAME) VALUES (?)";
    private final static String QUERY_UPDATE_DEPARTMENT = "UPDATE departments SET deptName = ? WHERE deptID = ?";
    private final static String QUERY_DELETE_DEPARTMENT = "DELETE FROM departments WHERE deptID = ? OR deptName = ?";
    private final static String QUERY_FIND_DEPARTMENTS_WHERE_ALL_EMPLOYEES_ACTIVE = "SELECT * FROM departments d WHERE 1 = ALL (SELECT DISTINCT empActive FROM employees e WHERE e.emp_dpID = d.deptID) AND EXISTS(SELECT 'X' FROM employees WHERE emp_dpID = d.deptID)";
    private final static String QUERY_FIND_DEPARTMENT_WHERE_EMPLOYEE_WORKS = "SELECT * FROM departments d WHERE d.deptID = (SELECT emp_dpID FROM employees e WHERE e.empID = ?)";
    private final static String QUERY_FIND_SIZE_TABLE = "SELECT COUNT(*) FROM departments";
    private final static String QUERY_FOR_MAP_DEPT_NAME_DEPT_ID = "SELECT deptID, deptName FROM departments";


    @Autowired
    public MySQLDepartmentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Department> findById(int id) {
        return jdbcTemplate
                .query(QUERY_FIND_BY_ID, new Object[]{id}, new DepartmentMapper())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Department> findByName(String name) {
        return jdbcTemplate
                .query(QUERY_FIND_BY_NAME, new Object[]{name}, new DepartmentMapper())
                .stream()
                .findFirst();
    }

    @Override
    public List<Department> findAll() {
        return jdbcTemplate
                .query(QUERY_FIND_ALL, new DepartmentMapper());
    }

    @Override
    public List<Department> findFromTo(int from, int to) {
        return jdbcTemplate
                .query(QUERY_FIND_ALL_WITH_LIMIT, new Object[]{to - from, from}, new DepartmentMapper());
    }

    @Override
    public void save(Department saveObject) {
        try {
            jdbcTemplate
                    .update(QUERY_INSERT_DEPARTMENT, saveObject.getName());
        } catch (DuplicateKeyException duplicateKeyException) {
            log.error("Cannot insert the department that has already exist");
            throw new DuplicateDepartmentException("This department has already exist");
        }
    }

    @Override
    public void update(Department updateObj) {
        jdbcTemplate
                .update(QUERY_UPDATE_DEPARTMENT, updateObj.getName(), updateObj.getId());
    }

    @Override
    public void delete(Department deleteObj) {
        try {
            jdbcTemplate
                    .update(QUERY_DELETE_DEPARTMENT, deleteObj.getId(), deleteObj.getName());
        } catch (DuplicateKeyException duplicateKeyException) {
            log.error("Two departments cannot have the same names");
            throw new DuplicateDepartmentException("The name of department is already reserved");
        }
    }

    @Override
    public List<Department> findDeptsWhereAllEmployeesActive() {
        return jdbcTemplate
                .query(QUERY_FIND_DEPARTMENTS_WHERE_ALL_EMPLOYEES_ACTIVE, new DepartmentMapper());
    }

    @Override
    public Optional<Department> findDeptWhereEmployeeWorks(int empId) {
        return jdbcTemplate
                .query(QUERY_FIND_DEPARTMENT_WHERE_EMPLOYEE_WORKS, new Object[]{empId}, new DepartmentMapper())
                .stream()
                .findFirst();
    }

    @Override
    public int quantityDepartments() {
        Integer countRow = jdbcTemplate
                .queryForObject(QUERY_FIND_SIZE_TABLE, Integer.class);

        if (countRow == null) {
            return 0;
        }

        return countRow;
    }

    @Override
    public Map<String, Integer> mapDepartments() {
        return jdbcTemplate.query(QUERY_FOR_MAP_DEPT_NAME_DEPT_ID, new DepartmentMapper())
                .stream()
                .collect(Collectors.toMap(Department::getName, Department::getId));
    }
}
