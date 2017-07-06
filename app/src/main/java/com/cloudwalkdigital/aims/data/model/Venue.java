package com.cloudwalkdigital.aims.data.model;

import io.realm.RealmObject;

/**
 * Created by alleoindong on 7/5/17.
 */

public class Venue extends RealmObject {
    private Integer id;
    private String category;
    private String subcategory;
    private String venue;

    public Venue() {
    }

    public Venue(Integer id, String category, String subcategory, String venue) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.venue = venue;
    }

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getVenue() {
        return venue;
    }
}
