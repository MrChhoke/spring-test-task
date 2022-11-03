package ua.bondar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.bondar.dto.UserDTO;
import ua.bondar.service.UserService;

import javax.validation.Valid;

@Controller
@Api(value = "SecurityController for authorization and registration",
        tags = {"Provides in-app authentication"})
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @ApiOperation(value = "Display the login page")
    public String login() {
        return "login";
    }

    @GetMapping("/reg")
    @ApiOperation(value = "Display the registration page")
    public String reg() {
        return "registration";
    }

    @PostMapping(value = "/reg", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert a new user into the database")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "userDTO",
                    value = "The user which you want to insert",
                    dataType = "UserDTO",
                    dataTypeClass = UserDTO.class,
                    paramType = "body"
            )
    })
    public String reg(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(UserDTO.fromDTO(userDTO));

        return "redirect:/reg";
    }
}
