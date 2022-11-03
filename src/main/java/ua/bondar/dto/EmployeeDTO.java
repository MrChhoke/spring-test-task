package ua.bondar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import ua.bondar.entity.Employee;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "EmployeeDTO",
        description = "DTO class for employee")
@Getter
@Setter
public class EmployeeDTO {

    @JsonProperty("empID")
    @NotNull(message = "id employee cannot be null")
    @ApiModelProperty(value = "Unique identifier of employee",
            example = "1",
            dataType = "Integer",
            name = "id")
    private int id;

    @JsonProperty("empName")
    @ApiModelProperty(value = "The name of employee",
            example = "Vlad",
            dataType = "String",
            name = "name")
    @NotEmpty(message = "The name of employee may not be empty")
    @Size(min = 2, max = 32, message = "The name of employee must be between 2 and 32 characters long")
    private String name;

    @JsonProperty("empActive")
    @ApiModelProperty(value = "This variable shows that this employee is active or not",
            example = "True", name = "isActive")
    @NotNull(message = "isActive may not be null")
    private boolean isActive;

    @JsonProperty("department")
    @ApiModelProperty(value = "Department of this employee", name = "department")
    @Valid
    @NotNull(message = "the department may not be null")
    private DepartmentDTO department;

    public static EmployeeDTO fromEmployee(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.id = employee.getId();
        employeeDTO.name = employee.getName();
        employeeDTO.isActive = employee.isActive();
        employeeDTO.department = DepartmentDTO.fromDepartment(employee.getDepartment());

        return employeeDTO;
    }

    public String getName(){
        return name.trim();
    }

    public static Employee fromDTO(EmployeeDTO employeeDTO){
        return Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName().trim())
                .department(DepartmentDTO.fromDTO(employeeDTO.getDepartment()))
                .isActive(employeeDTO.isActive())
                .build();
    }

}
