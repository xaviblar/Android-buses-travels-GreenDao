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
import com.example.xavier.lab_viajesbuses.dao.viajes;

import java.util.ArrayList;
import java.util.List;

public class editar_viaje extends Activity {
    EditText ET_destino;
    EditText ET_cantidad_pasajeros;
    Spinner SPN_matricula;
    List<buses> arrayListData;
    ArrayList<String> stringArrayList;
    ArrayAdapter arrayAdap;
    String Id_viaje;
    viajes viaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_viaje);
        ET_destino = (EditText) findViewById(R.id.ET_destino_viaje);
        ET_cantidad_pasajeros = (EditText) findViewById(R.id.ET_pasajeros_viaje);
        Bundle bagR = getIntent().getExtras();
        Id_viaje = bagR.getString("id");
        viaje = DaoAPP.getViajeDao().load(Long.parseLong(Id_viaje));
        ET_destino.setText(viaje.getDestino());
        ET_cantidad_pasajeros.setText(viaje.getPasajeros());
        SPN_matricula = (Spinner) findViewById(R.id.SPN_matricula_viaje);
        arrayListData = DaoAPP.getBusDao().loadAll();
        stringArrayList = new ArrayList<String>();
        for(buses bus : arrayListData)
        {
            stringArrayList.add(bus.getMatricula());
        }
        arrayAdap =  new ArrayAdapter(this, android.R.layout.simple_spinner_item, stringArrayList);
        SPN_matricula.setAdapter(arrayAdap);
        SPN_matricula.setSelection(getIndex(SPN_matricula, Id_viaje));
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
            viaje.setPasajeros(ET_cantidad_pasajeros.getText().toString());
            viaje.setDestino(ET_destino.getText().toString());
            viaje.setMatricula_bus(SPN_matricula.getSelectedItem().toString());
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
