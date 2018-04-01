package com.wanandroid.ykk.pluglin_lib.enerty;

import java.util.List;

/**
 * Created by yukun on 18-1-8.
 */

public class KnowledgeListInfo {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"QQ音乐技术团队","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2211,"link":"https://mp.weixin.qq.com/s/pS51kuCeT_zg1Lb60rwVnA","niceDate":"2018-01-19","origin":"","projectLink":"","publishTime":1516353183000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"[Android] Toast问题深度剖析(二)","type":0,"visible":1,"zan":0},{"apkLink":"","author":"QQ音乐技术团队","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2210,"link":"https://mp.weixin.qq.com/s/-bXalVki1KQh6Ey-XvKTjw","niceDate":"2018-01-19","origin":"","projectLink":"","publishTime":1516353171000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"[Android] Toast问题深度剖析(一)","type":0,"visible":1,"zan":0},{"apkLink":"","author":"qq_25867141","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1063,"link":"http://blog.csdn.net/qq_25867141/article/details/52807705","niceDate":"2016-11-05","origin":"CSDN","projectLink":"","publishTime":1478339429000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"[置顶] 【Android】当关闭通知消息权限后无法显示系统Toast的解决方案","type":0,"visible":1,"zan":0},{"apkLink":"","author":"尼古拉斯_赵四","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":149,"link":"http://blog.csdn.net/jiangwei0910410003/article/details/17096699","niceDate":"2016-06-15","origin":"CSDN","projectLink":"","publishTime":1465976277000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"Android中的Toast重复显示的问题","type":0,"visible":1,"zan":0},{"apkLink":"","author":"roykfw","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":148,"link":"http://blog.csdn.net/zhangweiwtmdbf/article/details/30031015","niceDate":"2016-06-15","origin":"CSDN","projectLink":"","publishTime":1465976239000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"Android自定义Toast","type":0,"visible":1,"zan":0},{"apkLink":"","author":"_Ryeeeeee ","chapterId":30,"chapterName":"Toast","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":147,"link":"http://www.jianshu.com/p/2088216e65fb","niceDate":"2016-06-15","origin":"简书","projectLink":"","publishTime":1465976214000,"superChapterId":30,"superChapterName":"用户交互","tags":[],"title":"「Android Tips」Toast 的一些使用技巧","type":0,"visible":1,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 6
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * apkLink :
         * author : QQ音乐技术团队
         * chapterId : 30
         * chapterName : Toast
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 2211
         * link : https://mp.weixin.qq.com/s/pS51kuCeT_zg1Lb60rwVnA
         * niceDate : 2018-01-19
         * origin :
         * projectLink :
         * publishTime : 1516353183000
         * superChapterId : 30
         * superChapterName : 用户交互
         * tags : []
         * title : [Android] Toast问题深度剖析(二)
         * type : 0
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int visible;
        private int zan;
        private List<?> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
