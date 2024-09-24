package com.tuempresa.lifebook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText diarioTexto;
    TextView relojTexto;
    Button guardarButton;
    String selectedDate = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        calendarView = findViewById(R.id.calendarView);
        diarioTexto = findViewById(R.id.diarioTexto);
        relojTexto = findViewById(R.id.relojTexto);
        guardarButton = findViewById(R.id.guardarButton);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("Diario", MODE_PRIVATE);

        // Configurar reloj en tiempo real
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                relojTexto.setText(getCurrentTime());
                handler.postDelayed(this, 1000); // Actualiza cada segundo
            }
        });

        // Manejar la selección de fecha en el calendario
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            // Cargar el texto almacenado para esa fecha
            cargarMensajeDelDia(selectedDate);
        });

        // Manejar la acción del botón Guardar
        guardarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                guardarMensajeDelDia(selectedDate, diarioTexto.getText().toString());
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Obtener la hora actual
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Cargar mensaje almacenado para la fecha seleccionada
    private void cargarMensajeDelDia(String fecha) {
        String mensajeGuardado = sharedPreferences.getString(fecha, "");
        diarioTexto.setText(mensajeGuardado);
    }

    // Guardar mensaje para la fecha seleccionada
    private void guardarMensajeDelDia(String fecha, String mensaje) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(fecha, mensaje);
        editor.apply();
        Toast.makeText(DiarioActivity.this, "Mensaje guardado para el " + fecha, Toast.LENGTH_SHORT).show();
    }
}
