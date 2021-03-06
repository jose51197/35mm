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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class RecActivity extends AppCompatActivity {

    private android.support.v7.widget.GridLayout moviesRecs;
    private android.support.v7.widget.GridLayout moviesRecs2;
    ArrayList<Integer> allMovies = new ArrayList<>();
    private int index;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendations);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user = intent.getStringExtra("userId");
        Log.d("usr", user);

        moviesRecs = findViewById(R.id.moviesRecs);
        moviesRecs2 = findViewById(R.id.moviesRecs2);

        try {
            getMovies();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getMovies() throws IOException, JSONException {
        Conexion c = new Conexion();
        final JSONArray generos = c.Query("SELECT p.genero FROM Comentario c JOIN Pelicula p on " +
                "p.idPelicula = c.idPelicula WHERE c.idUser= " +user+ " GROUP BY p.genero");
        String movieGen = "";
        if (generos != null){
            Log.d("qy", generos.toString());
            for (int i=0; i<generos.length(); i++){
                if (i+1 == generos.length())
                    movieGen += "p.genero=" +generos.getJSONObject(i).getString("genero");
                else
                    movieGen += "p.genero=" + generos.getJSONObject(i).getString("genero") + " or ";
            }
        }
        final JSONArray movies = c.Query("SELECT * FROM Pelicula p WHERE "+ movieGen);
        if(movies!=null){
            Log.d("msg", movies.toString());
            int last = 0;
            for (int i=0; i<movies.length()/2; i++){
                allMovies.add(movies.getJSONObject(i).getInt("idPelicula"));
                URL imgURL = new URL(movies.getJSONObject(i).getString("Foto"));
                Bitmap bitmap = textAsBitmap(movies.getJSONObject(i).getString("Nombre"),1000, Color.BLACK);
                ImageView img = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(545, 795);
                img.setImageBitmap(bitmap);
                img.setLayoutParams(layoutParams);
                MovieTask get = new MovieTask(imgURL,img);
                get.execute();
                index = i+1;
                img.setOnClickListener(new MyOwnListener(index));
                this.moviesRecs.addView(img);
                last = i;
            }
            for (int i = last+1; i<movies.length(); i++){
                allMovies.add(movies.getJSONObject(i).getInt("idPelicula"));
                URL imgURL = new URL(movies.getJSONObject(i).getString("Foto"));
                Bitmap bitmap = textAsBitmap(movies.getJSONObject(i).getString("Nombre"),1000, Color.BLACK);
                ImageView img = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(545, 795);
                img.setImageBitmap(bitmap);
                img.setLayoutParams(layoutParams);
                MovieTask get = new MovieTask(imgURL,img);
                get.execute();
                index = i+1;
                img.setOnClickListener(new MyOwnListener(index));
                this.moviesRecs2.addView(img);
            }
        }

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
            Intent intent = new Intent(RecActivity.this, UserMovieInfo.class);
            intent.putExtra("id", String.valueOf(allMovies.get(index-1)));
            intent.putExtra("userId", user);
            startActivity(intent);
        }
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

}
