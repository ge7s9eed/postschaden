package com.example.stuxnet.schmidt_schadensprotokoll;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Created by stuxnet on 16.10.16.
 */

public class Schadenaufnahme extends AppCompatActivity {

    private static final String debugMode = "Schadenaufnahme";
    Spinner carnum;
    Spinner location_out;
    Spinner location_in;
    EditText description;
    ImageButton addimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schadenaufnahme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSchaden);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        carnum      = (Spinner)findViewById(R.id.sp_carnum);
        location_out    = (Spinner)findViewById(R.id.sp_locationout);
        location_in    = (Spinner)findViewById(R.id.sp_locationin);
        description = (EditText)findViewById(R.id.et_descriptionSchaden);
        addimage    = (ImageButton)findViewById(R.id.btn_addimage);

        //Floating Button ist zwar initialisiert aber wird ausgeblendet.
        FloatingActionButton fab_schadenaufnahme = (FloatingActionButton) findViewById(R.id.fab_schadenauf);
        fab_schadenaufnahme.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter_carnum = ArrayAdapter.createFromResource(
                this, R.array.carnum_prompt, R.xml.spinner_item);
        adapter_carnum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carnum.setAdapter(adapter_carnum);

        ArrayAdapter<CharSequence> adapter_schadenout = ArrayAdapter.createFromResource(
                this, R.array.locationout_prompt, R.xml.spinner_item);
        adapter_schadenout.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_out.setAdapter(adapter_schadenout);

        ArrayAdapter<CharSequence> adapter_schadenin = ArrayAdapter.createFromResource(
                this, R.array.locationin_prompt, R.xml.spinner_item);
        adapter_schadenin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_in.setAdapter(adapter_schadenin);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(debugMode, "Öffne die Kamera um ein bild zu machen oer aus der Gallery auszuwählen. ");
            }
        });

    }
}
