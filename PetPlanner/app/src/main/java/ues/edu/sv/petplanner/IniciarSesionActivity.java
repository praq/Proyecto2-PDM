package ues.edu.sv.petplanner;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

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
        helper.cerrar();
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
