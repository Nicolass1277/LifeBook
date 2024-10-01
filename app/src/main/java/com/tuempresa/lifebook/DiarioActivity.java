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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText diarioTexto;
    TextView relojTexto, mensajesGuardados;
    Button guardarButton, editarButton, verMensajesMesButton;
    String selectedDate = "";
    boolean isEditing = false;  // Variable para controlar el estado de edición
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        // Inicializar vistas
        calendarView = findViewById(R.id.calendarView);
        diarioTexto = findViewById(R.id.diarioTexto);
        relojTexto = findViewById(R.id.relojTexto);
        guardarButton = findViewById(R.id.guardarButton);
        editarButton = findViewById(R.id.editarButton);
        verMensajesMesButton = findViewById(R.id.verMensajesMesButton); // Nuevo botón
        mensajesGuardados = findViewById(R.id.mensajesGuardados);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("Diario", MODE_PRIVATE);

        // Actualizar el reloj en tiempo real
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                relojTexto.setText(getCurrentTime());
                handler.postDelayed(this, 1000); // Actualiza cada segundo
            }
        });

        // Manejar la selección de fecha en el CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            // Cargar el texto almacenado para esa fecha
            cargarMensajeDelDia(selectedDate);
            isEditing = false;  // Reiniciar el modo de edición al seleccionar una nueva fecha
        });

        // Manejar el clic en el botón de Guardar
        guardarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                if (isEditing) {
                    // Si estamos editando, actualizar el mensaje en vez de concatenar
                    editarMensajeDelDia(selectedDate, diarioTexto.getText().toString());
                } else {
                    guardarMensajeDelDia(selectedDate, diarioTexto.getText().toString());
                }
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejar el clic en el botón de Editar
        editarButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                String mensajeActual = sharedPreferences.getString(selectedDate, "");
                if (!mensajeActual.isEmpty()) {
                    // Cargar el mensaje existente en el campo de texto para editarlo
                    diarioTexto.setText(mensajeActual);
                    isEditing = true;  // Entramos en modo de edición
                    Toast.makeText(DiarioActivity.this, "Edita tu mensaje para el " + selectedDate, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DiarioActivity.this, "No hay ningún mensaje guardado para editar.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DiarioActivity.this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejar el clic en el botón de Ver Mensajes del Mes
        verMensajesMesButton.setOnClickListener(v -> {
            if (!selectedDate.isEmpty()) {
                // Extraer el mes y año de la fecha seleccionada
                String[] parts = selectedDate.split("-");
                int mes = Integer.parseInt(parts[1]);
                int año = Integer.parseInt(parts[2]);

                // Mostrar mensajes del mes en un nuevo Activity o en el mismo
                verMensajesDelMes(mes, año);
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

    // Cargar mensajes guardados para la fecha seleccionada
    private void cargarMensajeDelDia(String fecha) {
        String mensajeGuardado = sharedPreferences.getString(fecha, "");
        diarioTexto.setText("");  // Limpiar el campo de texto para nuevas entradas
        mensajesGuardados.setText("Mensajes guardados para " + fecha + ": " + mensajeGuardado);
    }

    // Guardar un nuevo mensaje para la fecha seleccionada
    private void guardarMensajeDelDia(String fecha, String mensaje) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String mensajeExistente = sharedPreferences.getString(fecha, "");
        // Añadir nuevo mensaje al anterior (si existe)
        mensajeExistente += "\n" + mensaje;
        editor.putString(fecha, mensajeExistente);
        editor.apply();
        Toast.makeText(DiarioActivity.this, "Mensaje guardado para el " + fecha, Toast.LENGTH_SHORT).show();
        cargarMensajeDelDia(fecha);  // Refrescar la vista de mensajes guardados
    }

    // Editar el mensaje guardado (reemplazar el anterior)
    private void editarMensajeDelDia(String fecha, String mensaje) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(fecha, mensaje);  // Reemplaza el mensaje existente
        editor.apply();
        Toast.makeText(DiarioActivity.this, "Mensaje editado para el " + fecha, Toast.LENGTH_SHORT).show();
        isEditing = false;  // Salir del modo de edición
        cargarMensajeDelDia(fecha);  // Refrescar la vista de mensajes guardados
    }

    // Mostrar todos los mensajes guardados del mes
    private void verMensajesDelMes(int mes, int año) {
        StringBuilder mensajesMes = new StringBuilder();
        for (int dia = 1; dia <= 31; dia++) {
            String fecha = dia + "-" + mes + "-" + año;
            String mensajeGuardado = sharedPreferences.getString(fecha, "");
            if (!mensajeGuardado.isEmpty()) {
                mensajesMes.append("Día ").append(dia).append("-").append(mes).append("-").append(año).append(": \n").append(mensajeGuardado).append("\n\n");
            }
        }

        if (mensajesMes.length() > 0) {
            // Mostrar todos los mensajes del mes en el TextView o puedes crear un nuevo Activity
            mensajesGuardados.setText(mensajesMes.toString());
        } else {
            Toast.makeText(this, "No hay mensajes guardados para este mes.", Toast.LENGTH_SHORT).show();
        }
    }
}
