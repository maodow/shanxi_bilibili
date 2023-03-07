package tv.huan.bilibili.bean;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import lib.kalu.leanback.presenter.bean.TvPresenterRowBean;
import tv.huan.bilibili.BuildConfig;

@Keep
public class GetSubChannelsByChannelBean implements Serializable {

    private ChannelBean channel;
    private List<ListBean> list;
    private List<ClassesBean> classes;
    private ClassParentBean parent;

    public ClassParentBean getParent() {
        return parent;
    }

    public void setParent(ClassParentBean parent) {
        this.parent = parent;
    }

    public List<ClassesBean> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesBean> classes) {
        this.classes = classes;
    }

    public ChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ChannelBean channel) {
        this.channel = channel;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Keep
    public static class ChannelBean implements Serializable {

    }

    @Keep
    public static class ListBean implements Serializable {

        private int preTemplate;  // 模板类型
        private String name; // 模板标题
        private int show; // 模板标题是否显示
        private List<TemplateBean> templateData; // 数据

        public List<TemplateBean> getTemplateData() {
            return templateData;
        }

        public void setTemplateData(List<TemplateBean> templateData) {
            this.templateData = templateData;
        }

        public int getPreTemplate() {
            return preTemplate;
        }

        public void setPreTemplate(int preTemplate) {
            this.preTemplate = preTemplate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        @Keep
        public static class TemplateBean extends MediaBaseImageBean implements JumpBean, TvPresenterRowBean, Serializable {

            private boolean tempShow;
            private String tempTitle;

            private String name;
            private String cid;
            private int classId;
            private int toType;

            private List<LookBean> generalTemplate17Recs;
            private boolean generalTemplate17Selected = false;

            public TemplateBean() {
            }

            public TemplateBean( String poster, String newPicVt, String newPicHz,boolean tempShow, String tempTitle, String name, String cid, int classId, int toType, List<LookBean> generalTemplate17Recs, boolean generalTemplate17Selected) {
                super(poster, newPicVt, newPicHz);
                this.tempShow = tempShow;
                this.tempTitle = tempTitle;
                this.name = name;
                this.cid = cid;
                this.classId = classId;
                this.toType = toType;
                this.generalTemplate17Recs = generalTemplate17Recs;
                this.generalTemplate17Selected = generalTemplate17Selected;
            }

            public List<LookBean> getGeneralTemplate17Recs() {
                return generalTemplate17Recs;
            }

            public void setGeneralTemplate17Recs(List<LookBean> generalTemplate17Recs) {
                this.generalTemplate17Recs = generalTemplate17Recs;
            }

            public boolean isGeneralTemplate17Selected() {
                return generalTemplate17Selected;
            }

            public void setGeneralTemplate17Selected(boolean generalTemplate17Selected) {
                this.generalTemplate17Selected = generalTemplate17Selected;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public void setTempShow(boolean tempShow) {
                this.tempShow = tempShow;
            }

            public void setTempTitle(String tempTitle) {
                this.tempTitle = tempTitle;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public int getToType() {
                return toType;
            }

            public void setToType(int toType) {
                this.toType = toType;
            }

            @Override
            public String getRowTitle() {
                if(BuildConfig.HUAN_TEST_TEMPLATE_ENABLE){
                    return tempTitle;
                }
                else if (tempShow){
                    return tempTitle;
                } else {
                    return null;
                }
            }

            @NonNull
            @Override
            public String toString() {
                return "rowTitle = " + getRowTitle();
            }

            @Override
            public TemplateBean clone() {
                String picture = getPicture(false);
                String picture1 = getPicture(true);
                return new TemplateBean(picture, picture,picture1, tempShow, tempTitle, name, cid,classId, toType, generalTemplate17Recs,  generalTemplate17Selected);
            }
        }
    }

    public static class ClassesBean implements Serializable {
        /**
         * id : 119
         * productId : 1
         * name : 动作
         * parentId : 1
         * hasNext : 0
         * pos : 1
         * status : 1
         */

        private int id;
        private int productId;
        private String name;
        private int parentId;
        private int hasNext;
        private int pos;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getHasNext() {
            return hasNext;
        }

        public void setHasNext(int hasNext) {
            this.hasNext = hasNext;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    @Keep
    public static class ClassParentBean implements Serializable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
