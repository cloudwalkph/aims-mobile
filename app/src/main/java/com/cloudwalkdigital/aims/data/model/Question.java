package com.cloudwalkdigital.aims.data.model;

import java.util.List;

/**
 * Created by alleoindong on 7/5/17.
 */

public class Question {
    private Integer id;
    private String projectType;
    private String validateType;
    private Integer raterDepartment;
    private Integer rateeDepartment;
    private String question;
    private List<Choice> choices;

    public Question() {
    }

    public Question(Integer id, String projectType, String validateType,
                    Integer raterDepartment, Integer rateeDepartment, String question,
                    List<Choice> choices) {
        this.id = id;
        this.projectType = projectType;
        this.validateType = validateType;
        this.raterDepartment = raterDepartment;
        this.rateeDepartment = rateeDepartment;
        this.question = question;
        this.choices = choices;
    }

    public Integer getId() {
        return id;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getValidateType() {
        return validateType;
    }

    public Integer getRaterDepartment() {
        return raterDepartment;
    }

    public Integer getRateeDepartment() {
        return rateeDepartment;
    }

    public String getQuestion() {
        return question;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
