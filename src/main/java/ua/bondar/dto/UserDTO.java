package ua.bondar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ua.bondar.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "EmployeeDTO",
        description = "DTO class for user")
public class UserDTO {

    @JsonProperty("username")
    @ApiModelProperty(value = "The name of user. It is also unique",
            example = "Vlad",
            dataType = "String",
            name = "name")
    @NotEmpty(message = "Username's cannot be empty")
    @NotNull(message = "Username's cannot be null")
    @Size(min = 4, max = 80, message = "Username's size must be between 4 and 80")
    private String username;
    @JsonProperty("password")
    @ApiModelProperty(value = "The password of user",
            example = "my_password",
            dataType = "String",
            name = "password")
    @NotEmpty(message = "Password cannot empty")
    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 20, message = "Password's size must be between 4 and 80")
    private String password;

    private UserDTO() {
    }

    public static UserDTO fromUser(User employee) {
        UserDTO userDTO = new UserDTO();

        userDTO.username = employee.getUsername();
        userDTO.password = employee.getPassword();

        return userDTO;
    }

    public static User fromDTO(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.username.trim())
                .password(userDTO.password.trim())
                .build();
    }
}
