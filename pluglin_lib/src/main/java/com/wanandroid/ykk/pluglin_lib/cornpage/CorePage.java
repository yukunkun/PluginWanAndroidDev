package com.wanandroid.ykk.pluglin_lib.cornpage;

import java.io.Serializable;

public class CorePage implements Serializable {
    private static final long serialVersionUID = 3736359137726536495L;

    public static final String PAGE_TYPE_ACTIVITY = "activity";

    public static final String PAGE_TYPE_FRAGMENT = "fragment";

    /**
     * the type of target what you want to open, activity: activity, fragment: fragment
     */
    public String type;

    /**
     * the alias name of the page
     */
    public String nameAlias;

    /**
     * the class of the page
     */
    public String clazz;

    /**
     * the template of the page
     */
    public String template;

    /**
     * the params
     */
    public String params;

    public CorePage(String type, String nameAlias, String clazz, String params, String template) {
        this.type = type;
        this.nameAlias = nameAlias;
        this.clazz = clazz;
        this.params = params;
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameAlias() {
        return nameAlias;
    }

    public void setNameAlias(String nameAlias) {
        this.nameAlias = nameAlias;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public static final class Tags {
        public static final String TYPE = "type";

        public static final String NAME_ALIAS = "nameAlias";

        public static final String CLAZZ = "clazz";

        public static final String PARAMS = "params";

        public static final String TEMPLATE = "template";
    }
}