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
        listenerType = View.OnLongClickListener.class,
        listenerSetter = "setOnLongClickListener",
        methodName = "onLongClick"
)
public @interface OnLongClickEvent {
    String[] ids();

    String[] parentIds() default {""};
}