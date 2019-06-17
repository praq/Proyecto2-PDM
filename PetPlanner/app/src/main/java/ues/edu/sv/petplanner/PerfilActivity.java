package ues.edu.sv.petplanner;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {

    private String APP_DIRECTORY = "PicturesPetP/";  //Direccion de la app donde se guardara la imagen
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";  //nombre temporal
    private final int PHOTO_CODE = 100;     //para usar metodo OnActivityResult
    private final int SELECT_PICTURE = 200;
    private ImageView imageView;
    private EditText nombrePerrolbl;
    private EditText razaPerrolbl;
    private EditText edadPerrolbl;
    private EditText colorPerrolbl;
    private EditText pesoPerrolbl;
    private Button button;
    Spinner spinnerMascota;
    ArrayList<Perro> perros;
    String usuario;
    DBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        helper = new DBHelper(this );
        imageView = (ImageView) findViewById(R.id.imagen);
        button = (Button) findViewById(R.id.buttonImage);
        spinnerMascota = (Spinner) findViewById(R.id.mascota);
        nombrePerrolbl = (EditText) findViewById(R.id.nombrePerro);
        razaPerrolbl = (EditText)findViewById(R.id.raza);
        edadPerrolbl = (EditText) findViewById(R.id.edadPerro);
        colorPerrolbl = (EditText) findViewById(R.id.color);
        pesoPerrolbl = (EditText) findViewById(R.id.peso);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("nombreusuario");

        //LLENAR SPINNER DE MASCOTA
        perros = helper.obtenerListaPerros(usuario);
        final ArrayList<String> nombrePerro = new ArrayList<String>();
        nombrePerro.add("seleccione");
        for (int i = 0; i < perros.size(); i++) {
            Log.e("myTag", "1 ----- ");
            nombrePerro.add(perros.get(i).getNombrePerro());
            Log.e("myTag", "2 ----- ");

        }
        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,nombrePerro);
        spinnerMascota.setAdapter(adapter1);

        spinnerMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String mascota=spinnerMascota.getSelectedItem().toString();
                Log.e("Selected item : ",mascota);
                Perro perro = helper.consultarMascota(mascota);
                Log.e("myTag","perro "+perro.getEdadPerro());
                nombrePerrolbl.setText(perro.getNombrePerro());
                razaPerrolbl.setText(perro.getRaza());
                edadPerrolbl.setText(perro.getEdadPerro());
                colorPerrolbl.setText(perro.getColorPerro());
                pesoPerrolbl.setText(String.valueOf(perro.getPesoPerro()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        //CODIGO PARA USAR CAMARA
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                builder.setTitle("Elige una opcion ");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        if (options[seleccion]=="Tomar foto"){
                            openCamera();
                        }else if (options[seleccion]=="Elegir de galeria"){
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent,"Selecciona app"), SELECT_PICTURE);
                        }else if (options[seleccion]=="Cancelar"){
                            dialog.dismiss();
                        }

                    }
                });
                builder.show();//Con esto se muestra el cuadro de dialogo

            }
        });

    }
//----------------------------------------------------------------------------------------
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newfile = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    public  void registro(View v)
    {
        Intent ints = new Intent(this,RegistroMascotaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nombreusuario",usuario);
        ints.putExtras(bundle);
        startActivity(ints);
    }


    public  void rutina(View v) {
        Intent ints = new Intent(this, RutinaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nombreusuario",usuario);
        ints.putExtra("nombreperro", nombrePerrolbl.getText().toString());
        ints.putExtras(bundle);
        startActivity(ints);
    }

    public  void medicamentos(View v) {
        Intent ints = new Intent(this, MedicamentoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("dia",0);
        bundle.putInt("mes",0);
        bundle.putInt("anio",0);
        bundle.putString("nombreusuario",usuario);
        ints.putExtras(bundle);
        startActivity(ints);
    }

    public  void vacunas(View v) {
        Intent ints = new Intent(this, VacunaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("dia",0);
        bundle.putInt("mes",0);
        bundle.putInt("anio",0);
        bundle.putString("nombreusuario",usuario);
        ints.putExtras(bundle);
        startActivity(ints);
    }

    public  void calendario(View v) {
        Intent ints = new Intent(this, CalendarActivity.class);
        startActivity(ints);
    }

    public  void importarDatos(View v) {
        Intent ints = new Intent(this, ImportacionActivity.class);
        startActivity(ints);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                   String dir = Environment.getExternalStorageDirectory() + File.separator
                           + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                   decodeBitMap(dir);
                }
            break;

            case SELECT_PICTURE:
                //if(requestCode== RESULT_OK){
                    Uri path  = data.getData();
                    imageView.setImageURI(path);
                //}
            break;
        }
    }

    private void decodeBitMap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imageView.setImageBitmap(bitmap);
    }
}
