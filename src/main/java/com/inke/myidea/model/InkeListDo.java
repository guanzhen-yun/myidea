package com.inke.myidea.model;
/**
 * 注册人员列表
 */
public class InkeListDo {
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

    @Override
    public String toString() {
        return "InkeListDo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
