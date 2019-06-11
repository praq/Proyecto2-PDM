package ues.edu.sv.petplanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS usuario; ";
    public static String UsuarioAdmin;

    public DBHelper(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "petplanner.s3db";
        private static final int VERSION = 1;
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE usuario(\n" +
                        "nombreusuario VARCHAR(15) NOT NULL PRIMARY KEY,\n" +
                        "apellidousuario VARCHAR(15),\n" +
                        "edadusuario INTEGER,\n" +
                        "sexousuario VARCHAR(1),\n" +
                        "contrasena VARCHAR(8)\n" +
                        ");");
                db.execSQL("CREATE TABLE perro(\n" +
                        "nombreperro VARCHAR(15) NOT NULL PRIMARY KEY,\n" +
                        "raza VARCHAR(15),\n" +
                        "edadperro INTEGER,\n" +
                        "colorperro VARCHAR(10),\n" +
                        "pesoperro FLOAT\n" +
                        ");");
                db.execSQL("CREATE TABLE raza(\n" +
                        "nombreraza VARCHAR(15) NOT NULL PRIMARY KEY,\n" +
                        "descripcionraza VARCHAR(30)\n" +
                        ");");
                db.execSQL("insert into usuario values('Paola','Aguilar',24,'F','admin')");
                db.execSQL("insert into perro values('Pelusa','pug',4,'blanco',22)");

            }catch(SQLException e){
                e.printStackTrace();
            }

        }
        @Override
        // public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
// }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }

    /*public String llenarBD()
    {
        abrir();
        db.execSQL("DELETE FROM usuario;");
        db.execSQL("DELETE FROM perro;");

        //nombreusuario, apellidousuario, edadusuario, sexousuario, contrasena
        db.execSQL("insert into usuario values('Admin','Admin',20,'F','admin')");
        db.execSQL("insert into perro values('Pelusa','pug',4,'blanco',22)");

        return "Bienvenido";
    }*/

    //Para inicio de sesion
    public int consultarUsuario(String user, String clave) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        int estado = 0;
        String usua = "", pass = "";
        Cursor fila;
        UsuarioAdmin = user;
        Log.e("myTag", "usuario ----- "+user);
        Log.e("myTag", "pass ----- "+clave);
        fila=db.rawQuery("SELECT nombreusuario,contrasena FROM usuario WHERE nombreusuario='"+user+"' and contrasena='"+clave+"'",null);
        if (fila.moveToFirst()) {
                estado =1;
                Log.e("myTag", "estado1 ----- ");
        }else{
                estado =2;
                Log.e("myTag", "estado2 ----- ");
        }
        return estado;
    }

    public String RegistroUsuario(String nombreUsu, String apellidoUsu, int edadUsu,
                                String sexoUsu, String contrasena) {
        String regInsertados="Registrado";
        long contador=0;

        ContentValues c = new ContentValues();
        c.put("nombreusuario", nombreUsu);
        c.put("apellidousuario", apellidoUsu);
        c.put("edadusuario", edadUsu);
        c.put("sexousuario", sexoUsu);
        c.put("contrasena", contrasena);

        contador=db.insert("usuario", null, c);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String RegistroMascota(String nombrePerro, int edadPerro,
                                  String colorPerro, Float pesoPerro ) {
        String regInsertados="Registrado";
        long contador=0;

        ContentValues c = new ContentValues();
        c.put("nombreperro", nombrePerro);
        c.put("edadperro", edadPerro);
        c.put("colorperro", colorPerro);
        c.put("pesoperro", pesoPerro);
        contador=db.insert("perro", null, c);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

}
