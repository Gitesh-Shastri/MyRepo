package com.careeranna.boloanna.models;

import java.util.ArrayList;

public class NavItems {

    private String title;

    private ArrayList<Category> categories;

    public NavItems(String title) {
        this.title = title;
        this.categories = new ArrayList<>();
    }

    public NavItems(String title, ArrayList<Category> categories) {
        this.title = title;
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
