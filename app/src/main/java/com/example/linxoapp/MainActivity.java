package com.example.linxoapp;

import static com.example.linxoapp.constant.Internet.Dataisempty;
import static com.example.linxoapp.constant.Internet.errortoast;
import static com.example.linxoapp.constant.Internet.noInternet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.linxoapp.Api.AdminAPI;
import com.example.linxoapp.Api.ServiceGenerator;
import com.example.linxoapp.Model.TestModel;
import com.example.linxoapp.constant.Internet;
import com.example.linxoapp.customfonts.MyTextViewBold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialoge;
    CustomList adapter;
    ListView list;
    AdminAPI adminAPI;
   ArrayList<TestModel> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminAPI = ServiceGenerator.getAPIClass();
        progressDialoge = new ProgressDialog(MainActivity.this);
        progressDialoge.setCancelable(false);
        setToolbar();
        fetchid();


       if (Internet.isConnectingToInternet(getApplicationContext())) {
           AlbumList();
        } else {
            noInternet(getApplicationContext());
        }


    }

    /**
     * méthode utilisée pour créer un objet de vue, mise en page
     * ***/
    private void fetchid() {

        list = (ListView) findViewById(R.id.MainListView);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
    /**
     * méthode utilisée pour définir la barre d'outils personnalisée
     * **/
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * méthode utilisée pour appeler Api
     * **/
   public void AlbumList() {

        try {
                progressDialoge.show();

            Call<List<TestModel>> responseCall = adminAPI.GET_ALBUM();

            responseCall.enqueue(new Callback<List<TestModel>>() {
                @Override
                public void onResponse(Call<List<TestModel>> call, Response<List<TestModel>> response) {
                    progressDialoge.show();
                    dataArrayList.clear();

                    if(response.isSuccessful()) {
                        progressDialoge.dismiss();
                        if (dataArrayList != null) {

                            dataArrayList = new ArrayList<>(response.body());
                            adapter = new CustomList(dataArrayList, MainActivity.this);
                            SortAlbumArraylist();
                            list.setAdapter(adapter);


                            adapter.notifyDataSetChanged();

                        } else {
                            Dataisempty(getApplicationContext());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<TestModel>> call, Throwable t) {
                    progressDialoge.dismiss();
                    t.printStackTrace();
                    errortoast(getApplicationContext(), t);
                }
            });

        }
        catch (Exception e)
        {
            Log.e("Exception cc",""+e);
        }

    }

    /**
     * méthode utilisée pour trier la liste d'albums par ordre alphabétique
     * **/

    private void SortAlbumArraylist()
    {
        Collections.sort(dataArrayList, new Comparator<TestModel>() {
            @Override
            public int compare(TestModel o1, TestModel o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        adapter.notifyDataSetChanged();
    }

    /**
     * créer un adaptateur pour appeler la liste d'albums
     * */
    @SuppressLint("RestrictedApi")

    public class CustomList extends BaseAdapter {
        private Context context;
        private List<TestModel> listData = new ArrayList<>();



        public CustomList(ArrayList<TestModel> listData,Context context) {

            this.context = context;
            this.listData = listData;

        }


        @Override
        public int getCount() {
            return listData.size();

        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View rowView, ViewGroup parent) {
            MyViewHolder holder = null;

            final TestModel rowItem = (TestModel) getItem(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.custom_album_layout, null);

            holder = new MyViewHolder();

            holder.album_textview=(MyTextViewBold) rowView.findViewById(R.id.txt_album_title);


            holder.album_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, GridActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", listData.get(position).getId());
                    startActivity(intent);
                }
            });

            holder.album_textview.setText(listData.get(position).getTitle());
            Log.e("title:",listData.get(position).getTitle());

            return rowView;
        }

        class MyViewHolder {

            TextView album_textview;

        }

    }

}