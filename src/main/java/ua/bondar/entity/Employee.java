package ua.bondar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
@ApiModel(value = "Employee", description = "Entity for employee")
public class Employee {

    @EqualsAndHashCode.Include
    @JsonProperty("empID")
    @ApiModelProperty(value = "Unique identifier of employee",
            example = "1",
            dataType = "Integer",
            name = "id")
    private int id;

    @EqualsAndHashCode.Exclude
    @JsonProperty("empName")
    @ApiModelProperty(value = "The name of employee",
            example = "Vlad",
            dataType = "String",
            name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @JsonProperty("empActive")
    @ApiModelProperty(value = "This variable shows that this employee is active or not",
            example = "True", name = "isActive")
    private boolean isActive;

    @EqualsAndHashCode.Exclude
    @ApiModelProperty(value = "Department of this employee", name = "department")
    private Department department;

}
