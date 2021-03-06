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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class AdminMain extends AppCompatActivity {

    private android.support.v7.widget.GridLayout movies;
    private android.support.v7.widget.GridLayout movies2;
    ArrayList<Integer> allMovies = new ArrayList<>();
    private int index;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adm_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.movies = findViewById(R.id.movies);
        this.movies2 = findViewById(R.id.movies2);

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
        final JSONArray movies= c.Query("SELECT * FROM Pelicula");
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
            this.movies.addView(img);
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
            this.movies2.addView(img);
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
            Intent intent = new Intent(AdminMain.this, MovieInfo.class);
            intent.putExtra("id", String.valueOf(allMovies.get(index-1)));
            startActivity(intent);
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
