package com.example.jose5.a35mm;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose5.a35mm.R;

import org.json.JSONArray;
import org.json.JSONException;

public class Register extends AppCompatActivity  {
    private RegisterTask mRegTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button regButton = (Button) findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register(){
        if (mRegTask!= null) {
            return;
        }
        EditText pass = (EditText) findViewById(R.id.rpass);
        EditText user = (EditText) findViewById(R.id.ruser);
        EditText nombre = (EditText) findViewById(R.id.rnombre);
        EditText email = (EditText) findViewById(R.id.remail);
        mRegTask= new RegisterTask(email.getText().toString(),pass.getText().toString(),user.getText().toString(),nombre.getText().toString());
        mRegTask.execute((Void) null);

    }

    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private String mEmail;
        private String mPassword;
        private String mUser;
        private String mName;

        RegisterTask(String email, String password,String user,String name) {
            mEmail = email;
            mPassword = password;
            mName=name;
            mUser=user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Conexion c = new Conexion();
            boolean registro = c.Insert("Users","Nombre,User,Pass,email",mName+","+mUser+","+mPassword+","+mEmail);
            //boolean registro = c.Insert("prueba","p,p2","6,7");
            return registro;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            if (success) {
                finish();
                notificate("Registrado");

            } else {
                notificate("Error de conexion/datos");
            }
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
        }

        private void notificate(String notification){
            Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_LONG).show();
        }
    }
}
