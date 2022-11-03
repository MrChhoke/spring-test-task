package ua.bondar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found this department")
public class DepartmentNotFoundException extends NoSuchElementException {

    public DepartmentNotFoundException() {
        super("Not found this department");
    }
}
