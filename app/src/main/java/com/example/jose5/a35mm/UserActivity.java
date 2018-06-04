package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ImageButton recs;
    private ImageButton logOut;
    private android.support.v7.widget.GridLayout movies;
    private android.support.v7.widget.GridLayout movies2;
    ArrayList<Integer> allMovies = new ArrayList<>();
    private int index;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user = intent.getStringExtra("id");

        recs = findViewById(R.id.recommendations);
        logOut = findViewById(R.id.logOut);
        movies = findViewById(R.id.moviesUser);
        movies2 = findViewById(R.id.moviesUser2);

        try {
            getMovies();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
            }
        });

    }

    private void getMovies() throws IOException, JSONException {
        Conexion c = new Conexion();
        final JSONArray movies= c.Query("SELECT * FROM Pelicula");
        Log.d("msg", movies.toString());
        int last = 0;
        for (int i=0; i<movies.length()/2; i++){
            allMovies.add(movies.getJSONObject(i).getInt("idPelicula"));
            URL imgURL = new URL(movies.getJSONObject(i).getString("Foto"));
            Bitmap bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(545, 795);
            img.setImageBitmap(bitmap);
            img.setLayoutParams(layoutParams);
            index = i+1;
            img.setOnClickListener(new MyOwnListener(index));
            this.movies.addView(img);
            last = i;
        }
        for (int i = last+1; i<movies.length(); i++){
            allMovies.add(movies.getJSONObject(i).getInt("idPelicula"));
            URL imgURL = new URL(movies.getJSONObject(i).getString("Foto"));
            Bitmap bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            final ImageView img = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(545, 795);
            img.setImageBitmap(bitmap);
            img.setLayoutParams(layoutParams);
            index = i+1;
            img.setOnClickListener(new MyOwnListener(index));
            this.movies2.addView(img);
        }
    }

    public class MyOwnListener implements View.OnClickListener
    {
        // ...
        int index;

        public MyOwnListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(UserActivity.this, UserMovieInfo.class);
            intent.putExtra("id", String.valueOf(index));
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

}
