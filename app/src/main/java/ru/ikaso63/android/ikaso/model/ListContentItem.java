package ru.ikaso63.android.ikaso.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 20.11.2016.
 */

public class ListContentItem {

    private String id;
    private String title;
    private String preview;
    private String body;
    private String kindId;
    private String typeId;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPreview() {
        return preview;
    }

    public String getBody() {
        return body;
    }

    public String getKindId() {
        return kindId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
