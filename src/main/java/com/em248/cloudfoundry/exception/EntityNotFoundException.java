package com.em248.cloudfoundry.exception;

/**
 * 自定义异常
 *
 * @author tian
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
