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
    private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS perro; ";
    private static final String DROP_TABLE3 ="DROP TABLE IF EXISTS raza; ";
    private static final String DROP_TABLE4 ="DROP TABLE IF EXISTS registro";
    private static final String DROP_TABLE5 ="DROP TABLE IF EXISTS rutina; ";
    private static final String DROP_TABLE6 ="DROP TABLE IF EXISTS medicamento; ";
    private static final String DROP_TABLE7 ="DROP TABLE IF EXISTS vacuna; ";
    private static final String DROP_TABLE8 ="DROP TABLE IF EXISTS enfermedad; ";

    public ArrayList<Medicamento> medicamentoLista;
    public ArrayList<Rutina> rutinaLista;
    public ArrayList<Vacuna> vacunasLista;
    public ArrayList<Enfermedad> enfermedadLista;
    public ArrayList<Perro> perroLista;
    public ArrayList<Usuario> usuarioLista;
    public ArrayList<String> listaMedicamento,listaVacunas,listaEnfermedad,listaPerro, listaRutina;

    private static final String[] camposRegistro = new String[]{"codigoregistro","nombreusuario","nombreperro"};

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
                        "correo VARCHAR(40),\n" +
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

                db.execSQL("CREATE TABLE medicamento(\n" +
                        "nombreMedicamento VARCHAR(20) NOT NULL PRIMARY KEY,\n" +
                        "codRegistro INTEGER,\n" +
                        "nombreEnfermedad VARCHAR(20),\n" +
                        "descripcionMedicamento VARCHAR(30),\n" +
                        "dosis REAL,\n" +
                        "fecha VARCHAR(15));");

                db.execSQL("CREATE TABLE enfermedad(\n" +
                        "nombreEnfermedad VARCHAR(20) NOT NULL PRIMARY KEY,\n" +
                        "descripcionEnfermedad VARCHAR(30));");

                db.execSQL("CREATE TABLE vacuna(\n" +
                        "nombreVacuna VARCHAR(20) NOT NULL PRIMARY KEY,\n" +
                        "codRegistro INTEGER,\n" +
                        "fecha VARCHAR(15));");

                db.execSQL("insert into usuario values('Paola','Aguilar',24,'F','nenee.aguilar@gmail.com','pao')");

                db.execSQL("insert into perro values('Pelusa','Chihuahua',4,'blanco',22)");
                db.execSQL("insert into perro values('Luigi','Poodle',4,'blanco',22)");

                db.execSQL("insert into raza values('Poodle',' Hoy en día se les encuentra frecuentemente en las exposiciones caninas de belleza.')");
                db.execSQL("insert into raza values('Chihuahua','Originario de México. Es una de las razas de perros más antiguas del continente americano')");
                db.execSQL("insert into raza values('Bulldog','Es una raza canina originaria del Reino Unido')");
                db.execSQL("insert into raza values('Pug','Origen histórico en China, pero con el patrocinio de Reino Unido')");
                db.execSQL("insert into raza values('Criollo','Mezcla de razas')");

                db.execSQL("CREATE TABLE introduccion(nombre VARCHAR(20) PRIMARY KEY, valor INTEGER);");
                db.execSQL("INSERT INTO introduccion VALUES ('inicio',0)");

               // db.execSQL("insert into registro values(1,'Paola','Pelusa')");
                db.execSQL("insert into rutina values('RUT1',1, '11/06/2019', '01:30')");

                db.execSQL("insert into enfermedad values('Rabia','Es gravee')");
                db.execSQL("insert into enfermedad values('Viruela','No es grave')");



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
                db.execSQL(DROP_TABLE4);
                db.execSQL(DROP_TABLE5);
                db.execSQL(DROP_TABLE6);
                db.execSQL(DROP_TABLE7);
                db.execSQL(DROP_TABLE8);
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

    //Para obtener las razas en el spinner
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

    //Para obtener nombre de perros en el spinner
    public ArrayList<Perro> obtenerListaPerros(String usuario) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Perro perro = null;
        ArrayList<Perro> perroLista = new ArrayList<Perro>();
        Cursor cursor = db.rawQuery("SELECT * FROM perro inner join registro on perro.nombreperro=registro.nombreperro WHERE registro.nombreusuario = '"+usuario+"';", null);

        while (cursor.moveToNext()) {
            perro = new Perro();
            perro.setNombrePerro(cursor.getString(0));
            perroLista.add(perro);
        }
        return perroLista;
    }

    //Consultar perro
    public Perro consultarMascota(String nombrePerro) {
        Perro perro = new Perro();
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        int estado = 0;
        Cursor fila;
        fila=db.rawQuery("select * from perro where nombreperro='"+nombrePerro+"'",null);
        if (fila.moveToFirst()) {
            estado =1;
            Log.e("myTag", "estado1 ----- ");
            perro.setNombrePerro(fila.getString(0));
            perro.setRaza(fila.getString(1));
            perro.setEdadPerro(fila.getString(2));
            perro.setColorPerro(fila.getString(3));
            perro.setPesoPerro(fila.getFloat(4));
        }else{
            estado =2;
            Log.e("myTag", "estado2 ----- ");
        }
        return perro;
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
                                String sexoUsu, String correo, String contrasena) {
        String regInsertados="Registrado";
        long contador=0;

        ContentValues c = new ContentValues();
        c.put("nombreusuario", nombreUsu);
        c.put("apellidousuario", apellidoUsu);
        c.put("edadusuario", edadUsu);
        c.put("sexousuario", sexoUsu);
        c.put("correo",correo);
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

    public String RegistroMascota(String nombrePerro, String raza, int edadPerro, String colorPerro, Float pesoPerro ) {
        String regInsertados="Registrado";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("nombreperro", nombrePerro);
        c.put("raza",raza);
        c.put("edadperro", edadPerro);
        c.put("colorperro", colorPerro);
        c.put("pesoperro", pesoPerro);
        contador=db.insert("perro", null, c);
        if(contador==-1 || contador==0) {
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
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String insertarRegistro(Registro registro) {
        String regInsertados="Registrado ";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("nombreusuario", registro.getNombreUsuario());
        c.put("nombreperro", registro.getNombrePerro());
        contador=db.insert("registro", null, c);
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public Registro consultarRegistro(String nombrePerro){
        String [] id = {nombrePerro};
        Cursor cursor= db.query("registro",camposRegistro,"nombreperro = ?",id,null,null,null);
        if(cursor.moveToFirst()){
            Registro registro = new Registro();
            registro.setCodRegistro(cursor.getInt(0));
            registro.setNombreUsuario(cursor.getString(1));
            registro.setNombrePerro(cursor.getString(2));
            return registro;
        }else
            return null;
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
        if(contador==-1 || contador==0) {
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

    //Consultar codigoregistro para rutina
    public Registro consultarCodRegistro(String nombrePerro){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Registro registro = null;
        Cursor c = db.rawQuery("SELECT * FROM registro inner join perro on registro.nombreperro=perro.nombreperro WHERE registro.nombreperro='"+nombrePerro+"';",null);
        if(c.moveToNext()){
            registro = new Registro();
            registro.setCodRegistro(c.getInt(0));
            registro.setNombreUsuario(c.getString(1));
            registro.setNombrePerro(c.getString(2));
        }
        return registro;
    }

    public void obtenerListaRutina() {
        listaRutina = new ArrayList<String>();
        for (int i = 0; i < rutinaLista.size(); i++){
            listaRutina.add("COD RUTINA: "+rutinaLista.get(i).getCodigoRutina()+ "\nCOD REGISTRO : "+rutinaLista.get(i).getCodigoRegistro()+ "\nFECHA: "+rutinaLista.get(i).getFechaRutina()+ "\nDURACIÓN: "+rutinaLista.get(i).getDuracionRutina());
        }
    }

    public void consultarListaRutinas (int codRegistro) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Rutina rutina = null;
        rutinaLista = new ArrayList<Rutina>();
        Cursor cursor = db.rawQuery("SELECT * FROM rutina inner join registro on rutina.codigoregistro=registro.codigoregistro WHERE rutina.codigoregistro="+codRegistro+";", null);
        while (cursor.moveToNext()) {
            rutina = new Rutina();
            rutina.setCodigoRutina(cursor.getString(0));
            rutina.setCodigoRegistro(cursor.getInt(1));
            rutina.setFechaRutina(cursor.getString(2));
            rutina.setDuracionRutina(cursor.getString(3));
            rutinaLista.add(rutina);
        }
        obtenerListaRutina();
    }

    //Manuel
    public String InsertarMedicamento(Medicamento medicamento) {
        String regInsertados="Registrado";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("nombreMedicamento", medicamento.getNombreMedicamento());
        c.put("nombreEnfermedad", medicamento.getNombreEnfermedad());
        c.put("descripcionMedicamento", medicamento.getDescripcionMedicamento());
        c.put("dosis", medicamento.getDosis());
        c.put("fecha", medicamento.getFecha());
        contador=db.insert("medicamento", null, c);
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String InsertarVacuna(Vacuna vacuna) {
        String regInsertados="Registrado";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("nombreVacuna", vacuna.getNombreVacuna());
        c.put("codRegistro", vacuna.getCodRegistro());
        c.put("fecha",vacuna.getFecha());
        contador=db.insert("vacuna", null, c);
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public String InsertarEnfermedad(Enfermedad enfermedad) {
        String regInsertados="Registrado";
        long contador=0;
        ContentValues c = new ContentValues();
        c.put("nombreEnfermedad", enfermedad.getNombreEnfermedad());
        c.put("descripcionEnfermedad", enfermedad.getDescripcionEnfermedad());
        contador=db.insert("enfermedad", null, c);
        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado.";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public void consultarListaMedicamentos () {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Medicamento medicamento = null;
        medicamentoLista = new ArrayList<Medicamento>();
        Cursor cursor = db.rawQuery("SELECT * FROM medicamento;", null);
        while (cursor.moveToNext()) {
            medicamento = new Medicamento();
            medicamento.setNombreMedicamento(cursor.getString(0));
            medicamento.setCodRegistro(cursor.getInt(1));
            medicamento.setNombreEnfermedad(cursor.getString(2));
            medicamento.setDescripcionMedicamento(cursor.getString(3));
            medicamento.setDosis(cursor.getFloat(4));
            medicamento.setFecha(cursor.getString(5));
            medicamentoLista.add(medicamento);
        }
        obtenerListaMedicamento();
    }

    public void obtenerListaMedicamento() {
        listaMedicamento = new ArrayList<String>();
        for (int i = 0; i < medicamentoLista.size(); i++){
            listaMedicamento.add("NOMBRE : "+medicamentoLista.get(i).getNombreMedicamento()+ "\nDESCRIPCION : "+medicamentoLista.get(i).getDescripcionMedicamento()+"\n\n");
        }
    }

    public void consultarListaVacunas () {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Vacuna vacuna = null;
        vacunasLista = new ArrayList<Vacuna>();
        Cursor cursor = db.rawQuery("SELECT * FROM vacuna;", null);
        while (cursor.moveToNext()) {
            vacuna = new Vacuna();
            vacuna.setNombreVacuna(cursor.getString(0));
            vacuna.setCodRegistro(cursor.getInt(1));
            vacuna.setFecha(cursor.getString(2));
            vacunasLista.add(vacuna);
        }
        obtenerListaVacunas();
    }

    public void obtenerListaVacunas() {
        listaVacunas = new ArrayList<String>();
        for (int i = 0; i < vacunasLista.size(); i++){
            listaVacunas.add("NOMBRE : "+vacunasLista.get(i).getNombreVacuna()+"\n");
        }
    }

    public void consultarListaEnfermedades(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Enfermedad enfermedad= null;
        enfermedadLista= new ArrayList<Enfermedad>();
        Cursor cursor = db.rawQuery("SELECT * FROM enfermedad;", null);
        while (cursor.moveToNext()) {
            enfermedad = new Enfermedad();
            enfermedad.setNombreEnfermedad(cursor.getString(0));
            enfermedad.setDescripcionEnfermedad(cursor.getString(1));
            enfermedadLista.add(enfermedad);
        }
        obtenerListaEnfermedad();
    }
    public void obtenerListaEnfermedad() {
        listaEnfermedad = new ArrayList<String>();
        for (int i = 0; i < enfermedadLista.size(); i++){
            listaEnfermedad.add(enfermedadLista.get(i).getNombreEnfermedad());
        }
    }

    public void consultarListaEventoMedicamento(String f) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Medicamento medicamento = null;
        String [] id = {f};
        medicamentoLista = new ArrayList<Medicamento>();
        Cursor cursor = db.rawQuery("SELECT * FROM medicamento WHERE fecha = ?;", id);
        while (cursor.moveToNext()) {
            medicamento = new Medicamento();
            medicamento.setNombreMedicamento(cursor.getString(0));
            medicamento.setCodRegistro(cursor.getInt(1));
            medicamento.setNombreEnfermedad(cursor.getString(2));
            medicamento.setDescripcionMedicamento(cursor.getString(3));
            medicamento.setDosis(cursor.getFloat(4));
            medicamento.setFecha(cursor.getString(5));
            medicamentoLista.add(medicamento);
        }
        obtenerListaMedicamento();
    }

    public void consultarListaEventoVacuna(String f) {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Vacuna vacuna = null;
        String [] id = {f};
        vacunasLista = new ArrayList<Vacuna>();
        Cursor cursor = db.rawQuery("SELECT * FROM vacuna WHERE fecha = ?;", id);
        while (cursor.moveToNext()) {
            vacuna = new Vacuna();
            vacuna.setNombreVacuna(cursor.getString(0));
            vacuna.setCodRegistro(cursor.getInt(1));
            vacuna.setFecha(cursor.getString(2));
            vacunasLista.add(vacuna);
        }
        obtenerListaVacunas();
    }

    public void consultarListaPerro(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] id = {UsuarioAdmin};
        Perro perro= null;
        perroLista= new ArrayList<Perro>();
        Cursor cursor = db.rawQuery("SELECT * FROM perro INNER JOIN registro WHERE perro.nombreperro = registro.nombreperro AND registro.nombreusuario= ?;", id);
        while (cursor.moveToNext()) {
            perro = new Perro();
            perro.setNombrePerro(cursor.getString(0));
            perro.setRaza(cursor.getString(1));
            perro.setEdadPerro(cursor.getString(2));
            perro.setColorPerro(cursor.getString(3));
            perro.setPesoPerro(cursor.getFloat(4));
            perroLista.add(perro);
        }
        obtenerListaPerro();
    }
    public void obtenerListaPerro() {
        listaPerro = new ArrayList<String>();
        for (int i = 0; i < perroLista.size(); i++){
            listaPerro.add(perroLista.get(i).getNombrePerro());
        }
    }

    public Usuario consultarUsuarioRegistrado(String usuarioRegistrado){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Usuario usuario= null;
        usuarioLista= new ArrayList<Usuario>();
        Cursor cursor = db.rawQuery("SELECT * FROM usuario WHERE nombreusuario='"+usuarioRegistrado+"';", null);
        while (cursor.moveToNext()) {
            usuario = new Usuario();
            usuario.setNombreUsuario(cursor.getString(0));
            usuario.setApellidoUsuario(cursor.getString(1));
            usuario.setEdadUsuario(cursor.getInt(2));
            usuario.setSexoUsuario(cursor.getString(3));
            usuario.setCorreo(cursor.getString(4));
            usuario.setContraseña(cursor.getString(5));
        }
        return usuario;
    }


    //esto es para que la introduccion no se haga frecuentemente
    public int consultarIntro(){
        String [] id = {"inicio"};
        int valor;
        Cursor cursor= db.query("introduccion",null,"nombre=?",id,null,null,null);
        if(cursor.moveToFirst()){
            valor= cursor.getInt(1);
            return valor;
        }else
            return 0;
    }

    public String actualizarIntroduccion(){
        String [] id= {"inicio"};
        ContentValues cv= new ContentValues();
        cv.put("valor",1);
        db.update("introduccion",cv,"nombre=?",id);
        return "Finalizada la introduccion";
    }
}
