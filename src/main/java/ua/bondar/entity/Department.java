package ua.bondar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Builder
@ApiModel(value = "Department", description = "Entity for department")
public class Department {

    @EqualsAndHashCode.Include
    @JsonProperty("deptID")
    @ApiModelProperty(value = "Unique identifier of department",
            example = "1",
            dataType = "Integer",
            name = "id")
    private int id;

    @EqualsAndHashCode.Exclude
    @JsonProperty("deptName")
    @ApiModelProperty(value = "Unique name of department",
            example = "SALES",
            dataType = "String",
            name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ApiModelProperty(value = "The set of employees", name = "employees")
    private List<Employee> employees;

}
