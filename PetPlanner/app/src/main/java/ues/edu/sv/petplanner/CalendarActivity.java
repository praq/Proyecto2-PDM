package ues.edu.sv.petplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private CalendarView calendar;
    DBHelper DBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBhelper = new DBHelper(this);

        calendar=(CalendarView)findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(this);

    }


    //Metodo al seleccionar un dia del calendario
    @Override
    public void onSelectedDayChange(CalendarView view, final int year, final int month, final int day) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0] = "Agregar Medicamento";
        items[1]= "Agregar Vacuna";
        items[2]= "Ver Eventos";
        items[3]= "Cancelar";

        final int dia,mes,anio;
        dia=day;
        mes=month+1;
        anio=year;


        builder.setTitle("Seleccione Una Opcion").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    //Pasando los datos de dia,mes,anio a la vista
                    Intent intent = new Intent(getApplication(), MedicamentoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dia",dia);
                    bundle.putInt("mes",mes);
                    bundle.putInt("anio",anio);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(which==1){
                    //Pasando los datos de dia,mes,anio a la vista
                    Intent intent = new Intent(getApplication(), VacunaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dia",dia);
                    bundle.putInt("mes",mes);
                    bundle.putInt("anio",anio);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(which==2){
                    //Pasando los datos de dia,mes,anio a la vista
                    Intent intent = new Intent(getApplication(), VerEventosActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("dia",dia);
                    bundle.putInt("mes",mes);
                    bundle.putInt("anio",anio);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
