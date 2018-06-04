package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserMovieInfo extends AppCompatActivity {

    private TextView movieName;
    private TextView movieDescription;
    private TextView movieYear;
    private TextView movieDirectors;
    private TextView movieActors;
    private TextView movieGender;
    private ImageView movieImage;
    private String user;
    private Button addComment;
    private TextView comment;
    private String id;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_movie_info);
        Intent intent = getIntent();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        user = intent.getStringExtra("user");

        movieName = findViewById(R.id.movieUserName);
        movieDescription = findViewById(R.id.movieUserDescription);
        movieYear = findViewById(R.id.movieUserYear);
        movieImage = findViewById(R.id.movieUserImage);
        movieGender = findViewById(R.id.movieUserGender);
        movieDirectors = findViewById(R.id.movieUserDirectors);
        movieActors = findViewById(R.id.movieUserActors);
        addComment = findViewById(R.id.addComment);
        comment = findViewById(R.id.comment);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });;

        id = intent.getStringExtra("id");
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
    }

    private void addComment(){
        Conexion c = new Conexion();
        c.Insert("Comentario", "idUser, idPelicula, Comentario", user + "," +
                id+","+comment.getText().toString());
        Intent intent = new Intent(UserMovieInfo.this, UserMovieInfo.class);
        intent.putExtra("id", id);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
