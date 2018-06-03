package com.example.jose5.a35mm;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;

public class AdminMain extends AppCompatActivity {

    private ImageButton addMovie;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.addMovie = findViewById(R.id.imageButton8);

        getMovies();
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMain.this,AddMovieActivity.class));
            }
        });
    }

    private void getMovies() {
        Conexion c = new Conexion();
        JSONArray user= c.Query("SELECT * FROM Pelicula");
        Log.d("msg", user.toString());
    }
}
