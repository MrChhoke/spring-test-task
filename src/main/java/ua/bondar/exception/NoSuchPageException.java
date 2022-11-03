package ua.bondar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Page not found")
public class NoSuchPageException extends NoSuchElementException {

    public NoSuchPageException() {
        super("Page not found");
    }
}
