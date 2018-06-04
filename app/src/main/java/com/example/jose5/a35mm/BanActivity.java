package com.example.jose5.a35mm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Genero;

import org.json.JSONArray;

import java.util.ArrayList;

public class BanActivity extends AppCompatActivity {
    private Spinner notBanned;
    private Button ban;
    private Spinner Banned;
    private Button unban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);
        notBanned=(Spinner) findViewById(R.id.banned);
        Banned=(Spinner) findViewById(R.id.notbanned);
        ban = (Button) findViewById(R.id.ban);
        unban = (Button) findViewById(R.id.unban);
    }
    public class BanTask extends AsyncTask<Void, Void, Boolean> {


        BanTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                JSONArray genres=c.Query("Select * from users");
                ArrayList<Genero> generos= new ArrayList<>();
                for(int i =0;i<genres.length();i++ ){
                    generos.add(new Genero(genres.getJSONObject(i).getString("Genero"),genres.getJSONObject(i).getString("idGenero")));
                }
                //genders(generos);


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
