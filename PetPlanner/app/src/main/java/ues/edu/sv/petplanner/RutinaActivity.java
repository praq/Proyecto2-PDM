package ues.edu.sv.petplanner;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RutinaActivity extends AppCompatActivity {

    Button btnIniciar;
    Button btnParar;
    Button btnReiniciar;
    Chronometer cronometro;
    Boolean correr = false;
    long detenerse;
    EditText editFecha;
    private EditText usuario;
    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private CallbackManager callbackManager;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);
        helper = new DBHelper(this);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnParar = findViewById(R.id.btnParar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        cronometro = findViewById(R.id.cronometro);
        editFecha = findViewById(R.id.editFecha);
        loginButton = findViewById(R.id.login_button);
        circleImageView = findViewById(R.id.profile_image);
        usuario = findViewById(R.id.nombreUsuario);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

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

    //CODIGO BOTON DE FB

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null)
            {
                usuario.setText("");
                circleImageView.setImageResource(0);
                Toast.makeText(RutinaActivity.this, "User logged out",Toast.LENGTH_LONG).show();
            }else {
                loadUserProfile(currentAccessToken);
            }

        }
    };

    private void loadUserProfile (AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";

                    usuario.setText(first_name +" "+ last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(RutinaActivity.this).load(image_url).into(circleImageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
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

    public void insertarRutina(View v){
        String regInsertados;
        Rutina rutina = new Rutina();
        helper.abrir();
        rutina.setCodigoRutina("RUT"+(helper.cantidadRutina()+1)); //Hacer consulta
        helper.cerrar();
        rutina.setCodigoRegistro(1); //Hacer consulta
        rutina.setFechaRutina(editFecha.getText().toString());
        rutina.setDuracionRutina(cronometro.getText().toString());
        helper.abrir();
        regInsertados = helper.RegistroRutina(rutina);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public  void enviarCorreo(View v) {
        Intent ints = new Intent(this, EnviarCorreoActivity.class);
        startActivity(ints);
    }
}
