package com.example.linxoapp.Api;



import com.example.linxoapp.Model.GridPhotoModel;
import com.example.linxoapp.Model.TestModel;
import com.example.linxoapp.constant.ApiURLs;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * créer une interface AdminApi pour appeler l'api
 **/


public interface AdminAPI {

    @GET(ApiURLs.GET_ALBUMDATA)

    Call<List<TestModel>> GET_ALBUM();

    @GET(ApiURLs.GET_PHOTODATA)

    Call<List<GridPhotoModel>> GET_PHOTO();

}