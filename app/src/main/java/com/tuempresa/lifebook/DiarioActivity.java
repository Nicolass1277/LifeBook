package com.tuempresa.lifebook;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText diarioTexto;
    TextView relojTexto;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        calendarView = findViewById(R.id.calendarView);
        diarioTexto = findViewById(R.id.diarioTexto);
        relojTexto = findViewById(R.id.relojTexto);

        // Configurar reloj en tiempo real
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                relojTexto.setText(getCurrentTime());
                handler.postDelayed(this, 1000); // Actualizar cada segundo
            }
        });

        // Manejar la selección de fecha en el calendario
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            // Aquí cargarías el texto almacenado para esa fecha si ya existe
            // Puedes agregar lógica para guardar/recuperar textos
        });
    }

    // Obtener la hora actual
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
