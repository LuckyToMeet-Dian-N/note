package com.gentle.utils;

import com.gentle.exception.CheckException;
import org.springframework.util.StringUtils;

/**
 * @author Gentle
 * @date 2020/01/10 : 20:27
 */
public class ValidataUtils {

    public static void isNotNullByString(Object str,String message){
        if (StringUtils.isEmpty(str)){
            throw new CheckException(message);
        }
    }
    public static void isNotNull(Object str,String message){
        if (StringUtils.isEmpty(str)){
            throw new CheckException(message);
        }
    }

}
