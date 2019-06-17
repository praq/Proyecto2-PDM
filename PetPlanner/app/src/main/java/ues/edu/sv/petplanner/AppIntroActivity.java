package ues.edu.sv.petplanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    final int MY_PERMISSIONS_REQUEST=124;
    int contador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);
        helper= new DBHelper(this);

        addSlide(AppIntroFragment.newInstance("BIENVENIDO A PETPLANNER!!!","UNA APLICACION MUY UTIL E INTUITIVA QUE TE PERMITIRA ADMINISTRAR EL CUIDADO DE TU MASCOTA.",R.drawable.intro1, Color.rgb(222,86,64)));
        addSlide(AppIntroFragment.newInstance("HAY MUCHO QUE OFRECER!!!","DENTRO DE LA APLICACION PODRAS REGISTRAR A TU MASCOTA, CONTROLAR SUS VACUNAS, CONTROLAR SUS MEDICAMENTOS, REALIZAR RUTINAS Y MAS...",R.drawable.intro4, Color.rgb(244,175,73)));

        addSlide(AppIntroFragment.newInstance("YA FALTA POCO...","VAMOS A CONFIGURAR UNOS PERMISOS",R.drawable.intro3, Color.rgb(67,170,218)));
        askForPermissions(new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, 1);


        showStatusBar(false);
        setFlowAnimation();
        //setBarColor(Color.parseColor("#333639"));
        //setSeparatorColor(Color.parseColor("#2196F3"));
    }

    @Override
    public void onSlideChanged(Fragment oldFragment, Fragment newFragment){
        contador++;
        if(contador>2){
            pedirPermisos();
        }
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

    public void pedirPermisos(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(),"permission was granted, yay!",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),"permission denied, boo!",Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
