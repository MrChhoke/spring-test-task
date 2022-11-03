package ua.bondar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.bondar.dao.EmployeeDAO;
import ua.bondar.entity.Employee;
import ua.bondar.exception.NoSuchPageException;
import ua.bondar.service.PageService;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    private static Integer AMOUNT_EMPLOYEES_ON_PAGE = 5;

    private final EmployeeDAO employeeDAO;

    @Autowired
    public PageServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Value("${amount-employees-on-page}")
    public void setAmountEmployeesOnPage(Integer amountEmployeesOnPage) {
        if (amountEmployeesOnPage == null ||
                amountEmployeesOnPage <= 0) {
            throw new IllegalArgumentException("The amount employees on page cannot be negative or null");
        }

        AMOUNT_EMPLOYEES_ON_PAGE = amountEmployeesOnPage;
    }

    @Override
    public List<Employee> findUsersWithStartNameOnPage(String startName, Integer page) {
        List<Employee> allEmployeesOnPage;

        if (startName != null && !startName.isEmpty()) {
            startName = startName.trim();
            allEmployeesOnPage =
                    employeeDAO.findEmployeesNameStartWith(
                            startName,
                            (AMOUNT_EMPLOYEES_ON_PAGE * (page - 1)),
                            AMOUNT_EMPLOYEES_ON_PAGE * page
                    );
        } else {
            allEmployeesOnPage = findUsersOnPage(page);
        }

        if (allEmployeesOnPage.size() == 0 && page != 1) {
            throw new NoSuchPageException();
        }
        return allEmployeesOnPage;
    }

    @Override
    public List<Employee> findUsersOnPage(Integer page) {
        List<Employee> allEmployeesOnPage = employeeDAO.findFromTo(
                (AMOUNT_EMPLOYEES_ON_PAGE * (page - 1)),
                AMOUNT_EMPLOYEES_ON_PAGE * page
        );

        if (allEmployeesOnPage.size() == 0 && page != 1) {
            throw new NoSuchPageException();
        }

        return allEmployeesOnPage;
    }

    public long findAmountPages(String startName) {
        int amountEmployee;

        if (startName == null) {
            amountEmployee = employeeDAO.quantityEmployee();
        } else {
            amountEmployee = employeeDAO.quantityEmployee(startName);
        }

        return Math.round(Math.ceil((double) amountEmployee / AMOUNT_EMPLOYEES_ON_PAGE));
    }
}
