package com.project.springbatch;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {
    private static final String[] FILTERED_STRINGS = {
            "SPRING_SESSION",
            "SELECT 1",
            "BATCH_JOB_EXECUTION",
            "BATCH_JOB_EXECUTION_CONTEXT",
            "BATCH_JOB_EXECUTION_PARAMS",
            "BATCH_JOB_EXECUTION_SEQ",
            "BATCH_JOB_INSTANCE",
            "BATCH_JOB_SEQ",
            "BATCH_STEP_EXECUTION",
            "BATCH_STEP_EXECUTION_CONTEXT",
            "BATCH_STEP_EXECUTION_SEQ"
    };

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String message = event.getMessage();
        for (String filter : FILTERED_STRINGS) {
            if (message.contains(filter)) {
                return FilterReply.DENY;
            }
        }
        return FilterReply.ACCEPT;
    }
}