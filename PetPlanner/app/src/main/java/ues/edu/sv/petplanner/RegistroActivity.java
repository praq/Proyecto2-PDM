package ues.edu.sv.petplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    EditText nombreUsu;
    EditText apellidoUsu;
    EditText edadUsu;
    EditText sexoUsu;
    EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUsu = (EditText) findViewById(R.id.nombreUsuario);
        apellidoUsu = (EditText) findViewById(R.id.apellido);
        edadUsu = (EditText) findViewById(R.id.edad);
        sexoUsu = (EditText) findViewById(R.id.sexo);
        contrasena =(EditText)findViewById(R.id.contraseña);
    }

    public void registro(View v) {
        DBHelper helper = new DBHelper(this);
        String nombreUsua=nombreUsu.getText().toString();
        String apellidoUsua=apellidoUsu.getText().toString();
        String edadUsua=edadUsu.getText().toString();
        String sexoUsua=sexoUsu.getText().toString();
        String contra=contrasena.getText().toString();
        String regInsertados;
        helper.abrir();
        regInsertados=helper.RegistroUsuario( nombreUsua, apellidoUsua, Integer.valueOf(edadUsua), sexoUsua,
                contra);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }




}
