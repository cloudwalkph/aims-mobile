package com.cloudwalkdigital.aims.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alleoindong on 7/4/17.
 */

public class Role extends RealmObject {
    @PrimaryKey
    protected Integer id;
    protected String name;
    protected String slug;

    public Role() {
    }

    public Role(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
