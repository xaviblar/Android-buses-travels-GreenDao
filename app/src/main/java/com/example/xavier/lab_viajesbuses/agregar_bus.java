package com.example.xavier.lab_viajesbuses;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xavier.lab_viajesbuses.dao.buses;

import java.util.Arrays;
import java.util.List;

public class agregar_bus extends Activity {
    EditText ET_matricula;
    EditText ET_marca;
    EditText ET_modelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bus);

        ET_matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_marca = (EditText) findViewById(R.id.ET_marca);
        ET_modelo = (EditText) findViewById(R.id.ET_modelo);
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
            buses busID = DaoAPP.getBusDao().load(ET_matricula.getText().toString());
            if(busID != null) {
                Toast.makeText(getApplicationContext(), "Ya existe un bus con ese numero de matricula", Toast.LENGTH_LONG).show();
            }
            else {
                if(ET_matricula.getText().toString().equals("all")){
                    Toast.makeText(getApplicationContext(), "Matricula no permitida", Toast.LENGTH_LONG).show();
                }
                else{
                    buses bus = new buses();
                    bus.setMatricula(ET_matricula.getText().toString());
                    bus.setMarca(ET_marca.getText().toString());
                    bus.setModelo(ET_modelo.getText().toString());
                    DaoAPP.getBusDao().insert(bus);
                    Toast.makeText(getApplicationContext(), "Bus guardado exitosamente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
        else if(id == R.id.cancelar)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
