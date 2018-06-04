package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Genero;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    String id;
    private EditText movieDescription;
    private EditText movieName;
    private EditText movieImage;
    private EditText movieYear;
    private EditText movieDirectors;
    private EditText movieActors;
    private Spinner genders;
    private Button addMovieButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id = getIntent().getStringExtra("id");
        this.movieDescription = findViewById(R.id.emovieDescription);
        this.movieName = findViewById(R.id.emovieName);
        this.movieImage = findViewById(R.id.emovieImage);
        this.movieYear = findViewById(R.id.emovieYear);
        this.movieDirectors = findViewById(R.id.emovieDirectors);
        this.movieActors = findViewById(R.id.eMovieActors);
        this.addMovieButton = findViewById(R.id.eaddMovie);
        this.genders = findViewById(R.id.eMovieGender);

        GenreTask g= new GenreTask();
        g.execute();
        MovieTask t = new MovieTask();
        t.execute();
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
    }

    private void addMovie(){
        Conexion con = new Conexion();
        if (con.Delete("Pelicula","idPelicula="+id) && con.Insert("Pelicula", "idPelicula,Descripcion,Nombre,Foto,anhio,Actores,Directores,genero", id+movieDescription.getText().toString()+","+
                movieName.getText().toString()+","+movieImage.getText().toString()+","+ movieYear.getText().toString()
                +","+ movieActors.getText().toString()+","+ movieDirectors.getText().toString()+","+((Genero)genders.getSelectedItem()).getId())){
            startActivity(new Intent(EditActivity.this,AdminMain.class));
        }
        addMovieButton.setError("No insertado");
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
    public class MovieTask extends AsyncTask<Void, Void, Boolean> {


        MovieTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                final JSONArray movies=c.Query("Select * from Pelicula where idPelicula="+id);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            movieName.setText(movies.getJSONObject(0).getString("Nombre"));
                            movieDescription.setText(movies.getJSONObject(0).getString("Descripcion"));
                            movieYear.setText(movies.getJSONObject(0).getString("anhio"));
                            movieImage.setText(movies.getJSONObject(0).getString("Foto"));
                            movieDirectors.setText(movies.getJSONObject(0).getString("Directores"));
                            movieActors.setText(movies.getJSONObject(0).getString("Actores"));
                        }catch(Exception e){
                            notificate("No se pudo obtener peli");
                        }

                    }
                });
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
