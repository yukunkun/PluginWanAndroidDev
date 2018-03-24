package com.wanandroid.ykk.pluglin_lib.viewinject.view;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ViewInjectInfo {

    public Object value;

    public String parentId;

    public ViewInjectInfo() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ViewInjectInfo)) {
            return false;
        } else {
            ViewInjectInfo that = (ViewInjectInfo) o;
            return this.parentId != that.parentId ? false : (this.value == null ? null == that.value : this.value.equals(that.value));
        }
    }

    public int hashCode() {
        int result = this.value.hashCode();
        result = 31 * result + this.parentId.hashCode();
        return result;
    }
}
