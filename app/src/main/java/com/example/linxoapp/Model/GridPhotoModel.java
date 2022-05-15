package com.example.linxoapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * modèle pour définir les données dans l'adaptateur à l'aide de la méthode getter de Album l'api
 * */

public class GridPhotoModel {

    @SerializedName("albumId")
    @Expose
    private String albumId;

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("url")
    @Expose
    private String url;


    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;

    public String getAlbumId() {
        return albumId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
