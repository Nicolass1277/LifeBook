package com.tuempresa.lifebook;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VerEntradasDelMesActivity extends AppCompatActivity {

    private TextView entradasTextView;
    private Button eliminarButton, guardarButton;
    private SharedPreferences sharedPreferences;

    private static final int REQUEST_WRITE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entradas_del_mes);

        entradasTextView = findViewById(R.id.entradasTextView);
        eliminarButton = findViewById(R.id.eliminarButton);
        guardarButton = findViewById(R.id.guardarButton);

        sharedPreferences = getSharedPreferences("Diario", Context.MODE_PRIVATE);

        String entradas = getIntent().getStringExtra("entradas");
        if (entradas != null) {
            entradasTextView.setText(entradas);
        } else {
            entradasTextView.setText("No hay entradas para este mes.");
        }

        eliminarButton.setOnClickListener(v -> eliminarTodasLasEntradas());

        guardarButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_PERMISSION);
                } else {
                    guardarEntradasEnWord();
                }
            } else {
                guardarEntradasEnWord();  // No se requiere permiso en Android 10 (API 29) y superior
            }
        });
    }

    private void eliminarTodasLasEntradas() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(VerEntradasDelMesActivity.this, "Todas las entradas del mes han sido eliminadas", Toast.LENGTH_SHORT).show();
        entradasTextView.setText("No hay entradas para este mes.");
    }

    private void guardarEntradasEnWord() {
        String entradas = entradasTextView.getText().toString();
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDirectory, "EntradasDelMes.docx");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(entradas.getBytes());
            Toast.makeText(VerEntradasDelMesActivity.this, "Entradas guardadas en la carpeta Descargas", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(VerEntradasDelMesActivity.this, "Error al guardar el archivo Word", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                guardarEntradasEnWord();
            } else {
                Toast.makeText(this, "Permiso denegado para guardar en almacenamiento", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
