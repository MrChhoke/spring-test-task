package ua.bondar.service;

import ua.bondar.entity.Employee;

import java.util.List;

public interface PageService {
    List<Employee> findUsersWithStartNameOnPage(String startName, Integer page);

    List<Employee> findUsersOnPage(Integer page);

    long findAmountPages(String startName);
}
