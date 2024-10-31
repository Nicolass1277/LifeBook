package com.tuempresa.lifebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private Button loginButton, registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        dbHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.checkUser(user, pass)) {
                    Intent intent = new Intent(MainActivity.this, DiarioActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.addUser(user, pass)) {
                    Toast.makeText(MainActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
