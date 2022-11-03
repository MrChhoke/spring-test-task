package ua.bondar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.bondar.dto.EmployeeDTO;
import ua.bondar.entity.Employee;
import ua.bondar.exception.EmployeeNotFoundException;
import ua.bondar.service.EmployeeService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/emp")
@Api(value = "EmployeeController for CRUD Operations with Employees",
        tags = {"Create, Read, Update, Delete Employees"})
public class RestCRUDEmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public RestCRUDEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{idEmp}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Display info about some employee",
            response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "idEmp",
                    value = "ID employee on which you want to see",
                    dataType = "Integer",
                    dataTypeClass = Integer.class,
                    paramType = "path",
                    defaultValue = "1",
                    example = "1"
            )
    })
    public ResponseEntity<EmployeeDTO> infoEmployee(@PathVariable(name = "idEmp") Integer idEmp) {
        Optional<Employee> optionalEmployee = employeeService.findById(idEmp);

        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(optionalEmployee.get());

        return new ResponseEntity<>(employeeDTO, HttpStatus.valueOf(200));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert the employee into the database")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "employee",
                    value = "The employee which you want to insert",
                    dataType = "Employee",
                    dataTypeClass = Employee.class,
                    paramType = "body"
            )
    })
    public ResponseEntity<?> createEmp(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(EmployeeDTO.fromDTO(employeeDTO));

        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update the employee into the database")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "employee",
                    value = "The employee which you want to update",
                    dataType = "Employee",
                    dataTypeClass = Employee.class,
                    paramType = "body"
            )
    })
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDTO employee) {
        employeeService.update(EmployeeDTO.fromDTO(employee));
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete the employee into the database")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "employee",
                    value = "The employee which you want to delete",
                    dataType = "Employee",
                    dataTypeClass = Employee.class,
                    paramType = "body"
            )
    })
    public ResponseEntity<?> deleteEmployee(@RequestBody EmployeeDTO deleteEmployee) {
        employeeService.delete(EmployeeDTO.fromDTO(deleteEmployee));
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

}
