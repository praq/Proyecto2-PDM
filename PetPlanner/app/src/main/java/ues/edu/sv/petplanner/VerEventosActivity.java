package ues.edu.sv.petplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VerEventosActivity extends AppCompatActivity {

    ListView lstMedicamentos;
    ListView lstVacunas;
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
    }
}
