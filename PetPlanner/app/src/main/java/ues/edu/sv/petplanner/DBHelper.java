package ues.edu.sv.petplanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS usuario; ";
    private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS registro; ";
    private static final String DROP_TABLE3 ="DROP TABLE IF EXISTS rutina; ";
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
                        "descripcionraza VARCHAR(50)\n" +
                        ");");
                db.execSQL("CREATE TABLE registro(\n" +
                        "codigoregistro INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "nombreusuario VARCHAR(15) NOT NULL,\n" +
                        "nombreperro VARCHAR(15) NOT NULL, " +
                        "FOREIGN KEY(nombreusuario) REFERENCES usuario(nombreusuario), " +
                        "FOREIGN KEY(nombreperro) REFERENCES perro(nombreperro)" +
                        ");");
                db.execSQL("CREATE TABLE rutina(\n" +
                        "codigorutina VARCHAR(5) NOT NULL PRIMARY KEY,\n" +
                        "codigoregistro INTEGER NOT NULL,\n" +
                        "fecharutina VARCHAR(10) NOT NULL,\n" +
                        "duracionrutina VARCHAR(5) NOT NULL,\n" +
                        "FOREIGN KEY(codigoregistro) REFERENCES registro(codigoregistro)" +
                        ");");

                db.execSQL("insert into usuario values('Paola','Aguilar',24,'F','admin')");

                db.execSQL("insert into perro values('Pelusa','Poodle',4,'blanco',22)");

                db.execSQL("insert into raza values('Poodle',' Hoy en día se les encuentra frecuentemente en las exposiciones caninas de belleza.')");
                db.execSQL("insert into raza values('Chihuahua','Originario de México. Es una de las razas de perros más antiguas del continente americano')");
                db.execSQL("insert into raza values('Bulldog','Es una raza canina originaria del Reino Unido')");
                db.execSQL("insert into raza values('Pug','Origen histórico en China, pero con el patrocinio de Reino Unido')");

                db.execSQL("insert into registro values(1, 'Paola','Pelusa')");
                db.execSQL("insert into rutina values('RUT1',1, '11/06/2019', '01:30')");
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
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
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

    //Para obtener las razas
    public ArrayList<Raza> obtenerListaRazas() {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Raza raza = null;
        ArrayList<Raza> razaLista = new ArrayList<Raza>();
        Cursor cursor = db.rawQuery("SELECT * FROM raza;", null);

        while (cursor.moveToNext()) {
            raza = new Raza();
            raza.setNombreRaza(cursor.getString(0));
            //raza.setDescripcionRaza(cursor.getString(1));
            razaLista.add(raza);
        }
        return razaLista;
    }

    //Para inicio de sesion
    public int consultarUsuario(String user, String clave) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        int estado = 0;
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

    public String RegistroMascota(String nombrePerro, String raza, int edadPerro,
                                  String colorPerro, Float pesoPerro ) {
        String regInsertados="Registrado";
        long contador=0;

        ContentValues c = new ContentValues();
        c.put("nombreperro", nombrePerro);
        c.put("raza",raza);
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

    public String insertarRegistro(int codigoRegistro, String nombreUsuario, String nombrePerro) {
        String regInsertados="Registrado ";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("codigoregistro",codigoRegistro);
        c.put("nombreusuario", nombreUsuario);
        c.put("nombreperro", nombrePerro);
        contador=db.insert("registro", null, c);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String RegistroRutina(Rutina rutina) {
        String regInsertados="Registro ";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("codigorutina", rutina.getCodigoRutina());
        c.put("codigoregistro", rutina.getCodigoRegistro());
        c.put("fecharutina", rutina.getFechaRutina());
        c.put("duracionrutina", rutina.getDuracionRutina());
        contador=db.insert("rutina", null, c);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public int cantidadRutina(){
        Cursor c = db.rawQuery("select * from rutina",null);
        int cantidad = c.getCount();
        return cantidad;
    }

}
