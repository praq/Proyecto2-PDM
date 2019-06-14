package ues.edu.sv.petplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class VerEventosActivity extends AppCompatActivity {

    ListView lstMedicamentos;
    ListView lstVacunas;
    ScrollView scroll;
    TextView txtFecha;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eventos);
        helper = new DBHelper(this);

        lstMedicamentos = (ListView)findViewById(R.id.lstMedi);
        lstVacunas = (ListView)findViewById(R.id.lstVacunas);
        txtFecha = (TextView)findViewById(R.id.txtFecha);
        scroll= (ScrollView)findViewById(R.id.scroll);


        Bundle bundle = getIntent().getExtras();
        int dia,mes,anio;
        dia= bundle.getInt("dia");
        mes= bundle.getInt("mes");
        anio= bundle.getInt("anio");
        String f = dia+"/"+mes+"/"+anio;

        txtFecha.setText(f);

        helper.consultarListaEventoMedicamento(f);
        ArrayAdapter<CharSequence> adapterLista=new ArrayAdapter(this,android.R.layout.simple_list_item_1, helper.listaMedicamento);
        lstMedicamentos.setAdapter(adapterLista);

        helper.consultarListaEventoVacuna(f);
        ArrayAdapter<CharSequence> adapterLista1=new ArrayAdapter(this,android.R.layout.simple_list_item_1, helper.listaVacunas);
        lstVacunas.setAdapter(adapterLista1);


        //HABILITANDO SCROLL A LA LISTA
        scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.lstMedi).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        lstMedicamentos.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //HABILITANDO SCROLL A LA LISTA
        scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.lstVacunas).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        lstVacunas.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
