package com.wanandroid.ykk.pluglin_lib.viewinject.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ViewFinder {

    private View view;

    private Activity activity;

    private Resources mResources;

    private String packageName = "";

    private PreferenceGroup preferenceGroup;

    private PreferenceActivity preferenceActivity;

    public ViewFinder(View view) {
        this.view = view;

        this.mResources = view.getContext().getResources();

        this.packageName = view.getContext().getPackageName();
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;

        this.mResources = activity.getResources();

        this.packageName = activity.getPackageName();
    }

    public ViewFinder(PreferenceGroup preferenceGroup) {
        this.preferenceGroup = preferenceGroup;

        this.mResources = preferenceGroup.getContext().getResources();

        this.packageName = preferenceGroup.getContext().getPackageName();
    }

    public ViewFinder(PreferenceActivity preferenceActivity) {
        this.preferenceActivity = preferenceActivity;
        this.activity = preferenceActivity;
        this.mResources = this.activity.getResources();
        this.packageName = this.activity.getPackageName();
    }

    public View findViewById(int id) {
        return this.activity == null ? this.view.findViewById(id) : this.activity.findViewById(id);
    }

    public View findViewByInfo(ViewInjectInfo info) {
        return this.findViewById((String) info.value, info.parentId);
    }

    public View findViewById(String id, String pid) {
        View pView = null;
        if (!TextUtils.isEmpty(pid)) {
            int parentId = mResources.getIdentifier(pid, "id", packageName);
            if (parentId > 0) {
                pView = this.findViewById(parentId);
            }
        }

        View view = null;
        int viewId = mResources.getIdentifier(id, "id", packageName);
        if (pView != null && viewId > 0) {
            view = pView.findViewById(viewId);
        } else {
            view = this.findViewById(viewId);
        }

        return view;
    }

    public Preference findPreference(CharSequence key) {
        return this.preferenceGroup == null ? this.preferenceActivity.findPreference(key) : this.preferenceGroup.findPreference(key);
    }

    public Context getContext() {
        return (Context) (this.view != null ? this.view.getContext() : (this.activity != null ? this.activity : (this.preferenceActivity != null ? this.preferenceActivity : null)));
    }
}
