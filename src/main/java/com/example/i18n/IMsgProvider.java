package com.example.i18n;

public interface IMsgProvider {

    String getMessage(String msg);

    String getMessage(String msg,Object... params);
}
