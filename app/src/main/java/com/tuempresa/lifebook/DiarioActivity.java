package com.tuempresa.lifebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DiarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText diarioTexto;
    private Button guardarButton, backButton, editarButton;
    private String selectedDate = "";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        calendarView = findViewById(R.id.calendarView);
        diarioTexto = findViewById(R.id.diarioTexto);
        guardarButton = findViewById(R.id.guardarButton);
        backButton = findViewById(R.id.backButton);
        editarButton = findViewById(R.id.editarButton);

        diarioTexto.setEnabled(false);
        sharedPreferences = getSharedPreferences("Diario", MODE_PRIVATE);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            cargarMensajeDelDia(selectedDate);
            diarioTexto.setEnabled(false);
        });

        editarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                diarioTexto.setEnabled(true);
                diarioTexto.requestFocus();
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

        guardarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                guardarMensajeDelDia(selectedDate, diarioTexto.getText().toString());
                diarioTexto.setEnabled(false);
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DiarioActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void cargarMensajeDelDia(String fecha) {
        String mensajeGuardado = sharedPreferences.getString(fecha, "");
        diarioTexto.setText(mensajeGuardado);
    }

    private void guardarMensajeDelDia(String fecha, String mensaje) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(fecha, mensaje);
        editor.apply();
        Toast.makeText(DiarioActivity.this, "Mensaje guardado para el " + fecha, Toast.LENGTH_SHORT).show();
    }
}
