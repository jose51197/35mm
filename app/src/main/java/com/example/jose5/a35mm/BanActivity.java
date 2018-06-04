package com.example.jose5.a35mm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jose5.a35mm.modelo.Genero;
import com.example.jose5.a35mm.modelo.User;

import org.json.JSONArray;

import java.util.ArrayList;

public class BanActivity extends AppCompatActivity {
    private Spinner notBanned;
    private Button ban;
    private Spinner Banned;
    private Button unban;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);
        notBanned=(Spinner) findViewById(R.id.notbanned);
        Banned=(Spinner) findViewById(R.id.banned);
        ban = (Button) findViewById(R.id.ban);
        unban = (Button) findViewById(R.id.unban);

        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.DEBUG,"ban",((User) notBanned.getSelectedItem()).getId());
                Ban b= new Ban(((User) notBanned.getSelectedItem()).getId());
                b.execute();
            }
        });

        unban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.DEBUG,"ban",((User) notBanned.getSelectedItem()).getId());
                unBan b= new unBan(((User) Banned.getSelectedItem()).getId());
                b.execute();
            }
        });

        BanTask b = new BanTask();
        b.execute();

    }

    private void bans(ArrayList<User> ban, final ArrayList<User> noban){
        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, ban);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Banned.setAdapter(adapter);

            }
        });
        final ArrayAdapter<User> adapter2 = new ArrayAdapter<User>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, noban);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                notBanned.setAdapter(adapter2);

            }
        });

    }

    public class BanTask extends AsyncTask<Void, Void, Boolean> {


        BanTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                JSONArray genres=c.Query("Select * from Users u inner join Banned b on b.idUser = u.IdUser");
                ArrayList<User> ban= new ArrayList<>();
                if(genres!=null){
                    for(int i =0;i<genres.length();i++ ){
                        Log.println(Log.DEBUG,"id",genres.getJSONObject(i).getString("idUser"));
                        ban.add(new User(genres.getJSONObject(i).getString("User"),genres.getJSONObject(i).getString("idUser")));
                    }
                }

                genres=c.Query("Select u.idUser,u.User from Users u left join Banned b on b.idUser = u.IdUser where b.idUser is null");
                ArrayList<User> notban= new ArrayList<>();
                if(genres!=null){
                    for(int i =0;i<genres.length();i++ ){
                        Log.println(Log.DEBUG,"notbanid",genres.getJSONObject(i).getString("idUser"));
                        notban.add(new User(genres.getJSONObject(i).getString("User"),genres.getJSONObject(i).getString("idUser")));
                    }
                }
                bans(ban,notban);
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                notificate("Cargados users");
            }else{
                notificate("Error bajando users");
            }
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }

    public class Ban extends AsyncTask<Void, Void, Boolean> {
        private String id;

        Ban(String id) {
            this.id=id;
            Log.println(Log.DEBUG,"id",id);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                return c.Insert("Banned","idUser",id);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                notificate("Baneado");
                BanTask b = new BanTask();
                b.execute();
            }else{
                notificate("Error bajando users");
            }
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }
    public class unBan extends AsyncTask<Void, Void, Boolean> {
        private String id;

        unBan(String id) {
            this.id=id;
            Log.println(Log.DEBUG,"id",id);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                Conexion c = new Conexion();
                return c.Delete("Banned","idUser="+id);
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                notificate("Desbaneado");
                BanTask b = new BanTask();
                b.execute();
            }else{
                notificate("Error bajando users");
            }
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }
}
