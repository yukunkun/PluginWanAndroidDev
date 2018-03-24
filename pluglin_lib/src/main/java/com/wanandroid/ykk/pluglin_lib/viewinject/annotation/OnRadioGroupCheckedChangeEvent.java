package com.wanandroid.ykk.pluglin_lib.viewinject.annotation;

import android.widget.RadioGroup.OnCheckedChangeListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/2/26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
        listenerType = OnCheckedChangeListener.class,
        listenerSetter = "setOnCheckedChangeListener",
        methodName = "onCheckedChanged"
)
public @interface OnRadioGroupCheckedChangeEvent {
    String[] ids();

    String[] parentId() default {""};
}