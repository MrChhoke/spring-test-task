package ua.bondar.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This department has already exist")
public class DuplicateDepartmentException extends DuplicateKeyException {
    public DuplicateDepartmentException(String msg) {
        super(msg);
    }
}
