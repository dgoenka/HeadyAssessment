package com.divyanshgoenka.headyassessment.di;

import java.io.File;

/**
 * Created by divyanshgoenka on 24/02/18.
 */

public class Implementor {
    private static Implementation currentImplementation;

    public static Implementation getCurrentImplementation() {
        return Implementor.currentImplementation;
    }

    public static void setCurrentImplementation(Implementation currentImplementation) {
        Implementor.currentImplementation = currentImplementation;
    }


    public interface Implementation {
        File getCacheDir();

        boolean isNetworkAvailable();
    }
}
