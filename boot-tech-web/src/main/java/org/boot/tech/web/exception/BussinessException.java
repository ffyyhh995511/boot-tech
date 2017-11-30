package org.boot.tech.web.exception;

public class BussinessException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028792L;

    public BussinessException() {
        super();
    }

    public BussinessException(String s) {
        super(s);
    }

}
