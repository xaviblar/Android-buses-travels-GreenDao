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

import com.example.xavier.lab_viajesbuses.dao.buses;
import com.example.xavier.lab_viajesbuses.dao.viajesDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    ListView LV_buses;
    List<buses> dataArrayList;
    ArrayList<String> stringsArrayList;
    ArrayAdapter arrayAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LV_buses = (ListView) findViewById(R.id.LVListaBuses);
        dataArrayList = DaoAPP.getBusDao().loadAll();
        stringsArrayList = new ArrayList<String>();
        LV_buses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), parent);
                popupMenu.inflate(R.menu.popumenu_buses);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editar:
                                Intent i = new Intent(getApplicationContext(), editar_bus.class);
                                Bundle bag = new Bundle();
                                bag.putString("matricula", dataArrayList.get(position).getMatricula());
                                i.putExtras(bag);
                                startActivity(i);
                                break;
                            case R.id.eliminar:
                                confirmar_eliminar_Bus(dataArrayList.get(position).getMatricula());
                                break;
                            case R.id.ver_viajes:
                                Intent i2 = new Intent(getApplicationContext(), viajes_activity.class);
                                Bundle bag2 = new Bundle();
                                bag2.putString("matricula",dataArrayList.get(position).getMatricula());
                                i2.putExtras(bag2);
                                startActivity(i2);
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.agregar_bus) {
            Intent intent = new Intent(getApplicationContext(), agregar_bus.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.ver_todos_viajes){
            Intent i = new Intent(getApplicationContext(), viajes_activity.class);
            Bundle bag = new Bundle();
            bag.putString("matricula","all");
            i.putExtras(bag);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        dataArrayList = DaoAPP.getBusDao().loadAll();
        stringsArrayList.clear();
        for(buses bus : dataArrayList){
            stringsArrayList.add("Matricula: " +  bus.getMatricula() + " Marca: " + bus.getMarca() + " Modelo: " + bus.getModelo());
        }
        arrayAdap =  new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringsArrayList);
        LV_buses.setAdapter(arrayAdap);
        super.onResume();
    }

    public void confirmar_eliminar_Bus(final String matricula){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Alerta");
        dialogo1.setMessage("Se eliminara el bus y todos sus viajes!");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                DaoAPP.getBusDao().deleteByKey(matricula);
                DaoAPP.getViajeDao().queryBuilder().where(viajesDao.Properties.Matricula_bus.eq(matricula)).buildDelete().executeDeleteWithoutDetachingEntities();
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
