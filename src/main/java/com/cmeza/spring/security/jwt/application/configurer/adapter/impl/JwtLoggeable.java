package com.cmeza.spring.security.jwt.application.configurer.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

import java.util.Collections;
import java.util.Objects;

public abstract class JwtLoggeable {
    private static final int SPACER_LENGTH = 70;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtLoggeable.class);
    private static final String SPACER = String.join("", Collections.nCopies(SPACER_LENGTH, "-"));
    private static final String LINE_PATTERN = "| {} |";
    private static final String TITLE_PATTERN = "| {} {} {}{} |";

    public void printLog() {
        this.printLog(SPACER);
        String title = logTitle();
        if (Objects.nonNull(title)) {
            int pad = ((SPACER_LENGTH - title.length() - 2) / 2);
            int difference = SPACER_LENGTH - ((pad * 2) + title.length() + 2);
            String sp = String.join("", Collections.nCopies(pad, "-"));
            this.printLogGeneral(TITLE_PATTERN, sp, title, sp, String.join("", Collections.nCopies(difference, "-")));
            this.printLog(SPACER);
        }
        log(LOGGER, SPACER);
        this.printLogGeneral("|");
    }

    protected String logTitle() {
        return null;
    }

    protected abstract LogLevel logLevel();

    @SuppressWarnings("SameParameterValue")
    protected abstract void log(Logger log, String spacer);

    private void printLog(Object... param) {
        printLogGeneral(LINE_PATTERN, param);
    }

    private void printLogGeneral(String pattern, Object... param) {
        switch (logLevel()) {
            case INFO:
                LOGGER.info(pattern, param);
                break;
            case DEBUG:
                LOGGER.debug(pattern, param);
                break;
            case WARN:
                LOGGER.warn(pattern, param);
                break;
            case TRACE:
                LOGGER.trace(pattern, param);
                break;
            default:
                LOGGER.error(pattern, param);
        }
    }
}
