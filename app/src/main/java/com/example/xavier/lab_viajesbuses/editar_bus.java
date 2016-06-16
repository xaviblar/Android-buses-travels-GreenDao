package com.example.xavier.lab_viajesbuses;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xavier.lab_viajesbuses.dao.buses;

import java.util.ArrayList;
import java.util.List;

public class editar_bus extends Activity {
    Spinner SPN_matriculas;
    EditText ET_matricula;
    EditText ET_marca;
    EditText ET_modelo;
    buses bus;
    List<buses> dataArrayList;
    ArrayList<String> spinnerArrayList;
    ArrayAdapter arrayAdap;
    String matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_bus);

        Bundle bagR = getIntent().getExtras();
        matricula = bagR.getString("matricula");
        dataArrayList = DaoAPP.getBusDao().loadAll();
        spinnerArrayList = new ArrayList<String>();
        for(buses bus : dataArrayList){
            spinnerArrayList.add(bus.getMatricula());
        }
        arrayAdap =  new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        SPN_matriculas = (Spinner) findViewById(R.id.SPN_matricula_editar_bus);
        SPN_matriculas.setAdapter(arrayAdap);
        SPN_matriculas.setSelection(getIndex(SPN_matriculas, matricula));
        bus = DaoAPP.getBusDao().load(matricula);
        ET_matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_matricula.setKeyListener(null);
        ET_matricula.setText(bus.getMatricula());
        ET_marca = (EditText) findViewById(R.id.ET_marca);
        ET_marca.setText(bus.getMarca());
        ET_modelo = (EditText) findViewById(R.id.ET_modelo);
        ET_modelo.setText(bus.getModelo());
        SPN_matriculas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus = DaoAPP.getBusDao().load(parent.getItemAtPosition(position).toString());
                ET_matricula.setText(bus.getMatricula());
                ET_marca.setText(bus.getMarca());
                ET_modelo.setText(bus.getModelo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.guardar) {
            bus.setModelo(ET_modelo.getText().toString());
            bus.setMarca(ET_marca.getText().toString());
            DaoAPP.getBusDao().update(bus);
            finish();
        }
        else if(id == R.id.cancelar)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
