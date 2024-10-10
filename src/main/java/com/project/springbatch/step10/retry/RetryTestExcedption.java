package com.project.springbatch.step10.retry;

public class RetryTestExcedption extends RuntimeException{
    public RetryTestExcedption() {
    }

    public RetryTestExcedption(String message) {
        super(message);
    }

    public RetryTestExcedption(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryTestExcedption(Throwable cause) {
        super(cause);
    }

    public RetryTestExcedption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
