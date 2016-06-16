package com.example.xavier.lab_viajesbuses;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.xavier.lab_viajesbuses.dao.busesDao;
import com.example.xavier.lab_viajesbuses.dao.viajes;
import com.example.xavier.lab_viajesbuses.dao.viajesDao;

import java.util.ArrayList;
import java.util.List;

public class viajes_activity extends Activity {
    ListView LV_viajes;
    List<viajes> dataArrayList;
    ArrayList<String> stringsArrayList;
    ArrayAdapter arrayAdap;
    String matricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes);
        LV_viajes = (ListView) findViewById(R.id.LV_viajes);
        Bundle bagR = getIntent().getExtras();
        matricula = bagR.getString("matricula");
        if(matricula.equals("all"))
        {
            dataArrayList = DaoAPP.getViajeDao().loadAll();
        }
        else
        {
            dataArrayList = DaoAPP.getViajeDao().queryBuilder().where(viajesDao.Properties.Matricula_bus.eq(matricula))
                                                               .list();
        }
        stringsArrayList = new ArrayList<String>();
        for(viajes viaje : dataArrayList){
            stringsArrayList.add("Matricula Bus: " + viaje.getMatricula_bus() + " Destino: " + viaje.getDestino() + " Pasajeros: " + viaje.getPasajeros());
        }
        arrayAdap =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringsArrayList);
        LV_viajes.setAdapter(arrayAdap);
        LV_viajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), parent);
                popupMenu.inflate(R.menu.popupmenu_viajes);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar_viaje:
                                Intent i = new Intent(getApplicationContext(), editar_viaje.class);
                                Bundle bag = new Bundle();
                                bag.putString("id", dataArrayList.get(position).getId().toString());
                                i.putExtras(bag);
                                startActivity(i);
                                break;
                            case R.id.eliminar_viaje:
                                confirmar_eliminar_Viaje(dataArrayList.get(position).getId().toString());
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_viajes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agregar_viaje) {
            Intent intent = new Intent(getApplicationContext(), agregar_viaje.class);
            Bundle bag = new Bundle();
            bag.putString("matricula",matricula);
            intent.putExtras(bag);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.ver_buses){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        if(matricula.equals("all"))
        {
            dataArrayList = DaoAPP.getViajeDao().loadAll();
        }
        else
        {
            dataArrayList = DaoAPP.getViajeDao().queryBuilder().where(viajesDao.Properties.Matricula_bus.eq(matricula))
                    .list();
        }
        stringsArrayList.clear();
        for(viajes viaje : dataArrayList){
            stringsArrayList.add("Matricula Bus: " + viaje.getMatricula_bus() + " Destino: " + viaje.getDestino() + " Pasajeros: " + viaje.getPasajeros());
        }
        arrayAdap =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringsArrayList);
        LV_viajes.setAdapter(arrayAdap);
        super.onResume();
    }

    public void confirmar_eliminar_Viaje(final String id_viaje){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Alerta");
        dialogo1.setMessage("Se eliminara el viaje!");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                DaoAPP.getViajeDao().deleteByKey(Long.parseLong(id_viaje));
                onResume();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }
}
