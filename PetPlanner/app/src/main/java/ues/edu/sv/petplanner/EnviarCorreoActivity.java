package ues.edu.sv.petplanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnviarCorreoActivity extends Activity {
    Session session = null;
    EditText reciep, sub, msg;
    String correo;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);
        Button login = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        sub = (EditText) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);

        correo = "elixa.mendex98@gmail.com";
        contraseña = "eli12345";
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        message.setSubject(sub.getText().toString());
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciep.getText().toString()));
                        message.setContent(msg.getText().toString(), "text/html; charset=utf-8");
                        Transport.send(message);
                        limpiarTexto();
                        Toast.makeText(EnviarCorreoActivity.this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(EnviarCorreoActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiarTexto(){
        reciep.setText("");
        sub.setText("");
        msg.setText("");
    }
}
