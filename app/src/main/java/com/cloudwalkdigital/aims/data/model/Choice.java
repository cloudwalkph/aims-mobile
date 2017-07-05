package com.cloudwalkdigital.aims.data.model;

/**
 * Created by alleoindong on 7/5/17.
 */

public class Choice {
    private Integer id;
    private String choice;
    private Double point;

    public Choice() {
    }

    public Choice(Integer id, String choice, Double point) {
        this.id = id;
        this.choice = choice;
        this.point = point;
    }

    public Integer getId() {
        return id;
    }

    public String getChoice() {
        return choice;
    }

    public Double getPoint() {
        return point;
    }
}
