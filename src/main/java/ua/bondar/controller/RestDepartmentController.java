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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.bondar.dto.DepartmentDTO;
import ua.bondar.entity.Department;
import ua.bondar.service.DepartmentService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/dept")
@Api(value = "REST Controller for departments",
        tags = {"Display information about departments"})
public class RestDepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public RestDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show ids of all departments")
    public ResponseEntity<Map<String, Integer>> showAllIDsDepartments() {
        Map<String, Integer> jsonMap = departmentService.mapDepartments();

        return new ResponseEntity<>(jsonMap, HttpStatus.valueOf(200));
    }


    @PostMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert the department into the database")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "department",
                    value = "The department which you want to insert",
                    dataType = "Department",
                    dataTypeClass = Department.class,
                    paramType = "body"
            )
    })
    public ResponseEntity<?> insertDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        departmentService.save(DepartmentDTO.fromDTO(departmentDTO));

        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
