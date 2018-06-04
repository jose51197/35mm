package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
    private android.widget.GridLayout comments;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_movie_info);
        Intent intent = getIntent();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        user = intent.getStringExtra("userId");
        Log.d("info", user);

        movieName = findViewById(R.id.movieUserName);
        movieDescription = findViewById(R.id.movieUserDescription);
        movieYear = findViewById(R.id.movieUserYear);
        movieImage = findViewById(R.id.movieUserImage);
        movieGender = findViewById(R.id.movieUserGender);
        movieDirectors = findViewById(R.id.movieUserDirectors);
        movieActors = findViewById(R.id.movieUserActors);
        addComment = findViewById(R.id.addComment);
        comment = findViewById(R.id.comment);
        comments = findViewById(R.id.movieComments);

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
        JSONArray commentaries = c.Query("SELECT c.Comentario, u.User FROM Comentario c JOIN Users u on c.idUser = u.idUser WHERE c.idPelicula= " + id);
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
            if (commentaries != null){
                TextView e = new TextView(this);
                String comment="";
                for (int i=0; i<commentaries.length(); i++){

                    comment+= commentaries.getJSONObject(i).get("User") + ": " + commentaries.getJSONObject(i).get("Comentario")+"\n";
                }
                e.setText(comment);
                comments.addView(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addComment(){
        Log.d("usr", user);
        Conexion c = new Conexion();
        c.Insert("Comentario", "idUser, idPelicula, Comentario", user + "," +
                id+","+comment.getText().toString());
        Intent intent = new Intent(UserMovieInfo.this, UserMovieInfo.class);
        intent.putExtra("id",id);
        intent.putExtra("userId",user);
        startActivity(intent);
    }
}
