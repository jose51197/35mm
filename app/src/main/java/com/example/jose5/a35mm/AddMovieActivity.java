package com.example.jose5.a35mm;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.sql.Connection;

public class AddMovieActivity extends AppCompatActivity{

    private EditText movieDescription;
    private EditText movieName;
    private EditText movieImage;
    private EditText movieYear;
    private Button addMovieButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.movieDescription = findViewById(R.id.movieDescription);
        this.movieName = findViewById(R.id.movieName);
        this.movieImage = findViewById(R.id.movieImage);
        this.movieYear = findViewById(R.id.movieYear);
        this.addMovieButton = findViewById(R.id.addMovie);

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
    }

    private void addMovie(){
        Conexion con = new Conexion();
        try {
            con.AddMovie(movieName.getText().toString(),movieDescription.getText().toString(),
                    movieImage.getText().toString(),movieYear.getText().toString());
            startActivity(new Intent(AddMovieActivity.this,AdminMain.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
