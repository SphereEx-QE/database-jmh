package com.sphereex.jmh.customizable.util;

import java.io.File;
import java.io.FileNotFoundException;

public class CustomizableUtil {
    
    private static File getFile(String filePath, String errorMessage) throws FileNotFoundException {
        filePath = filePath.startsWith(File.separator) ? filePath : System.getProperty("user.dir") + File.separator + filePath;
        File result = new File(filePath);
        if (!result.exists()) {
            throw new FileNotFoundException(errorMessage);
        }
        return result;
    }
    
    public static File getConfigFile(String filePath) throws FileNotFoundException {
        return getFile(filePath, "can not find the config file ");
    }
    
    public static File getSQLFile(String property) throws FileNotFoundException {
        return getFile(property, "can not find the sql file ");
    }
}
