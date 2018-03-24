package com.wanandroid.ykk.pluglin_lib.viewinject.view;

import android.view.View;


import com.wanandroid.ykk.pluglin_lib.viewinject.annotation.EventBase;
import com.wanandroid.ykk.pluglin_lib.viewinject.utils.DoubleKeyValueMap;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/2/23.
 */
public class EventListenerManager {
    private static final DoubleKeyValueMap<ViewInjectInfo, Class<?>, Object> listenerCache = new DoubleKeyValueMap();

    private EventListenerManager() {
    }

    public static void addEventMethod(ViewFinder finder, ViewInjectInfo info, Annotation eventAnnotation, Object handler, Method method) {
        try {
            View view = finder.findViewByInfo(info);
            if (view != null) {
                EventBase eventBase = eventAnnotation.annotationType().getAnnotation(EventBase.class);
                Class listenerType = eventBase.listenerType();
                String listenerSetter = eventBase.listenerSetter();
                String methodName = eventBase.methodName();
                boolean addNewMethod = false;
                Object listener = listenerCache.get(info, listenerType);
                EventListenerManager.DynamicHandler dynamicHandler = null;
                if (listener != null) {
                    dynamicHandler = (EventListenerManager.DynamicHandler) Proxy.getInvocationHandler(listener);
                    addNewMethod = handler.equals(dynamicHandler.getHandler());
                    if (addNewMethod) {
                        dynamicHandler.addMethod(methodName, method);
                    }
                }

                if (!addNewMethod) {
                    dynamicHandler = new EventListenerManager.DynamicHandler(handler);
                    dynamicHandler.addMethod(methodName, method);
                    listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, dynamicHandler);
                    listenerCache.put(info, listenerType, listener);
                }

                Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, new Class[]{listenerType});
                setEventListenerMethod.invoke(view, new Object[]{listener});
            }
        } catch (Throwable var14) {
            var14.printStackTrace();
        }

    }

    public static class DynamicHandler implements InvocationHandler {
        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap = new HashMap(1);

        public DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference(handler);
        }

        public void addMethod(String name, Method method) {
            this.methodMap.put(name, method);
        }

        public Object getHandler() {
            return this.handlerRef.get();
        }

        public void setHandler(Object handler) {
            this.handlerRef = new WeakReference(handler);
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                Object handler = this.handlerRef.get();
                if (handler != null) {
                    String methodName = method.getName();
                    method = this.methodMap.get(methodName);
                    if (method != null) {
                        return method.invoke(handler, args);
                    }
                }

            } catch (Exception e) {
                return null;
            }
            return null;

        }
    }
}
