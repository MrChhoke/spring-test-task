package ua.bondar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found this employee")
public class EmployeeNotFoundException extends NoSuchElementException {

    public EmployeeNotFoundException() {
        super("Not found this employee");
    }
}
