package ua.bondar.service;

import ua.bondar.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> findById(int id);

    Optional<Employee> findByName(String name);

    List<Employee> findAll();

    List<Employee> findFromTo(int from, int to);

    void save(Employee saveObject);

    void update(Employee updateObj);

    void delete(Employee deleteObj);

    List<Employee> findByActive(boolean isActive);

    List<Employee> findAllFromDepartment(String deptName);

    List<Employee> findAllFromDepartment(int idDept);

    int quantityEmployee();

    int quantityEmployee(String startWith);

    List<Employee> findEmployeesNameStartWith(String startWith);

    List<Employee> findEmployeesNameStartWith(String startWith, int from, int to);

}
