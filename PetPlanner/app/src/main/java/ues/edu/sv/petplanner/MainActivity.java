package ues.edu.sv.petplanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper= new DBHelper(this);



        helper.abrir();
        if(helper.consultarIntro()==0){
            Intent inte= new Intent(this, AppIntroActivity.class);
            startActivity(inte);
        }
        helper.cerrar();
    }

    public void registro(View v) {
        Intent ints = new Intent(this, RegistroActivity.class);
        startActivity(ints);
    }

    public void iniciarSesion(View v) {
        Intent ints = new Intent(this, IniciarSesionActivity.class);
        startActivity(ints);
    }


}
