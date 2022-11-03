package ua.bondar.dao;

import ua.bondar.entity.Employee;

import java.util.List;

public interface EmployeeDAO extends DAO<Employee> {

    List<Employee> findByActive(boolean isActive);

    List<Employee> findAllFromDepartment(String deptName);

    List<Employee> findAllFromDepartment(int idDept);

    int quantityEmployee();

    int quantityEmployee(String startWith);

    List<Employee> findEmployeesNameStartWith(String startWith);

    List<Employee> findEmployeesNameStartWith(String startWith, int from, int to);
}
