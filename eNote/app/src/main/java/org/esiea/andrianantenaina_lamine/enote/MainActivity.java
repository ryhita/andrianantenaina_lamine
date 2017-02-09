package org.esiea.andrianantenaina_lamine.enote;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button clear, save, download;
    String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        note = preferences.getString("note","");
        download = (Button) findViewById(R.id.download);

        editText.setText(note);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note = editText.getText().toString();
                SharedPreferences preferences = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("note",note);
                editor.commit();

                Toast.makeText(getApplicationContext(),getString(R.string.toast),Toast.LENGTH_LONG).show();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }



    public void dialog(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
        a_builder.setMessage(getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        note = "";
                        editText.setText(note);
                        notification();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    public void notification() {
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(this);
        ncb.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("eNote")
                .setContentText(getString(R.string.notification));

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(7,ncb.build());
    }

    public void download(){
        startActivity(new Intent(this,SecondActivity.class));
    }
}
