package com.divyanshgoenka.headyassessment.util;

import com.divyanshgoenka.headyassessment.pojo.Category;

import java.util.Collection;
import java.util.List;

/**
 * Created by divyanshgoenka on 05/02/18.
 */

public class Validations {
    public static boolean isEmptyOrNull(Collection collection) {
        return collection == null || collection.size()<1;
    }
}
