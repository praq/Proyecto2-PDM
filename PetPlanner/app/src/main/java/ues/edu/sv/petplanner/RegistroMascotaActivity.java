package ues.edu.sv.petplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroMascotaActivity extends AppCompatActivity {

    EditText nombrePerro;
    EditText edadPerro;
    EditText colorPerro;
    EditText pesoPerro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mascota);
        nombrePerro = (EditText) findViewById(R.id.nombrePerro);
        edadPerro = (EditText) findViewById(R.id.edadPerro);
        colorPerro = (EditText) findViewById(R.id.color);
        pesoPerro =(EditText)findViewById(R.id.peso);
    }

    public void registro(View v) {
        DBHelper helper = new DBHelper(this);
        String nombrePerr=nombrePerro.getText().toString();
        String edadPerr=edadPerro.getText().toString();
        String colorPerr=colorPerro.getText().toString();
        String pesoPerr=pesoPerro.getText().toString();
        String regInsertados;
        helper.abrir();
        regInsertados=helper.RegistroMascota( nombrePerr,Integer.valueOf(edadPerr),colorPerr,Float.valueOf(pesoPerr));
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
}
