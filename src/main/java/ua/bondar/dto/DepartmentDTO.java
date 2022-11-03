package ua.bondar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import ua.bondar.entity.Department;
import ua.bondar.entity.Employee;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "DepartmentDTO",
        description = "DTO class for department")
@Getter
@Setter
public class DepartmentDTO {

    @JsonProperty("deptID")
    @ApiModelProperty(value = "Unique identifier of department",
            example = "1",
            dataType = "Integer",
            name = "id")
    @NotNull(message = "id department cannot be null")
    private int id;

    @JsonProperty("deptName")
    @ApiModelProperty(value = "Unique name of department",
            example = "SALES",
            dataType = "String",
            name = "name")
    @NotNull(message = "The name of department may not be null")
    @Size(min = 2, max = 32, message = "The name of department must be between 2 and 32 characters long")
    private String name;

    @JsonProperty("employees")
    @ApiModelProperty(value = "The set of employees` names", name = "employees")
    private List<String> employees;

    public String getName(){
        return name.trim();
    }

    public static DepartmentDTO fromDepartment(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();

        departmentDTO.id = department.getId();
        departmentDTO.name = department.getName();
        departmentDTO.employees = department.getEmployees()
                .stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        return departmentDTO;
    }

    public static Department fromDTO(DepartmentDTO departmentDTO){
        return Department.builder()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName().trim())
                .build();
    }
}
