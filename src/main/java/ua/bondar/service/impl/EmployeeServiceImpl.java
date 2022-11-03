package ua.bondar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.dao.EmployeeDAO;
import ua.bondar.entity.Employee;
import ua.bondar.service.EmployeeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Optional<Employee> findById(int id) {
        return employeeDAO.findById(id);
    }

    @Override
    public Optional<Employee> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        return employeeDAO.findByName(name);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public List<Employee> findFromTo(int from, int to) {
        return employeeDAO.findFromTo(from, to);
    }

    @Override
    public void save(Employee saveObject) {
        if (saveObject == null) {
            return;
        }

        employeeDAO.save(saveObject);
    }

    @Override
    public void update(Employee updateObj) {
        if (updateObj == null) {
            return;
        }

        employeeDAO.update(updateObj);
    }

    @Override
    public void delete(Employee deleteObj) {
        if (deleteObj == null) {
            return;
        }

        employeeDAO.delete(deleteObj);
    }

    @Override
    public List<Employee> findByActive(boolean isActive) {
        return employeeDAO.findByActive(isActive);
    }

    @Override
    public List<Employee> findAllFromDepartment(String deptName) {
        if (deptName == null) {
            return Collections.emptyList();
        }

        return employeeDAO.findAllFromDepartment(deptName);
    }

    @Override
    public List<Employee> findAllFromDepartment(int idDept) {
        return employeeDAO.findAllFromDepartment(idDept);
    }

    @Override
    public int quantityEmployee() {
        return employeeDAO.quantityEmployee();
    }

    @Override
    public int quantityEmployee(String startWith) {
        if (startWith == null) {
            return 0;
        }
        return employeeDAO.quantityEmployee(startWith);
    }

    @Override
    public List<Employee> findEmployeesNameStartWith(String startWith) {
        if (startWith == null) {
            return Collections.emptyList();
        }

        return employeeDAO.findEmployeesNameStartWith(startWith);
    }

    @Override
    public List<Employee> findEmployeesNameStartWith(String startWith, int from, int to) {
        if (startWith == null) {
            return Collections.emptyList();
        }

        return employeeDAO.findEmployeesNameStartWith(startWith, from, to);
    }
}
