package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Genero;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

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

        this.movieDescription = findViewById(R.id.movieDescription);
        this.movieName = findViewById(R.id.movieName);
        this.movieImage = findViewById(R.id.movieImage);
        this.movieYear = findViewById(R.id.movieYear);
        this.movieDirectors = findViewById(R.id.movieDirectors);
        this.movieActors = findViewById(R.id.movieActors);
        this.addMovieButton = findViewById(R.id.addMovie);
        this.genders = findViewById(R.id.genders);

        GenreTask g= new GenreTask();
        g.execute();
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
    }

    private void genders(ArrayList<Genero> genres){
        final ArrayAdapter<Genero> adapter =
                new ArrayAdapter<Genero>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, genres);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                genders.setAdapter(adapter);

            }
        });

    }

    private void addMovie(){
        Conexion con = new Conexion();
        if (con.Insert("Pelicula", "Descripcion,Nombre,Foto,anhio,Actores,Directores,genero", movieDescription.getText().toString()+","+
                movieName.getText().toString()+","+movieImage.getText().toString()+","+ movieYear.getText().toString()
                +","+ movieActors.getText().toString()+","+ movieDirectors.getText().toString()+","+((Genero)genders.getSelectedItem()).getId())){
            startActivity(new Intent(AddMovieActivity.this,AdminMain.class));
        }
        addMovieButton.setError("No insertado");
    }
    public class GenreTask extends AsyncTask<Void, Void, Boolean> {


        GenreTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                JSONArray genres=c.Query("Select * from genero");
                ArrayList<Genero> generos= new ArrayList<>();
                for(int i =0;i<genres.length();i++ ){
                    generos.add(new Genero(genres.getJSONObject(i).getString("Genero"),genres.getJSONObject(i).getString("idGenero")));
                }
                genders(generos);


                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }

}
