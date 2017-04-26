package com.sunesoft.ancon.fr.exceptions;

/**
 * Created by zhouz on 2016/5/11.
 */
public interface ExceptionPolicy {
    Boolean HandleException(Object sender, Exception exception);
}
