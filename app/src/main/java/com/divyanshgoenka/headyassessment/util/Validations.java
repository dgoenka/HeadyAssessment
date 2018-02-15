package com.divyanshgoenka.headyassessment.util;

import java.util.Collection;

/**
 * Created by divyanshgoenka on 05/02/18.
 */

public class Validations {
    public static boolean isEmptyOrNull(Collection collection) {
        return collection == null || collection.size()<1;
    }

    public static boolean isEmptyOrNull(CharSequence str) {
        return str == null || str.length() < 1;
    }
}
