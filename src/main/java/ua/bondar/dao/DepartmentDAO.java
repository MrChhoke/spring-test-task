package ua.bondar.dao;

import ua.bondar.entity.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentDAO extends DAO<Department> {

    List<Department> findDeptsWhereAllEmployeesActive();

    Optional<Department> findDeptWhereEmployeeWorks(int empID);

    int quantityDepartments();

    Map<String, Integer> mapDepartments();
}
