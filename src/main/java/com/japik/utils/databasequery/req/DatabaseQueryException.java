package com.japik.utils.databasequery.req;

public class DatabaseQueryException extends Exception {
    public DatabaseQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseQueryException(Throwable cause) {
        super(cause);
    }
}
