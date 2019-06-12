package ues.edu.sv.petplanner;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RutinaActivity extends AppCompatActivity {

    Button btnIniciar;
    Button btnParar;
    Button btnReiniciar;
    Chronometer cronometro;
    Boolean correr = false;
    long detenerse;
    EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnParar = findViewById(R.id.btnParar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        cronometro = findViewById(R.id.cronometro);
        editFecha = findViewById(R.id.editFecha);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarCronometro();
            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararCronometro();
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarCronometro();
            }
        });

        editFecha.setText(getDate());
    }

    public void iniciarCronometro(){
        if(!correr){
            cronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            cronometro.start();
            correr = true;
        }
    }

    public void pararCronometro(){
        if(correr){
            cronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - cronometro.getBase();
            correr = false;
        }
    }

    public void reiniciarCronometro(){
        cronometro.setBase(SystemClock.elapsedRealtime());
        detenerse = 0;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
