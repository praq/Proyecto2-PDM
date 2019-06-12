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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class PerfilActivity extends AppCompatActivity {

    private String APP_DIRECTORY = "PicturesPetP/";  //Direccion de la app donde se guardara la imagen
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";  //nombre temporal
    private final int PHOTO_CODE = 100;     //para usar metodo OnActivityResult
    private final int SELECT_PICTURE = 200;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        imageView = (ImageView) findViewById(R.id.imagen);
        button = (Button) findViewById(R.id.buttonImage);

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
        startActivity(ints);
    }


    public  void rutina(View v) {
        Intent ints = new Intent(this, RutinaActivity.class);
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
