package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jose5.a35mm.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieInfo extends AppCompatActivity {

    private TextView movieName;
    private TextView movieDescription;
    private TextView movieYear;
    private TextView movieDirectors;
    private TextView movieActors;
    private TextView movieGender;
    private ImageView movieImage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);
        Intent intent = getIntent();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        movieName = findViewById(R.id.movieName);
        movieDescription = findViewById(R.id.movieDescription);
        movieYear = findViewById(R.id.movieYear);
        movieImage = findViewById(R.id.movieImage);
        movieGender = findViewById(R.id.movieGender);
        movieDirectors = findViewById(R.id.movieDirectors);
        movieActors = findViewById(R.id.movieActors);

        String id = intent.getStringExtra("id");
        Conexion c = new Conexion();
        JSONArray movies= c.Query("SELECT p.*, g.Genero FROM Pelicula p JOIN genero g on g.idGenero = p.genero WHERE idPelicula= " + id);
        Log.d("res", movies.toString());
        try {
            movieName.setText(movies.getJSONObject(0).getString("Nombre"));
            movieDescription.setText(movies.getJSONObject(0).getString("Descripcion"));
            movieYear.setText(movies.getJSONObject(0).getString("anhio"));
            URL imgURL = new URL(movies.getJSONObject(0).getString("Foto"));
            Bitmap bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(545, 795);
            movieImage.setLayoutParams(layoutParams);
            movieImage.setImageBitmap(bitmap);
            movieDirectors.setText(movies.getJSONObject(0).getString("Directores"));
            movieActors.setText(movies.getJSONObject(0).getString("Actores"));
            movieGender.setText(movies.getJSONObject(0).getString("Genero"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            //Pelicula pelicula = getPelicula(id);
            //movieName.setText(pelicula.getNombre());
            //movieDescription.setText(pelicula.getDescripcion());
            //movieYear.setText(pelicula.getAnhio());
            //URL imgURL = new URL(pelicula.getFoto());
            //Bitmap bitmap = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
            //movieImage.setImageBitmap(bitmap);
            //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(545, 795);
            //movieImage.setLayoutParams(layoutParams);
        } catch (JSONException e) {
            e.printStackTrace();
        //} catch (MalformedURLException e) {
          //  e.printStackTrace();
        //} catch (IOException e) {
         //   e.printStackTrace();
        }*/


    }

    protected Pelicula getPelicula(int id) throws JSONException {
        Pelicula p = new Pelicula();
        Conexion c = new Conexion();
        final JSONArray movies= c.Query("SELECT * FROM Pelicula WHERE idPelicula=" + 1);
        p.setNombre(movies.getJSONObject(0).getString("Nombre"));
        p.setDescripcion(movies.getJSONObject(0).getString("Descripcion"));
        p.setFoto(movies.getJSONObject(0).getString("Foto"));
        p.setAnhio(movies.getJSONObject(0).getInt("anhio"));
        movieName.setText(p.getNombre());
        movieDescription.setText(p.getDescripcion());
        movieYear.setText(p.getAnhio());
        return p;
    }
}
