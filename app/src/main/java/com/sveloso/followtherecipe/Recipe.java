package com.sveloso.followtherecipe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Veloso on 5/31/2016.
 */
public class Recipe {

    private UUID mId;
    private String mTitle;
    private String mIngredients;
    private String mDirections;

    public Recipe() {
        this(UUID.randomUUID());
    }

    public Recipe(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public String getDirections() {
        return mDirections;
    }

    public void setDirections(String directions) {
        mDirections = directions;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
