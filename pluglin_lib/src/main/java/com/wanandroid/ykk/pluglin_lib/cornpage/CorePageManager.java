package com.wanandroid.ykk.pluglin_lib.cornpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanandroid.ykk.pluglin_lib.activity.BaseFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CorePageManager {
    private static final String TAG = CorePageManager.class.getSimpleName();

    private volatile static CorePageManager mInstance = null;

    private Context mContext;

    private Map<String, CorePage> mPageMap = new HashMap<String, CorePage>();

    private CorePageManager() {

    }

    public Map<String, CorePage> getPageMap() {
        return mPageMap;
    }


    public static CorePageManager getInstance() {
        if (mInstance == null) {
            synchronized (CorePageManager.class) {
                if (mInstance == null) {
                    mInstance = new CorePageManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context, String pageConfig) {
        try {
            mContext = context.getApplicationContext();

            String content = readFileFromAssets(mContext, pageConfig);
            readConfig(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readConfig(String content) {
        JSONArray jsonArray = JSON.parseArray(content);
        Iterator<Object> iterator = jsonArray.iterator();
        JSONObject jsonPage;
        String pageType;
        String pageName;
        String pageClazz;
        String pageParams;
        String pageTemplate;

        while (iterator.hasNext()) {
            jsonPage = (JSONObject) iterator.next();
            pageType = jsonPage.getString(CorePage.Tags.TYPE);
            pageName = jsonPage.getString(CorePage.Tags.NAME_ALIAS);
            pageClazz = jsonPage.getString(CorePage.Tags.CLAZZ);
            pageParams = jsonPage.getString(CorePage.Tags.PARAMS);
            pageTemplate = jsonPage.getString(CorePage.Tags.TEMPLATE);
            if (TextUtils.isEmpty(pageName) || TextUtils.isEmpty(pageClazz)) {
                return;
            }
            mPageMap.put(pageName, new CorePage(pageType, pageName, pageClazz, pageParams, pageTemplate));
        }
    }

    public void initTemplate(Context context, String pageConfig) {
        try {
            mContext = context.getApplicationContext();

            String content = readFileFromAssets(mContext, pageConfig);
            readConfig(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFileFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void openPage(Context context, String nameAlias, Bundle bundle) {
        openPage(context, nameAlias, bundle, -1);
    }

    private String tostring;
    private static long lastClickTime = 0;

    private boolean isfastopenpage(String nameAlias) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastClickTime < 800 && tostring.equals(nameAlias))
            return true;
        lastClickTime = curTime;
        tostring = nameAlias;
        return false;
    }

    public void openPage(Context context, String nameAlias, Bundle bundle, int flags) {
        if (isfastopenpage(nameAlias)) {
            return;
        }

        CorePage corePage = this.mPageMap.get(nameAlias);
        if (corePage == null) {
            Log.e(TAG, "Page:" + nameAlias + " is null");
            return;
        }

        Bundle pageBundle = buildBundle(corePage);
        if (null != pageBundle && bundle != null) {
            pageBundle.putAll(bundle);
        }

        if (corePage.type.equals(CorePage.PAGE_TYPE_ACTIVITY)) {
            Intent intent = new Intent();
            if (-1 != flags) {
                intent.setFlags(flags);
            }

            intent.setClassName(mContext, getClazzByTemplate(corePage.clazz, corePage.template));

            if (null != pageBundle) {
                intent.putExtras(pageBundle);
            }
            context.startActivity(intent);
        } else {
            Log.e(TAG, "This method only can access by activity--> Page:" + nameAlias);
        }
    }

    private String getClazzByTemplate(String clazz, String template) {
        if (!TextUtils.isEmpty(template)) {
            String packageName = clazz.substring(0, clazz.lastIndexOf("."));
            String clazzName = clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
            return packageName + "." + template.toLowerCase() + "." + clazzName + template;
        } else {

        }
        return clazz;
    }

    public Class<?> getPageByName(String nameAlias) {
        CorePage corePage = this.mPageMap.get(nameAlias);
        if (corePage == null) {
            Log.e(TAG, "Page:" + nameAlias + " is null");
            return null;
        }
        try {
            return Class.forName(getClazzByTemplate(corePage.clazz, corePage.template));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getClazzByName(String nameAlias) {
        return getPageByName(nameAlias);
    }

    public void openPageForResult(Activity mActivity, String nameAlias, Bundle bundle, int requestCode) {
        CorePage corePage = this.mPageMap.get(nameAlias);
        if (corePage == null) {
            Log.e(TAG, "Page:" + nameAlias + " is null");
            return;
        }

        Bundle pageBundle = buildBundle(corePage);
        if (null != pageBundle && bundle != null) {
            pageBundle.putAll(bundle);
        }

        if (corePage.type.equals(CorePage.PAGE_TYPE_ACTIVITY)) {
            Intent intent = new Intent();

            intent.setClassName(mContext, getClazzByTemplate(corePage.clazz, corePage.template));

            if (null != pageBundle) {
                intent.putExtras(pageBundle);
            }

            mActivity.startActivityForResult(intent, requestCode);
        } else {
            Log.e(TAG, "This method only can access by activity--> Page:" + nameAlias);
        }
    }

    public Fragment gotoPage(int fragmentContainerID, FragmentManager fragmentManager, String pageName, Bundle bundle, int[] animations) {
        Fragment fragment = null;
        if (fragmentManager != null) {
            fragment = fragmentManager.findFragmentByTag(pageName);
        }
        if (fragment != null) {
            fragmentManager.popBackStackImmediate(pageName, 0);
        } else {
            fragment = this.openPage(fragmentContainerID, fragmentManager, pageName, bundle, animations, true);
        }

        return fragment;
    }

    public BaseFragment gotoPage(int fragmentContainerID, FragmentManager fragmentManager, String pageName, String prePage, Bundle bundle, int[] animations) {
        BaseFragment fragment = null;
        BaseFragment fragmentPre = null;
        FragmentTransaction transaction = null;
        if (prePage != null && prePage.equals(pageName)) {
            fragment = (BaseFragment) fragmentManager.findFragmentByTag(pageName);
            if (bundle != null && fragment != null) { //try to fix RT 456228
                fragment.invalidate(bundle);
                return fragment;
            }
        }

        if (fragmentManager != null) {
            transaction = fragmentManager.beginTransaction();
            if (pageName != null) {
                fragment = (BaseFragment) fragmentManager.findFragmentByTag(pageName);
            }

            if (prePage != null) {
                fragmentPre = (BaseFragment) fragmentManager.findFragmentByTag(prePage);
            }
        }

        if (null != fragmentPre) {
            transaction.hide(fragmentPre);
        }

        if (fragment != null) {
            fragment.invalidate(bundle);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        } else {
            fragment = openPage(fragmentContainerID, transaction, pageName, bundle, animations, true);
        }

        return fragment;
    }

    public Fragment openPage(int fragmentContainerID, FragmentManager fragmentManager, String nameAlias, Bundle bundle, int[] animations, boolean addToBackStack) {
        return openPage(fragmentContainerID, fragmentManager.beginTransaction(), nameAlias, bundle, animations, addToBackStack);
    }

    public BaseFragment openPage(int fragmentContainerID, FragmentTransaction fragmentTransaction, String nameAlias, Bundle bundle, int[] animations, boolean addToBackStack) {
        BaseFragment fragment;
        try {
            CorePage corePage = this.mPageMap.get(nameAlias);
            if (corePage == null) {
                Log.e(TAG, "Page:" + nameAlias + " is null");
                return null;
            }

            if (!corePage.type.equals(CorePage.PAGE_TYPE_FRAGMENT)) {
                Log.e(TAG, "This method only can access by fragment--> Page:" + nameAlias);
                return null;
            }

            fragment = (BaseFragment) Class.forName(getClazzByTemplate(corePage.clazz, corePage.template)).newInstance();

            Bundle pageBundle = buildBundle(corePage);
            if (null != pageBundle && bundle != null) {
                pageBundle.putAll(bundle);
            }

            if (null != pageBundle) {
                fragment.setArguments(pageBundle);
            }

            if (animations != null && animations.length >= 4) {
                fragmentTransaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
            }

            if (!addToBackStack) {
                fragmentTransaction.replace(fragmentContainerID, fragment, nameAlias);
            } else {
                fragmentTransaction.add(fragmentContainerID, fragment, nameAlias);
                fragmentTransaction.addToBackStack(nameAlias);
            }
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Fragment.error:" + e.getMessage());
            return null;
        }

        return fragment;
    }

    public Fragment getPage(String nameAlias, Bundle bundle, boolean needTemplate) {
        BaseFragment fragment;
        try {
            CorePage corePage = this.mPageMap.get(nameAlias);
            if (corePage == null) {
                Log.e(TAG, "Page:" + nameAlias + " is null");
                return null;
            }

            if (!corePage.type.equals(CorePage.PAGE_TYPE_FRAGMENT)) {
                Log.e(TAG, "This method only can access by fragment--> Page:" + nameAlias);
                return null;
            }

            if (needTemplate) {
                fragment = (BaseFragment) Class.forName(getClazzByTemplate(corePage.clazz, corePage.template)).newInstance();
            } else {
                fragment = (BaseFragment) Class.forName(corePage.clazz).newInstance();
            }

            Bundle pageBundle = buildBundle(corePage);
            if (null != pageBundle && bundle != null) {
                pageBundle.putAll(bundle);
            }

            if (null != pageBundle) {
                fragment.setArguments(pageBundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Fragment.error:" + e.getMessage());
            return null;
        }

        return fragment;
    }

    public Fragment getPage(String nameAlias, Bundle bundle) {
        return getPage(nameAlias, bundle, false);
    }

    private Bundle buildBundle(CorePage corePage) {
        Bundle bundle = new Bundle();
        String key;
        Object value;
        if (corePage != null && corePage.getParams() != null) {
            JSONObject j = JSON.parseObject(corePage.getParams());
            if (j != null) {
                Set<String> keySet = j.keySet();
                if (keySet != null) {
                    Iterator<String> ite = keySet.iterator();
                    while (ite.hasNext()) {
                        key = ite.next();
                        value = j.get(key);
                        bundle.putString(key, value.toString());
                    }
                }
            }
        }
        return bundle;
    }
}