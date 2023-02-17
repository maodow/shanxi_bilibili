package tv.huan.bilibili.bean;

import java.io.Serializable;

public class AddHistory implements Serializable {


    private static final long serialVersionUID = -2037347775393717103L;

    private int optCode ;
    private String description ;
    private int id ;

    @Override
    public String toString() {
        return "AddHistory{" +
                "optCode=" + optCode +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getOptCode() {
        return optCode;
    }

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
