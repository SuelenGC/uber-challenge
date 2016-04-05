package com.suelengc.flickrgrid.model;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;

public class Photo implements Serializable {

    private String url;

    public Photo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
