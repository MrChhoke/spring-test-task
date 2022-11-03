package ua.bondar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.bondar.dao.DepartmentDAO;
import ua.bondar.entity.Department;
import ua.bondar.service.DepartmentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public Optional<Department> findById(int id) {
        return departmentDAO.findById(id);
    }

    @Override
    public Optional<Department> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        return departmentDAO.findByName(name);
    }

    @Override
    public List<Department> findAll() {
        return departmentDAO.findAll();
    }

    @Override
    public List<Department> findFromTo(int from, int to) {
        return departmentDAO.findFromTo(from, to);
    }

    @Override
    public void save(Department saveObject) {
        if (saveObject == null) {
            return;
        }
        departmentDAO.save(saveObject);
    }

    @Override
    public void update(Department updateObj) {
        if (updateObj == null) {
            return;
        }
        departmentDAO.update(updateObj);

    }

    @Override
    public void delete(Department deleteObj) {
        if (deleteObj == null) {
            return;
        }
        departmentDAO.delete(deleteObj);
    }

    @Override
    public List<Department> findDeptsWhereAllEmployeesActive() {
        return departmentDAO.findDeptsWhereAllEmployeesActive();
    }

    @Override
    public Optional<Department> findDeptWhereEmployeeWorks(int empID) {
        return departmentDAO.findDeptWhereEmployeeWorks(empID);
    }

    @Override
    public int quantityDepartments() {
        return departmentDAO.quantityDepartments();
    }

    @Override
    public Map<String, Integer> mapDepartments() {
        return departmentDAO.mapDepartments();
    }

}
