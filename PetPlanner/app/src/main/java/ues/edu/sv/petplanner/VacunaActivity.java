package ues.edu.sv.petplanner;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class VacunaActivity extends AppCompatActivity {
    EditText nombreVacuna;
    Spinner spinnerPerro;
    EditText fecha;
    ListView listVacunas;
    Button btnFecha;
    ScrollView scroll;
    Perro perro;
    Registro registro;
    String usuario;
    private ImageView imagenGif;
    DBHelper helper;

    int dia =0;
    int mes =0;
    int anio =0;

    Calendar calendar;
    DatePickerDialog dataPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacuna);
        helper = new DBHelper(this);
        nombreVacuna = (EditText) findViewById(R.id.editNombre);
        spinnerPerro = (Spinner) findViewById(R.id.spinnerPerr);
        fecha =(EditText)findViewById(R.id.editFecha);
        listVacunas=(ListView)findViewById(R.id.lstVacunas);
        scroll=(ScrollView)findViewById(R.id.scroll);
        btnFecha= (Button)findViewById(R.id.btnFecha);

        imagenGif = (ImageView) findViewById(R.id.imagenVacuna);
        String url = "https://media.giphy.com/media/gT2fj8ioFAv87cA5RO/source.gif";
        Glide.with(VacunaActivity.this)
                .load(url)
                .into(imagenGif);


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dataPicker = new DatePickerDialog(VacunaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        fecha.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                    }
                },day, month, year);

                dataPicker.show();
            }
        });

        Bundle bundle = getIntent().getExtras();

        dia= bundle.getInt("dia");
        mes= bundle.getInt("mes");
        anio= bundle.getInt("anio");
        usuario = bundle.getString("nombreusuario");

        fecha.setText(dia+"/"+mes+"/"+anio);

        //CARGANDO DATOS A LA LISTA VACUNAS
        helper.consultarListaVacunas();
        ArrayAdapter<CharSequence> adapterLista=new ArrayAdapter(this,android.R.layout.simple_list_item_1, helper.listaVacunas);
        listVacunas.setAdapter(adapterLista);

        //CARGANDO DATOS EN EL SPINNER
        helper.consultarListaPerro();
        ArrayAdapter<CharSequence> adapterSpinner=new ArrayAdapter(this,android.R.layout.simple_spinner_item, helper.listaPerro);
        spinnerPerro.setAdapter(adapterSpinner);
        spinnerPerro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                perro= new Perro();
                perro=helper.perroLista.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //HABILITANDO SCROLL A LA LISTA
        scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.lstVacunas).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        listVacunas.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }


    public void registroVacuna(View v) {
        if (nombreVacuna.getText().toString().equals("")||fecha.getText().toString().equals(""))
        {Toast.makeText(this,"Complete todos los campos", Toast.LENGTH_SHORT).show();}
        else {
            String nombre = nombreVacuna.getText().toString();
            String perroo = perro.getNombrePerro();
            String fechas = fecha.getText().toString();


            helper.abrir();
            registro = helper.consultarRegistro(usuario, perroo);
            helper.cerrar();


            Vacuna vacuna = new Vacuna();
            vacuna.setNombreVacuna(nombre);
            vacuna.setCodRegistro(registro.getCodRegistro());
            vacuna.setFecha(fechas);

            String regInsertados;
            helper.abrir();
            regInsertados = helper.InsertarVacuna(vacuna);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }
    }
}
