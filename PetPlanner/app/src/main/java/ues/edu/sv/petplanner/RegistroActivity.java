package ues.edu.sv.petplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static java.lang.System.load;

public class RegistroActivity extends AppCompatActivity {
    EditText nombreUsu;
    EditText apellidoUsu;
    EditText edadUsu;
    EditText sexoUsu;
    EditText correoUsu;
    EditText contrasena;
    private ImageView imagenGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUsu = (EditText) findViewById(R.id.nombreUsuario);
        apellidoUsu = (EditText) findViewById(R.id.apellido);
        edadUsu = (EditText) findViewById(R.id.edad);
        sexoUsu = (EditText) findViewById(R.id.sexo);
        correoUsu = (EditText) findViewById(R.id.correo);
        contrasena =(EditText)findViewById(R.id.contraseña);

        imagenGif = (ImageView) findViewById(R.id.profile_image);
        String url = "https://media.giphy.com/media/LoNQv53ySBrTJFRel7/source.gif";
        Glide   .with(RegistroActivity.this)
                .load(url)
                .into(imagenGif);
    }

    public void registro(View v) {
        DBHelper helper = new DBHelper(this);
        if(nombreUsu.getText().toString().equals("")||apellidoUsu.getText().toString().equals("")||edadUsu.getText().toString().equals("")||
        sexoUsu.getText().toString().equals("")||correoUsu.getText().toString().equals("")||contrasena.getText().toString().equals(""))
        {Toast.makeText(this,"Complete todos los campos", Toast.LENGTH_SHORT).show();}
        else {
            String nombreUsua = nombreUsu.getText().toString();
            String apellidoUsua = apellidoUsu.getText().toString();
            String edadUsua = edadUsu.getText().toString();
            String sexoUsua = sexoUsu.getText().toString();
            String correoUsua = correoUsu.getText().toString();
            String contra = contrasena.getText().toString();
            String regInsertados;
            helper.abrir();
            regInsertados = helper.RegistroUsuario(nombreUsua, apellidoUsua,
                    Integer.valueOf(edadUsua), sexoUsua, correoUsua, contra);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }
    }




}
