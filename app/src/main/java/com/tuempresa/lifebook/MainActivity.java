package com.tuempresa.lifebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private Button biometricLoginButton, googleLoginButton;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        biometricLoginButton = findViewById(R.id.biometricLoginButton);
        googleLoginButton = findViewById(R.id.googleLoginButton);

        // Verifica si el usuario ya está autenticado con Google
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(MainActivity.this, DiarioActivity.class));
            finish();
        }

        // Configuración de inicio de sesión de Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        biometricLoginButton.setOnClickListener(v -> authenticateBiometric());
        googleLoginButton.setOnClickListener(v -> signInWithGoogle());
    }

    private void authenticateBiometric() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor,
                    new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                            startActivity(new Intent(MainActivity.this, DiarioActivity.class));
                            finish();
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            Toast.makeText(MainActivity.this, "Autenticación fallida", Toast.LENGTH_SHORT).show();
                        }
                    });

            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Inicio de sesión con huella")
                    .setNegativeButtonText("Cancelar")
                    .build();

            biometricPrompt.authenticate(promptInfo);
        } else {
            Toast.makeText(this, "La autenticación biométrica no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    if (task.isSuccessful()) {
                        GoogleSignInAccount account = task.getResult();
                        Toast.makeText(MainActivity.this, "Bienvenido, " + account.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, DiarioActivity.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Error de autenticación con Google", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
