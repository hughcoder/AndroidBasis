package com.hugh.basis.home.model;

/**
 * Created by chenyw on 2020/8/11.
 */
public class HomeTabEntity {
    String pic;
    String title;
    int type;

    public HomeTabEntity(String pic, String title,int type) {
        this.pic = pic;
        this.title = title;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
