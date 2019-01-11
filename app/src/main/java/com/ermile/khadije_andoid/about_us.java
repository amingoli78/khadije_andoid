package com.ermile.khadije_andoid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ermile.khadije_andoid.network.AppContoroler;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class about_us extends AppCompatActivity {

    TextView titles, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        titles = findViewById(R.id.about_title);
        desc  = findViewById(R.id.about_desc);

        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();

        final Boolean farsi = shared.getBoolean("farsi", false);
        final Boolean arabic = shared.getBoolean("arabic", false);
        final Boolean english = shared.getBoolean("english", false);



        String url = "";
        if (farsi){
            url = "https://khadije.com/api/v5/about";
        }
        if (arabic){
            url = "https://khadije.com/ar/api/v5/about";
        }
        if (english){
            url = "https://khadije.com/en/api/v5/about";
        }


        // JSON
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String title = response.getString("title");
                    String dees = response.getString("content");
                    titles.setText(title);
                    String desc_decod = Jsoup.parse(dees).body().text();
                    desc.setText(desc_decod);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }


        });
        AppContoroler.getInstance().addToRequestQueue(req);
        // END JSON


    }
}
