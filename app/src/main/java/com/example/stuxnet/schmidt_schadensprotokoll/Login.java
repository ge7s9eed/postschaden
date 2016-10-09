package com.example.stuxnet.schmidt_schadensprotokoll;

import android.opengl.EGLDisplay;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by stuxnet on 09.10.16.
 */

public class Login extends AppCompatActivity {

    EditText    username;
    EditText    password;
    Button      login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        username    = (EditText)findViewById(R.id.et_username);
        password    = (EditText)findViewById(R.id.et_password);
        login       = (Button) findViewById(R.id.btn_login);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Start DB Abfrage ob der Benutzer schon registriert ist.
                loginDBQuery(username.getText().toString(), password.getText().toString());
            }
        });
    }

    public void loginDBQuery(String username, String password){

    }


}
