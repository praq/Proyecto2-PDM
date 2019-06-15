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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MedicamentoActivity extends AppCompatActivity {

    EditText nombreMedi;
    Spinner spinnerEnfermedad;
    Spinner spinnerPerro;
    EditText descripcionM;
    EditText dosis;
    EditText fecha;
    ListView listMedicamentos;
    Button btnFecha;
    ScrollView scroll;
    Enfermedad enfermedad;
    DBHelper helper;

    Perro perro;
    Registro registro;
    String usuario;

    int dia =0;
    int mes =0;
    int anio =0;

    Calendar calendar;
    DatePickerDialog dataPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        helper = new DBHelper(this);
        nombreMedi = (EditText) findViewById(R.id.editNombre);
        spinnerEnfermedad = (Spinner) findViewById(R.id.spinnerEnfermedad);
        spinnerPerro = (Spinner) findViewById(R.id.spinnerPerr);
        descripcionM =(EditText)findViewById(R.id.editDescriMedi);
        dosis=(EditText) findViewById(R.id.editDosis);
        fecha =(EditText)findViewById(R.id.editFecha);
        listMedicamentos=(ListView)findViewById(R.id.lstMedicamentos);
        scroll=(ScrollView)findViewById(R.id.scroll);
        btnFecha= (Button)findViewById(R.id.btnFecha);


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dataPicker = new DatePickerDialog(MedicamentoActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        //CARGANDO DATOS A LA LISTA MEDICMENTOS
        helper.consultarListaMedicamentos();
        ArrayAdapter<CharSequence> adapterLista=new ArrayAdapter(this,android.R.layout.simple_list_item_1, helper.listaMedicamento);
        listMedicamentos.setAdapter(adapterLista);

        //CARGANDO DATOS EN EL SPINNER
        helper.consultarListaEnfermedades();
        ArrayAdapter<CharSequence> adapterSpinner=new ArrayAdapter(this,android.R.layout.simple_spinner_item, helper.listaEnfermedad);
        spinnerEnfermedad.setAdapter(adapterSpinner);
        spinnerEnfermedad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enfermedad= new Enfermedad();
                enfermedad=helper.enfermedadLista.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CARGANDO DATOS EN EL SPINNER
        helper.consultarListaPerro();
        ArrayAdapter<CharSequence> adapterSpinner1=new ArrayAdapter(this,android.R.layout.simple_spinner_item, helper.listaPerro);
        spinnerPerro.setAdapter(adapterSpinner1);
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
                findViewById(R.id.lstMedicamentos).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        listMedicamentos.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }


    public void registroMedicamento(View v) {
        if (nombreMedi.getText().toString().equals("")||descripcionM.getText().toString().equals("")||
        dosis.getText().toString().equals("")||fecha.getText().toString().equals(""))
        {Toast.makeText(this,"Complete todos los campos", Toast.LENGTH_SHORT).show();}
        else {
            String nombre = nombreMedi.getText().toString();
            String enfe = enfermedad.getNombreEnfermedad();
            String perroo = perro.getNombrePerro();
            String descriM = descripcionM.getText().toString();
            float dosi = Float.parseFloat(dosis.getText().toString());
            String fechas = fecha.getText().toString();

            helper.abrir();
            registro = helper.consultarRegistro(usuario, perroo);
            helper.cerrar();

            Medicamento medicamento = new Medicamento();
            medicamento.setNombreMedicamento(nombre);
            medicamento.setCodRegistro(registro.getCodRegistro());
            medicamento.setNombreEnfermedad(enfe);
            medicamento.setDescripcionMedicamento(descriM);
            medicamento.setDosis(dosi);
            medicamento.setFecha(fechas);

            String regInsertados;
            helper.abrir();
            regInsertados = helper.InsertarMedicamento(medicamento);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }
    }
}

