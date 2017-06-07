package ru.ikaso63.android.ikaso.model;

/**
 * Created by home on 12.05.2017.
 */

public class ImagesItem {

    private int id;
    private String url;
    private String name_banners;
    private boolean active;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName_banners() {
        return name_banners;
    }

    public boolean isActive() {
        return active;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
