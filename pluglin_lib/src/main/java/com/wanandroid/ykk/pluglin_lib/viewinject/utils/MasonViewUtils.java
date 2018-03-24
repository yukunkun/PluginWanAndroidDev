//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wanandroid.ykk.pluglin_lib.viewinject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.text.TextUtils;
import android.view.View;


import com.wanandroid.ykk.pluglin_lib.MyApp;
import com.wanandroid.ykk.pluglin_lib.viewinject.annotation.BindLayoutById;
import com.wanandroid.ykk.pluglin_lib.viewinject.annotation.BindViewById;
import com.wanandroid.ykk.pluglin_lib.viewinject.annotation.EventBase;
import com.wanandroid.ykk.pluglin_lib.viewinject.annotation.InjectObject;
import com.wanandroid.ykk.pluglin_lib.viewinject.view.EventListenerManager;
import com.wanandroid.ykk.pluglin_lib.viewinject.view.ViewFinder;
import com.wanandroid.ykk.pluglin_lib.viewinject.view.ViewInjectInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MasonViewUtils {

    private static MasonViewUtils mMasonViewUtils = null;

    private Context mContext = MyApp.mInstance;

    private MasonViewUtils(Context mContext) {
    }

    public static MasonViewUtils getInstance(Context mContext) {
        if (null == mMasonViewUtils) {
            mMasonViewUtils = new MasonViewUtils(mContext);
        }

        return mMasonViewUtils;
    }

    public void inject(View view) {
        injectObject(view, new ViewFinder(view));
    }

    public void inject(Activity activity) {
        injectObject(activity, new ViewFinder(activity));
    }

    public void inject(PreferenceActivity preferenceActivity) {
        injectObject(preferenceActivity, new ViewFinder(preferenceActivity));
    }

    public void inject(Object handler, View view) {
        injectObject(handler, new ViewFinder(view));
    }

    public void inject(Object handler, Activity activity) {
        injectObject(handler, new ViewFinder(activity));
    }

    public void inject(Object handler, PreferenceGroup preferenceGroup) {
        injectObject(handler, new ViewFinder(preferenceGroup));
    }

    public void inject(Object handler, PreferenceActivity preferenceActivity) {
        injectObject(handler, new ViewFinder(preferenceActivity));
    }

    private void injectObject(Object handler, ViewFinder finder) {
        Class handlerType = handler.getClass();
        BindLayoutById contentView = (BindLayoutById) handlerType.getAnnotation(BindLayoutById.class);
        if (contentView != null) {
            try {
                Method fields = handlerType.getMethod("setContentView", new Class[]{Integer.TYPE});
                Resources r = mContext.getResources();
                int id = r.getIdentifier(contentView.layoutId(), "layout", mContext.getPackageName());
                if (id > 0) {
                    fields.invoke(handler, new Object[]{Integer.valueOf(id)});
                } else {

                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                InvocationTargetException exception = (InvocationTargetException) throwable;
                Throwable e = exception.getTargetException();

                e.printStackTrace();
            }
        }

        Field[] declaredFields = handlerType.getDeclaredFields();
        int index;
        if (declaredFields != null && declaredFields.length > 0) {
            Field[] methods = declaredFields;
            int length = declaredFields.length;

            for (index = 0; index < length; ++index) {
                Field field = methods[index];
                BindViewById method = field.getAnnotation(BindViewById.class);
                if (method != null) {
                    try {
                        View annotations = finder.findViewById(method.id().equals("") ? field.getName() : method.id(), method.parentId());
                        if (annotations != null) {
                            field.setAccessible(true);
                            field.set(handler, annotations);
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                InjectObject injectObject = field.getAnnotation(InjectObject.class);
                if (injectObject != null) {
                    try {
                        String type = field.getType().toString();
                        if (TextUtils.isEmpty(type)) {
                            return;
                        }

                        String typeName = type.toString().split(" ")[0];
                        String clazzName = type.toString().split(" ")[1];

                        if (TextUtils.isEmpty(typeName) || TextUtils.isEmpty(clazzName) || !typeName.equals("class")) {
                            return;
                        }

                        Class<?> clazz = Class.forName(clazzName);
                        Object ob = clazz.newInstance();
                        field.setAccessible(true);
                        field.set(handler, ob);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Method[] declaredMethods = handlerType.getDeclaredMethods();
        if (declaredMethods != null && declaredMethods.length > 0) {
            Method[] methods = declaredMethods;
            index = declaredMethods.length;

            for (int dmIndex = 0; dmIndex < index; ++dmIndex) {
                Method method = methods[dmIndex];
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                if (declaredAnnotations != null && declaredAnnotations.length > 0) {
                    Annotation[] annotations = declaredAnnotations;
                    int length = declaredAnnotations.length;

                    for (int dasIndex = 0; dasIndex < length; ++dasIndex) {
                        Annotation annotation = annotations[dasIndex];
                        Class annType = annotation.annotationType();
                        if (annType.getAnnotation(EventBase.class) != null) {
                            method.setAccessible(true);

                            try {
                                Method e1 = annType.getDeclaredMethod("ids", new Class[0]);
                                Method parentIdMethod = null;

                                try {
                                    parentIdMethod = annType.getDeclaredMethod("parentIds", new Class[0]);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }

                                Object values = e1.invoke(annotation, new Object[0]);
                                Object parentIds = parentIdMethod == null ? null : parentIdMethod.invoke(annotation, new Object[0]);
                                int parentIdsLen = parentIds == null ? 0 : Array.getLength(parentIds);
                                int len = Array.getLength(values);

                                for (int i = 0; i < len; ++i) {
                                    ViewInjectInfo info = new ViewInjectInfo();
                                    info.value = Array.get(values, i);
                                    info.parentId = parentIdsLen > i ? (String) Array.get(parentIds, i) : "";
                                    EventListenerManager.addEventMethod(finder, info, annotation, handler, method);
                                }
                            } catch (Throwable throwable1) {
                                throwable1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }
}