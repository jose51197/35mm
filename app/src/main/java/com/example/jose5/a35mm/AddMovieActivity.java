package com.example.jose5.a35mm;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddMovieActivity extends AppCompatActivity{

    private EditText movieDescription;
    private EditText movieName;
    private EditText movieImage;
    private EditText movieYear;
    private EditText movieDirectors;
    private EditText movieActors;
    private Spinner genders;
    private Button addMovieButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.movieDescription = findViewById(R.id.movieUserDescription);
        this.movieName = findViewById(R.id.movieName);
        this.movieImage = findViewById(R.id.movieUserImage);
        this.movieYear = findViewById(R.id.movieYear);
        this.movieDirectors = findViewById(R.id.movieDirectors);
        this.movieActors = findViewById(R.id.movieActors);
        this.addMovieButton = findViewById(R.id.addMovie);

        //getGenders();
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
    }

    private void getGenders(){
        //Conexion con = new Conexion();
        //JSONArray genders = con.Query("SELECT * FROM genero");
        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner,arraySpinner);
        adapter.setDropDownViewResource(R.layout.simple_spinner);
        this.genders.setAdapter(adapter);
    }

    private void addMovie(){
        Conexion con = new Conexion();
        if (con.Insert("Pelicula", "Nombre,Descripcion,Foto,anhio,Actores,Directores", movieDescription.getText().toString()+","+
                movieName.getText().toString()+","+movieImage.getText().toString()+","+ movieYear.getText().toString()
                +","+ movieActors.getText().toString()+","+ movieDirectors.getText().toString())){
            startActivity(new Intent(AddMovieActivity.this,AdminMain.class));
        }
        addMovieButton.setError("No insertado");
    }

}
