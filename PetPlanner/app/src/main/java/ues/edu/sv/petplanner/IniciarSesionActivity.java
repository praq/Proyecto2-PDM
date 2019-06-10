package ues.edu.sv.petplanner;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IniciarSesionActivity extends AppCompatActivity {

    EditText usuario;
    EditText contrasena;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        usuario= (EditText) findViewById(R.id.nombreUsuario);
        contrasena = (EditText) findViewById(R.id.contraseña);
        helper = new DBHelper(this);
        helper.abrir();
        String mensaje = helper.llenarBD();
        helper.cerrar();
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void iniciarSesion(View v) {
        String login = usuario.getText().toString();
        String passw = contrasena.getText().toString();
        helper = new DBHelper(this);
        int consulta = helper.consultarUsuario(login, passw);
        if (consulta == 1) {
            Intent intens = new Intent(this,PerfilActivity.class);
            intens.putExtra("nombreusuario", login);
            startActivity(intens);
        } else {
            Toast.makeText(this,"Campos incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
}
