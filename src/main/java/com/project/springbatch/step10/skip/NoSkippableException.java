package com.project.springbatch.step10.skip;

public class NoSkippableException extends Exception{
    public NoSkippableException(String s) {
        super(s);
    }
}
