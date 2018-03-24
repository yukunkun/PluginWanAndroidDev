package com.wanandroid.ykk.pluglin_lib.viewinject.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/2/23.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = View.OnClickListener.class,
        listenerSetter = "setOnClickListener",
        methodName = "onClick"
)
public @interface OnClickEvent {
    String[] ids();

    String[] parentIds() default {""};
}