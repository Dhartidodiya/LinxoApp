package com.example.linxoapp.constant;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.linxoapp.R;

/**
 * classe constante pour vérifier la connexion Internet
 * */


public class Internet {

    public static boolean isConnectingToInternet(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void noInternet(Context context) {
        CustomToast(context,"Please check Your Internet");

    }

    public static void Dataisempty(Context context) {
        CustomToast(context,"Data is Empty");
    }


    public static void errortoast(Context context,Throwable throwable)
    {
        CustomToast(context,"Something went wrong, Please try again later!");

    }


    public static void CustomToast(Context context, String st) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(st);
        text.setTextSize(14);
        text.setTextColor(context.getResources().getColor(R.color.purple_200));
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);

        if (st != null && !st.trim().isEmpty()) toast.show();

    }

}
