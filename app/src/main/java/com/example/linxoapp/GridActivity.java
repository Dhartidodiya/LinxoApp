package com.example.linxoapp;

import static com.example.linxoapp.constant.Internet.Dataisempty;
import static com.example.linxoapp.constant.Internet.errortoast;
import static com.example.linxoapp.constant.Internet.noInternet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.linxoapp.Api.AdminAPI;
import com.example.linxoapp.Api.ServiceGenerator;
import com.example.linxoapp.Model.GridPhotoModel;
import com.example.linxoapp.constant.Internet;
import com.example.linxoapp.customfonts.MyTextViewBold;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GridActivity extends AppCompatActivity {
    ProgressDialog progressDialoge;
    GridList Grid_adapter;
    GridView grid_view;
    AdminAPI adminAPI;
    String id="";
    Dialog dialog_photo;
    ImageView closepopup;
    ImageView detailImageview;
    ArrayList<GridPhotoModel> gridArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        adminAPI = ServiceGenerator.getAPIClass();
        progressDialoge = new ProgressDialog(GridActivity.this);
        progressDialoge.setCancelable(false);
        setToolbar();
        fetchIds();
        Intent intent = getIntent();




        if (Internet.isConnectingToInternet(getApplicationContext())) {
            id = intent.getExtras().getString("id");
            Log.e("id*****",id);
            GridList(id);

        } else {
            noInternet(getApplicationContext());
        }

    }

    /**
     * méthode utilisée pour créer un objet de vue, mise en page
     * ***/

    public void fetchIds() {
        grid_view = (GridView) findViewById(R.id.albumGridView);
    }
    /**
     * méthode utilisée pour définir la barre d'outils personnalisée
     * **/

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txttitle = (TextView) findViewById(R.id.title_l);
        ImageView imgback = (ImageView) findViewById(R.id.back);
        ImageView imghome = (ImageView) findViewById(R.id.home);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(GridActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                finish();
            }
        });
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(GridActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                finish();
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * méthode utilisée pour appeler Api
     * **/
    public void GridList(String id) {

        try {
            progressDialoge.show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/albums/"+id+"/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AdminAPI request = retrofit.create(AdminAPI.class);
            Call<List<GridPhotoModel>> responseCall=request.GET_PHOTO();

            responseCall.enqueue(new Callback<List<GridPhotoModel>>() {
                @Override
                public void onResponse(Call<List<GridPhotoModel>> call, Response<List<GridPhotoModel>> response) {
                    progressDialoge.show();
                    gridArrayList.clear();

                    if(response.isSuccessful()) {
                        progressDialoge.dismiss();
                        if (gridArrayList != null) {

                            gridArrayList = new ArrayList<>(response.body());
                            Grid_adapter = new GridList(gridArrayList, GridActivity.this);
                            SortAlbumArraylist();
                            grid_view.setAdapter(Grid_adapter);


                            Grid_adapter.notifyDataSetChanged();

                        } else {
                            Dataisempty(getApplicationContext());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<GridPhotoModel>> call, Throwable t) {
                    progressDialoge.dismiss();
                    Log.e("error" , ""+call);
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
     * méthode utilisée pour trier la Gridliste de photos par ordre alphabétique
     * **/

    private void SortAlbumArraylist()
    {
        Collections.sort(gridArrayList, new Comparator<GridPhotoModel>() {
            @Override
            public int compare(GridPhotoModel o1, GridPhotoModel o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        Grid_adapter.notifyDataSetChanged();
    }

    /**
     * créer un adaptateur pour appeler la liste d'photos
     * */
    @SuppressLint("RestrictedApi")

    public class GridList extends BaseAdapter {
        private Context context;
        private List<GridPhotoModel> gridData = new ArrayList<>();




        public GridList(ArrayList<GridPhotoModel> gridData,Context context) {

            this.context = context;
            this.gridData = gridData;
        }


        @Override
        public int getCount() {
            return gridData.size();

        }

        @Override
        public Object getItem(int position) {
            return gridData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View rowView, ViewGroup parent) {
            MyViewHolder holder = null;

            final GridPhotoModel rowItem = (GridPhotoModel) getItem(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.custom_photo_layout, null);

            holder = new MyViewHolder();

            holder.grid_TextView=(MyTextViewBold) rowView.findViewById(R.id.GridTextView);
            holder.grid_Imageview=(ImageView) rowView.findViewById(R.id.GridImageview);

            holder.grid_TextView.setText(gridData.get(position).getTitle());

            String image = gridData.get(position).getUrl();
            Picasso.get()
                    .load(image)
                    .into(holder.grid_Imageview);

            holder.grid_Imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoDialoge(rowItem);
                }
            });

            return rowView;
        }

        class MyViewHolder {

            TextView grid_TextView;
            ImageView grid_Imageview;

        }


        public void PhotoDialoge(final GridPhotoModel rowItem) {
            dialog_photo = new Dialog(GridActivity.this);
            dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_photo.setCancelable(true);
            dialog_photo.setContentView(R.layout.dialog_photo_view);
            dialog_photo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_photo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);




            closepopup = dialog_photo.findViewById(R.id.closepopup);
            detailImageview = dialog_photo.findViewById(R.id.detailImageview);

            String image = rowItem.getUrl();
            Picasso.get()
                    .load(image)
                    .into(detailImageview);


            closepopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_photo.dismiss();
                }
            });
            dialog_photo.show();

        }

    }
}