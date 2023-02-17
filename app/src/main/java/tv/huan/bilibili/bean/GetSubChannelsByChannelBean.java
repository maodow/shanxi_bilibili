package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

import lib.kalu.leanback.presenter.ListGridPresenter;
import lib.kalu.leanback.presenter.ListRowPresenter;

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

        public List<TemplateBean> getTemplateData() {
            return templateData;
        }

        public void setTemplateData(List<TemplateBean> templateData) {
            this.templateData = templateData;
        }

        @Keep
        public static class TemplateBean implements JumpBean, ListGridPresenter.ListGridBean, ListRowPresenter.ListRowBean, Serializable {

            private int show;
            private String title;
            private String name;
            private String cid;
            private int classId;
            private int toType;
            private String newPicVt;
            private String newPicHz;

            private List<LookBean> generalTemplate17Recs;
            private boolean generalTemplate17Selected = false;

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

            public int getShow() {
                return show;
            }

            public void setShow(int show) {
                this.show = show;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getNewPicVt() {
                return newPicVt;
            }

            public void setNewPicVt(String newPicVt) {
                this.newPicVt = newPicVt;
            }

            public String getNewPicHz() {
                return newPicHz;
            }

            public void setNewPicHz(String newPicHz) {
                this.newPicHz = newPicHz;
            }

            @Override
            public String getGridHead() {
                if (1 == getShow()) {
                    return getTitle();
                } else {
                    return null;
                }
            }

            @Override
            public String getRowHead() {
                if (1 == getShow()) {
                    return getTitle();
                } else {
                    return null;
                }
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
