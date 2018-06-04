package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.jose5.a35mm.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AdminMain extends AppCompatActivity {

    private ImageButton addMovie;
    private android.support.v7.widget.GridLayout movies;
    private android.support.v7.widget.GridLayout movies2;
    ArrayList<Integer> allMovies = new ArrayList<>();
    private int index;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.addMovie = findViewById(R.id.imageButton8);
        this.movies = findViewById(R.id.movies);
        this.movies2 = findViewById(R.id.movies2);

        try {
            getMovies();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMain.this,AddMovieActivity.class));
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
            index = i;
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
            index = i;
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
            Log.d("idPelicula", String.valueOf(allMovies.get(index)));
        }
    }
}
