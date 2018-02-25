package com.divyanshgoenka.headyassessment.log;

import com.divyanshgoenka.headyassessment.android.log.TimberLoggerImplementation;

/**
 * Created by divyanshgoenka on 15/02/18.
 */

public class Logger {
    private static LoggerImplementation loggerImplementation;

    public static void d(String s) {
        if (Logger.loggerImplementation != null) {
            Logger.loggerImplementation.d(s);
        }
    }

    public static void e(Throwable e) {
        if (Logger.loggerImplementation != null) {
            Logger.loggerImplementation.e(e);
        }
    }

    public static void setLoggerImplementation(TimberLoggerImplementation loggerImplementation) {
        Logger.loggerImplementation = loggerImplementation;
    }

    public interface LoggerImplementation {
        void d(String s);

        void e(Throwable e);
    }
}
