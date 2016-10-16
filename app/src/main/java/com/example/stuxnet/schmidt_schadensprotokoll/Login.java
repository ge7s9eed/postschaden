package com.example.stuxnet.schmidt_schadensprotokoll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by stuxnet on 09.10.16.
 */

public class Login extends AppCompatActivity {

    private static final String debugMode       = "Login";
    private static final String TARGET_URL      =  "http://proappschmidt.bplaced.net/setuser.php";
    private static final String PARAM_USERID    =  "kuerzel";
    private static final String PARAM_PWD       =  "pwd";
    private static final String PARAM_METHOD    =  "method";
    public static final  String PREFS_NAME      =  "MyConfigFile";

    EditText    username;
    EditText    password;
    Button      login;
    CheckBox    remeberme;

    String input_username;
    String input_pwd;

    String auth_userid;
    String auth_name;
    String auth_lastname;
    String auth_token;
    String auth_pwd;
    String auth_vertrag;

    JSONObject jsonRawResponse;
    JSONArray jsonResponse;

    boolean remember = false;

    Context context = Login.this;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        username    = (EditText)findViewById(R.id.et_username);
        password    = (EditText)findViewById(R.id.et_password);
        login       = (Button) findViewById(R.id.btn_login);
        remeberme   = (CheckBox)findViewById(R.id.cb_rememberme);

        //Holt sich die Infos aus dem File PREFS_NAME
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        input_username = settings.getString("token", "");
        input_pwd      = settings.getString("pwd", "");
        remember       = settings.getBoolean("remember", false);


        //Setzt die Namen in die Inputfelder.
        username.setText(input_username);
        password.setText(input_pwd);
        remeberme.setChecked(remember);

        //Der User moechte gerne direkt eingeloggt werden. 
        if (remember){
            new CheckUserFromDB().execute();
        }

        /**
         * Button auf der Loginseite.
         */
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Start DB Abfrage ob der Benutzer schon registriert ist.
                input_username = username.getText().toString();
                input_pwd = password.getText().toString();


                new CheckUserFromDB().execute();
            }
        });

        remeberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remeberme.isChecked()){
                    remember = true;
                }else{
                    remember = false;
                }
            }
        });

    }


    private String getQuery(Map map) {

        StringBuilder result = new StringBuilder();
        Iterator it = map.entrySet().iterator();
        boolean first = true;
        while (it.hasNext()) {

            if (first) {
                first = false;
            }else {
                result.append("&");
            }

            Map.Entry pair = (Map.Entry)it.next();
            Log.d(debugMode, pair.getKey().toString() + " = " + pair.getValue());
            result.append(pair.getKey());
            result.append("=");
            result.append(pair.getValue());
        }

        return result.toString();
    }

    public void makeToast(String msg, boolean status){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();


        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(status){
            if (remember){
                Log.d(debugMode, "Schreibt in den editor");
                    editor.putString("token", auth_token);
                    editor.putString("userid", auth_userid);
                    editor.putString("name", auth_name);
                    editor.putString("lastname", auth_lastname);
                    editor.putString("pwd", auth_pwd);
                    editor.putString("vertrag", auth_vertrag);

                Log.d(debugMode, "Schreibt  den input username : " + auth_token);
                Log.d(debugMode, "Liest den input username : " + auth_pwd);
            }

            editor.putBoolean("remember",remember);
            editor.commit();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    class CheckUserFromDB extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... args) {

            HttpURLConnection connection;
            OutputStreamWriter request = null;
            URL url = null;
            String response = null;
            String parameters = "";

            Map keyValue = new HashMap();
            keyValue.put(PARAM_USERID, input_username);
            keyValue.put(PARAM_PWD, input_pwd);
            keyValue.put(PARAM_METHOD, "loginuser");


            try
            {
                url = new URL(TARGET_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");

                request = new OutputStreamWriter(connection.getOutputStream());


                parameters = getQuery(keyValue);

                Log.d(debugMode, parameters);

                Log.d("website parameters : " , parameters);
                request.write(parameters);
                request.flush();
                request.close();
                String line = "";
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                // Response from server after login process will be stored in response variable.
                response = sb.toString();
                // You can perform UI operations here


                try {
                    jsonRawResponse = new JSONObject(response);
                }catch (JSONException e){
                    e.printStackTrace();
                }

                isr.close();
                reader.close();

            }
            catch(IOException e)
            {
                // Error
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONArray jsonRespons = jsonRawResponse.getJSONArray("result");

                if(jsonRespons.length() == 0){
                    makeToast("Login fehlgeschlagen. Ueberpruefen Sie die Eingabe.", false);
                }else{
                    auth_userid     = jsonRespons.getJSONObject(0).getString("userid");
                    auth_name       = jsonRespons.getJSONObject(0).getString("name");
                    auth_lastname   = jsonRespons.getJSONObject(0).getString("lastname");
                    auth_token      = jsonRespons.getJSONObject(0).getString("token");
                    auth_pwd        = jsonRespons.getJSONObject(0).getString("pwd");
                    auth_vertrag    = jsonRespons.getJSONObject(0).getString("vertrag");
                    makeToast("Login erfolgreich", true);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }


        }
    }

}
