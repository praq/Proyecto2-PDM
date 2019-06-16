package ues.edu.sv.petplanner;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {
DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);
        helper= new DBHelper(this);

        addSlide(AppIntroFragment.newInstance("BIENVENIDO A PETPLANNER!!!","UNA APLICACION MUY UTIL E INTUITIVA QUE TE PERMITIRA ADMINISTRAR EL CUIDADO DE TU MASCOTA.",R.drawable.intro1, Color.rgb(222,86,64)));
        addSlide(AppIntroFragment.newInstance("HAY MUCHO QUE OFRECER!!!","DENTRO DE LA APLICACION PODRAS REGISTRAR A TU MASCOTA, CONTROLAR SUS VACUNAS, CONTROLAR SUS MEDICAMENTOS, REALIZAR RUTINAS Y MAS...",R.drawable.intro4, Color.rgb(244,175,73)));
        addSlide(AppIntroFragment.newInstance("YA FALTA POCO...","VAMOS A CONFIGURAR UNOS PERMISOS",R.drawable.intro3, Color.rgb(67,170,218)));
    }

    @Override
    public  void onDonePressed(Fragment currentFragment){
        super.onDonePressed(currentFragment);
        helper.abrir();
        String mensaje= helper.actualizarIntroduccion();
        helper.cerrar();
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        Intent inte = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inte);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        helper.abrir();
        String mensaje= helper.actualizarIntroduccion();
        helper.cerrar();
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        Intent inte = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(inte);
    }
}
