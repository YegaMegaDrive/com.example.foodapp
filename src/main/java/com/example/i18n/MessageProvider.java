package com.example.i18n;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageProvider implements IMsgProvider{

    @Autowired
    @Qualifier("messageSource")
    private MessageSource iMsgSource;

    public String getText(String key){
        return StringEscapeUtils.unescapeJava(iMsgSource.getMessage(key,null, LocaleContextHolder.getLocale()));
    }


    public String getText(String key, Object... params) {
        return StringEscapeUtils.unescapeJava(iMsgSource.getMessage(key,params,LocaleContextHolder.getLocale()));
    }

    @Override
    public String getMessage(String msg) {
        return getText(msg);
    }

    @Override
    public String getMessage(String msg, Object... params) {
        return getText(msg,params);
    }
}
