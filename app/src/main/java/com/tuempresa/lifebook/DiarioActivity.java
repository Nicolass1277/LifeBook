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
<<<<<<< HEAD
import java.text.SimpleDateFormat;
import java.util.Date;
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19

public class DiarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText diarioTexto;
<<<<<<< HEAD
    private Button guardarButton, backButton, editarButton, verMesButton;
=======
    private Button guardarButton, backButton, editarButton;
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
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
<<<<<<< HEAD
        verMesButton = findViewById(R.id.verMesButton);  // Botón para ver entradas del mes
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19

        diarioTexto.setEnabled(false);
        sharedPreferences = getSharedPreferences("Diario", MODE_PRIVATE);

<<<<<<< HEAD
        // Acción de seleccionar una fecha
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            cargarMensajeDelDia(selectedDate);
            diarioTexto.setEnabled(false);
        });

<<<<<<< HEAD
        // Acción de editar la entrada
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
        editarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                diarioTexto.setEnabled(true);
                diarioTexto.requestFocus();
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

<<<<<<< HEAD
        // Acción de guardar la entrada
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
        guardarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                guardarMensajeDelDia(selectedDate, diarioTexto.getText().toString());
                diarioTexto.setEnabled(false);
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

<<<<<<< HEAD
        // Acción de volver atrás
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DiarioActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
<<<<<<< HEAD

        // Acción de ver todas las entradas del mes
        verMesButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                String mes = selectedDate.split("-")[1];  // Obtener el mes de la fecha seleccionada
                verEntradasDelMes(mes);
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
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
<<<<<<< HEAD

    private void verEntradasDelMes(String mes) {
        StringBuilder allEntries = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.split("-")[1].equals(mes)) {  // Si la fecha del mensaje está en el mes seleccionado
                String entry = sharedPreferences.getString(key, "");
                String formattedDate = sdf.format(new Date()); // Obtener la fecha y hora actual
                allEntries.append("Fecha: ").append(key).append("\n")
                        .append("Hora: ").append(formattedDate).append("\n")
                        .append(entry).append("\n\n");
            }
        }

        if (allEntries.length() > 0) {
            Intent intent = new Intent(DiarioActivity.this, VerEntradasDelMesActivity.class);
            intent.putExtra("entradas", allEntries.toString());
            startActivity(intent);
        } else {
            Toast.makeText(DiarioActivity.this, "No hay entradas para este mes", Toast.LENGTH_SHORT).show();
        }
    }
=======
>>>>>>> 6bef70bbd2595c9481e093d189ea3c22e198ad19
}
