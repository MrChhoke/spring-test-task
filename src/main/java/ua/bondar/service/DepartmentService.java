package ua.bondar.service;

import ua.bondar.entity.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentService {

    Optional<Department> findById(int id);

    Optional<Department> findByName(String name);

    List<Department> findAll();

    List<Department> findFromTo(int from, int to);

    void save(Department saveObject);

    void update(Department updateObj);

    void delete(Department deleteObj);

    List<Department> findDeptsWhereAllEmployeesActive();

    Optional<Department> findDeptWhereEmployeeWorks(int empID);

    int quantityDepartments();

    Map<String, Integer> mapDepartments();
}
