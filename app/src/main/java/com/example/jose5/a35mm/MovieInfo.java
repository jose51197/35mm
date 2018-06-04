package com.example.jose5.a35mm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class MovieInfo extends AppCompatActivity {

    private TextView movieName;
    private TextView movieDescription;
    private TextView movieYear;
    private TextView movieDirectors;
    private TextView movieActors;
    private TextView movieGender;
    private ImageView movieImage;
    private String id;


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

        id = intent.getStringExtra("id");
        Conexion c = new Conexion();
        JSONArray movies= c.Query("SELECT p.*, g.Genero FROM Pelicula p JOIN genero g on g.idGenero = p.genero WHERE idPelicula= " + id);
        Log.d("res", movies.toString());
        try {
            movieName.setText(movies.getJSONObject(0).getString("Nombre"));
            movieDescription.setText(movies.getJSONObject(0).getString("Descripcion"));
            movieYear.setText(movies.getJSONObject(0).getString("anhio"));
            URL imgURL = new URL(movies.getJSONObject(0).getString("Foto"));
            Bitmap bitmap = textAsBitmap(movies.getJSONObject(0).getString("Nombre"),1000, Color.BLACK);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(545, 795);
            movieImage.setLayoutParams(layoutParams);
            movieImage.setImageBitmap(bitmap);
            movieDirectors.setText(movies.getJSONObject(0).getString("Directores"));
            movieActors.setText(movies.getJSONObject(0).getString("Actores"));
            movieGender.setText(movies.getJSONObject(0).getString("Genero"));
            MovieTask get = new MovieTask(imgURL,movieImage);
            get.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteTask del = new DeleteTask(id);
                del.execute();
            }
        });



    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    public class MovieTask extends AsyncTask<Void, Void, Boolean> {

        private URL url;
        private ImageView view;

        MovieTask(URL url,ImageView view) {
            this.url=url;
            this.view=view;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        view.setImageBitmap(bitmap);

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
            if (success) {
            } else {
                notificate("Error bajando imagen");
            }
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }

    public class DeleteTask extends AsyncTask<Void, Void, Boolean> {

        private String id;
        private ImageView view;

        DeleteTask(String id) {
            this.id=id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                return c.Delete("Pelicula","idPelicula="+id);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                notificate("Borrada");
                finish();
            } else {
                notificate("Error borrando pelicula");
            }
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }
}
