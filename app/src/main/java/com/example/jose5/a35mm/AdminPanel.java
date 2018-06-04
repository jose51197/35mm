package com.example.jose5.a35mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminPanel extends AppCompatActivity {
    private Button ver;
    private Button nueva;
    private Button ban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ver = (Button) findViewById(R.id.adminMovies);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,AdminMain.class));
            }
        });
        nueva = (Button) findViewById(R.id.adminNew);
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,AddMovieActivity.class));
            }
        });
        ban = (Button) findViewById(R.id.adminUsers);
        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,BanActivity.class));
            }
        });
    }
}
