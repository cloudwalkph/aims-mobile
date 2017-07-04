package com.cloudwalkdigital.aims.data.model;

/**
 * Created by alleoindong on 7/4/17.
 */

public class Department {
    protected Integer id;
    protected String name;
    protected String slug;

    public Department() {
    }

    public Department(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
