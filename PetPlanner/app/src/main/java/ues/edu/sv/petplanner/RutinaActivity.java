package ues.edu.sv.petplanner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hdodenhof.circleimageview.CircleImageView;


public class RutinaActivity extends AppCompatActivity {
    Button btnIniciar;
    Button btnParar;
    Button btnReiniciar;
    Chronometer cronometro;
    Boolean correr = false;
    long detenerse;
    EditText editFecha;
    EditText editNombreMascota;
    ListView listRutinas;
    ScrollView scroll;
    //para btnFB
    private EditText usuario;
    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private CallbackManager callbackManager;

    ShareDialog shareDialog;

    //para audio
    MediaPlayer Media;
    Button play;
    Button stop;

    //para correo electronico
    Session session = null;
    //correo de donde se enviará el mensaje
    String correo = "elixa.mendex98@gmail.com";
    String contraseña = "eli12345";
    Usuario usua = null;
    String usuarioRegistrado;
    String nombrePerro;
    Bundle bundle;

    private ImageView imagenGif;
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
        editNombreMascota = findViewById(R.id.editNombreMascota);
        listRutinas = findViewById(R.id.lstRutinas);

        //CARGANDO DATOS A LA LISTA DE RUTINAS
        helper.consultarListaRutinas();
        ArrayAdapter<CharSequence> adapterLista=new ArrayAdapter(this,android.R.layout.simple_list_item_1, helper.listaRutina);
        listRutinas.setAdapter(adapterLista);

        //HABILITANDO SCROLL A LA LISTA
        /*scroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.lstRutinas).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        listRutinas.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/


        play=(Button) findViewById(R.id.play);
        stop=(Button) findViewById(R.id.stop);
        play.setOnClickListener(onClick);
        stop.setOnClickListener(onClick);
        Media= MediaPlayer.create(getApplicationContext(), R.raw.music);

        imagenGif = (ImageView) findViewById(R.id.imageRutina);
        String url = "https://media.giphy.com/media/51W7lOzH4007niylO3/source.gif";
        Glide   .with(RutinaActivity.this)
                .load(url)
                .into(imagenGif);

        //FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        callbackManager = CallbackManager.Factory.create();

        //Capturar el usuario que se ha registrado
        bundle = getIntent().getExtras();
        usuarioRegistrado = bundle.getString("nombreusuario");

        //para saber a qué perro le corresponde la rutina
        bundle = getIntent().getExtras();
        nombrePerro = bundle.getString("nombreperro");
        editNombreMascota.setText(nombrePerro);


        /*loginButton = findViewById(R.id.login_button);
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
        });*/

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
    //CODIGO PARA BOTON DE SHARE EN FB
    public void publicar(View view){
        try{
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("Prueba Facebook")
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .build();
                shareDialog.show(linkContent);
            }
        }catch (Exception e){
            Toast.makeText(RutinaActivity.this,"algo salio mal !"+ e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    //CODIGO DE BOTONES DE AUDIO
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        // TODO Auto-generated method stub
            if (v.getId()==R.id.play){
                if (Media.isPlaying()){
                    Media.pause();
                    play.setText("Play");
                }
                else{
                    Media.start();
                    play.setText("Pause");
                }
            }
            else{
                Media.stop();
                play.setText("Play");
                try{
                    Media.prepare();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    };

    //CODIGO BOTON DE FB
    /*@Override
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
    }*/

    //CODIGO DEL CRONOMETRO
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
        Registro registro = new Registro();
        nombrePerro = editNombreMascota.getText().toString();
        registro = helper.consultarCodRegistro(nombrePerro);
        int codRegistro = registro.getCodRegistro();
        rutina.setCodigoRegistro(codRegistro);
        helper.cerrar();
        //rutina.setCodigoRegistro(1); //Hacer consulta

        rutina.setFechaRutina(editFecha.getText().toString());
        rutina.setDuracionRutina(cronometro.getText().toString());
        helper.abrir();
        regInsertados = helper.RegistroRutina(rutina);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void enviarCorreo(View v) {
        helper.abrir();
        usua = helper.consultarUsuarioRegistrado(usuarioRegistrado);
        helper.cerrar();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try {
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo, contraseña);
                }
            });
            if (session != null) {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Datos de rutina");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usua.getCorreo()));
                message.setContent("Duracion de rutina: "+cronometro.getText().toString(), "text/html; charset=utf-8");
                Transport.send(message);
                Toast.makeText(RutinaActivity.this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RutinaActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
