package com.wanandroid.ykk.pluglin_lib.viewinject.annotation;

import android.widget.AdapterView.OnItemClickListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/2/24.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = OnItemClickListener.class,
        listenerSetter = "setOnItemClickListener",
        methodName = "onItemClick"
)
public @interface OnItemClickEvent {
    String[] ids();

    String[] parentIds() default {""};
}