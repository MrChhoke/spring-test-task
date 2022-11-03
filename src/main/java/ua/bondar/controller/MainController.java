package ua.bondar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bondar.entity.Employee;
import ua.bondar.service.PageService;

import java.util.List;

@Controller
@Api(value = "The main controller which displays menu for working with employees")
public class MainController {

    private final PageService pageService;

    @Autowired
    public MainController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    @ApiOperation(value = "The main page of this controller",
            response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "page",
                    value = "The page to view",
                    dataType = "Integer",
                    dataTypeClass = Integer.class,
                    paramType = "path",
                    defaultValue = "1",
                    example = "1"
            ),
            @ApiImplicitParam(
                    name = "search",
                    value = "The substring that begins with the employee's name",
                    dataType = "Integer",
                    dataTypeClass = String.class,
                    paramType = "path",
                    defaultValue = "",
                    example = "Petro"
            )
    })
    public String showAll(@RequestParam(name = "page", required = false) Integer page,
                          @RequestParam(name = "search", required = false) String startName,
                          Model model) {
        if (page == null || page == 0) {
            page = 1;
        }

        List<Employee> allEmployeesOnPage =
                pageService.findUsersWithStartNameOnPage(startName, page);
        long amountPages = pageService.findAmountPages(startName);

        model.addAttribute("currentNumberPage", page);
        model.addAttribute("searchedName", startName);
        model.addAttribute("amountPages", amountPages);
        model.addAttribute("employees", allEmployeesOnPage);
        return "index";
    }
}
