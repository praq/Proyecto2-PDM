package ues.edu.sv.petplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuRegistroActivity extends AppCompatActivity {
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_registro);
        helper = new DBHelper(this);
        helper.abrir();
        String mensaje = helper.llenarBD();
        helper.cerrar();
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public  void registroUsuario(View v)
    {
        Intent ints = new Intent(this,RegistroActivity.class);
        startActivity(ints);
    }
    public  void registroMascota(View v)
    {
        Intent ints = new Intent(this,RegistroMascotaActivity.class);
        startActivity(ints);
    }
}
