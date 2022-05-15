package com.example.linxoapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * modèle pour définir les données dans l'adaptateur à l'aide de la méthode getter de photo l'api
 * */

public class TestModel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("id")
    @Expose
    private String id;

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
