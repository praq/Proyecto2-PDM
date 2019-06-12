package ues.edu.sv.petplanner;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class RegistroMascotaActivity extends AppCompatActivity {

    private String APP_DIRECTORY = "PicturesPetP/";  //Direccion de la app donde se guardara la imagen
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";  //nombre temporal
    private final int PHOTO_CODE = 100;     //para usar metodo OnActivityResult
    private final int SELECT_PICTURE = 200;
    private ImageView imageView;
    private Button button;

    EditText nombrePerro;
    EditText edadPerro;
    EditText colorPerro;
    EditText pesoPerro;
    Spinner spinnerRaza;
    ArrayList<Raza> razas;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mascota);
        helper = new DBHelper(this );
        nombrePerro = (EditText) findViewById(R.id.nombrePerro);
        edadPerro = (EditText) findViewById(R.id.edadPerro);
        colorPerro = (EditText) findViewById(R.id.color);
        pesoPerro =(EditText)findViewById(R.id.peso);
        spinnerRaza = (Spinner) findViewById(R.id.raza);

        //LLENAR SPINNER DE RAZA
        razas = helper.obtenerListaRazas();
        ArrayList<String> nombreRaza = new ArrayList<String>();
        for (int i = 0; i < razas.size(); i++)
        {
            Log.e("myTag", "1 ----- ");
            nombreRaza.add(razas.get(i).getNombreRaza());
            Log.e("myTag", "2 ----- ");

        }
        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,nombreRaza);
        spinnerRaza.setAdapter(adapter1);
        Log.e("myTag", "pase por aqui ----- ");

        /*int nombreR = spinnerRaza.getSelectedItemPosition();
        Log.e("myTag", "id ----- "+nombreR);
        Raza raza= razas.get(nombreR);
        raza.setNombreRaza(raza.getNombreRaza());*/



        //CODIGO PARA EL USO DE LA CAMARA
        imageView = (ImageView) findViewById(R.id.imagen);
        button = (Button) findViewById(R.id.buttonImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegistroMascotaActivity.this);
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

    //BOTON REGISTRAR MASCOTA
    public void registro(View v) {
        DBHelper helper = new DBHelper(this);
        String nombrePerr=nombrePerro.getText().toString();
        String edadPerr=edadPerro.getText().toString();
        String colorPerr=colorPerro.getText().toString();
        String pesoPerr=pesoPerro.getText().toString();

        String nombreRaza = String.valueOf(spinnerRaza.getSelectedItemId());
        Raza raza = new Raza();
        raza.setNombreRaza(nombreRaza);

        String regInsertados;
        helper.abrir();
        regInsertados=helper.RegistroMascota( nombrePerr,nombreRaza,Integer.valueOf(edadPerr),colorPerr,Float.valueOf(pesoPerr));
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    //PARA ABRIR LA CAMARA DEL DISPOSITIVO
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
                if(requestCode== RESULT_OK){
                    Uri path  = data.getData();
                    imageView.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitMap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imageView.setImageBitmap(bitmap);
    }
}
