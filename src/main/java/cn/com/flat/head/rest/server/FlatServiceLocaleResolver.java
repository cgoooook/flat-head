package cn.com.flat.head.rest.server;

import java.util.Locale;

public interface FlatServiceLocaleResolver {

    String getMessage(String code);

    String getMessage(String code, Locale locale);

    String getMessage(String code, Object[] args);

    String getMessage(String code, Object[] args, Locale locale);

    String getMessage(String code, String defaultMessage);

    String getMessage(String code, String defaultMessage, Locale locale);

    String getMessage(String code, Object[] args, String defaultMessage);

    String getMessage(String code, Object[] args, String defaultMessage, Locale locale);

}
