package ua.bondar.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.bondar.dao.EmployeeDAO;
import ua.bondar.entity.Employee;
import ua.bondar.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;

@Primary
@Component
public class MySQLEmployeeDAO implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String QUERY_FIND_BY_ID = "SELECT * FROM (SELECT * FROM employees e WHERE e.empID = ?) fe JOIN departments d on d.deptID = fe.emp_dpID";
    private final static String QUERY_FIND_BY_NAME = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE empName = ?";
    private final static String QUERY_FIND_ALL = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID ORDER BY empID";
    private final static String QUERY_FIND_ALL_WITH_LIMIT = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID ORDER BY empID LIMIT ? OFFSET ?";
    private final static String QUERY_FIND_ALL_FROM_DEPARTMENT_NAME = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE emp_dpID = (SELECT deptID FROM departments WHERE deptName = ?) ORDER BY empID";
    private final static String QUERY_FIND_ALL_FROM_DEPARTMENT_ID = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE e.emp_dpID = (SELECT deptID FROM departments d WHERE d.deptID = ?) ORDER BY empID";
    private final static String QUERY_INSERT_EMPLOYEE = "INSERT INTO employees (empName, empActive, emp_dpID) VALUES (?,?,?)";
    private final static String QUERY_UPDATE_EMPLOYEE = "UPDATE employees SET empName = ?, emp_dpID = ?, empActive = ? WHERE empID = ?";
    private final static String QUERY_DELETE_EMPLOYEE = "DELETE FROM employees WHERE empID = ?";
    private final static String QUERY_FIND_ALL_EMPLOYEE_BY_ACTIVE = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE empActive = ? ORDER BY empID";
    private final static String QUERY_FIND_SIZE_TABLE_EMPLOYEES = "SELECT COUNT(*) FROM employees";
    private final static String QUERY_FIND_SIZE_TABLE_EMPLOYEES_START_NAME_WITH = "SELECT COUNT(*) FROM employees e WHERE empName LIKE ?";
    private final static String QUERY_FIND_EMPLOYEE_BY_PATTERN_LIKE = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE empName LIKE ? ORDER BY empID";
    private final static String QUERY_FIND_EMPLOYEE_BY_PATTERN_LIKE_FROM_TO = "SELECT * FROM employees e JOIN departments d on d.deptID = e.emp_dpID WHERE empName LIKE ? ORDER BY empID LIMIT ? OFFSET ?";

    @Autowired
    public MySQLEmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Employee> findById(int id) {
        Optional<Employee> optionalEmployee = jdbcTemplate
                .query(QUERY_FIND_BY_ID, new Object[]{id}, new EmployeeMapper())
                .stream()
                .findFirst();

        setDepartment(optionalEmployee);

        return optionalEmployee;
    }


    @Override
    public Optional<Employee> findByName(String name) {
        Optional<Employee> optionalEmployee = jdbcTemplate
                .query(QUERY_FIND_BY_NAME, new Object[]{name}, new EmployeeMapper())
                .stream()
                .findFirst();

        setDepartment(optionalEmployee);

        return optionalEmployee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_ALL, new EmployeeMapper());

        employees.forEach(this::setDepartment);

        return employees;
    }

    @Override
    public List<Employee> findFromTo(int from, int to) {
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_ALL_WITH_LIMIT, new Object[]{to - from, from}, new EmployeeMapper());

        employees.forEach(this::setDepartment);

        return employees;
    }

    @Override
    public void save(Employee saveObject) {
        jdbcTemplate
                .update(QUERY_INSERT_EMPLOYEE,
                        saveObject.getName(),
                        (byte) (saveObject.isActive() ? 1 : 0),
                        saveObject.getDepartment().getId()
                );
    }

    @Override
    public void update(Employee updateObj) {
        jdbcTemplate
                .update(QUERY_UPDATE_EMPLOYEE,
                        updateObj.getName(),
                        updateObj.getDepartment().getId(),
                        (byte) (updateObj.isActive() ? 1 : 0),
                        updateObj.getId()
                );
    }

    @Override
    public void delete(Employee deleteObj) {
        jdbcTemplate
                .update(QUERY_DELETE_EMPLOYEE, deleteObj.getId());
    }

    @Override
    public List<Employee> findByActive(boolean isActive) {
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_ALL_EMPLOYEE_BY_ACTIVE,
                        new Object[]{(byte) (isActive ? 1 : 0)},
                        new EmployeeMapper()
                );

        employees.forEach(this::setDepartment);

        return employees;
    }

    @Override
    public List<Employee> findAllFromDepartment(String deptName) {
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_ALL_FROM_DEPARTMENT_NAME,
                        new Object[]{deptName},
                        new EmployeeMapper()
                );

        employees.forEach(this::setDepartment);

        return employees;
    }

    @Override
    public List<Employee> findAllFromDepartment(int idDept) {
        return jdbcTemplate
                .query(QUERY_FIND_ALL_FROM_DEPARTMENT_ID,
                        new Object[]{idDept},
                        new EmployeeMapper()
                );
    }

    @Override
    public int quantityEmployee() {
        Integer countRow = jdbcTemplate
                .queryForObject(QUERY_FIND_SIZE_TABLE_EMPLOYEES, Integer.class);

        if (countRow == null) {
            return 0;
        }

        return countRow;
    }

    @Override
    public int quantityEmployee(String startWith) {
        startWith = startWith + "%";
        Integer countRow = jdbcTemplate
                .queryForObject(
                        QUERY_FIND_SIZE_TABLE_EMPLOYEES_START_NAME_WITH,
                        Integer.class,
                        startWith);
        if (countRow == null) {
            return 0;
        }

        return countRow;
    }

    @Override
    public List<Employee> findEmployeesNameStartWith(String startWith) {
        startWith = startWith + "%";
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_EMPLOYEE_BY_PATTERN_LIKE,
                        new Object[]{startWith},
                        new EmployeeMapper()
                );

        employees.forEach(this::setDepartment);

        return employees;
    }

    @Override
    public List<Employee> findEmployeesNameStartWith(String startWith, int from, int to) {
        startWith = startWith + "%";
        List<Employee> employees = jdbcTemplate
                .query(QUERY_FIND_EMPLOYEE_BY_PATTERN_LIKE_FROM_TO,
                        new Object[]{startWith, to - from, from},
                        new EmployeeMapper()
                );

        employees.forEach(this::setDepartment);

        return employees;
    }

    private void setDepartment(Optional<Employee> optionalEmployee) {
        if (optionalEmployee.isEmpty()) {
            return;
        }

        Employee employee = optionalEmployee.get();

        setDepartment(employee);
    }

    private void setDepartment(Employee employee) {
        if (employee == null) {
            return;
        }

        List<Employee> departmentEmployees =
                findAllFromDepartment(employee.getDepartment().getId());

        employee.getDepartment().setEmployees(departmentEmployees);
    }
}
