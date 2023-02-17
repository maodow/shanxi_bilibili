package tv.huan.bilibili.bean;

import java.io.Serializable;
import java.util.List;

public class ParentCategory implements Serializable {

    private static final long serialVersionUID = 2743682384102953282L;
    private HomeTag parent ;
    private List<HomeTag> classes ;

    @Override
    public String toString() {
        return "ParentCategory{" +
                "parent=" + parent +
                ", classes=" + classes +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HomeTag getParent() {
        return parent;
    }

    public void setParent(HomeTag parent) {
        this.parent = parent;
    }

    public List<HomeTag> getClasses() {
        return classes;
    }

    public void setClasses(List<HomeTag> classes) {
        this.classes = classes;
    }
}
