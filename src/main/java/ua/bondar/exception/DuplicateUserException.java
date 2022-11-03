package ua.bondar.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This user has already exist")
public class DuplicateUserException extends DuplicateKeyException {
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
