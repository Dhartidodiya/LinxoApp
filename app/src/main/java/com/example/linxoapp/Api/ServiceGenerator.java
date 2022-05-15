package com.example.linxoapp.Api;



import androidx.annotation.NonNull;

import com.example.linxoapp.constant.ApiURLs;


import java.io.File;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * créer une classe servivegenerator pour appeler la requête http api pour la méthode post get patch avec des données multipart
 * */


public class ServiceGenerator {

    public static AdminAPI getAPIClass(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiURLs.BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AdminAPI.class);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        //Log.e("mimeType",mimeType);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        //MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private static OkHttpClient getRequestHeader() {

        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

        return httpClient;
    }





}
