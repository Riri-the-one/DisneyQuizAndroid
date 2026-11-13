package com.example.projet_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_register extends AppCompatActivity {

    Button btnRegister;
    EditText FirstName, Email, Password, ConfirmPassword;
    TextView tvBack;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialisation Firebase
        FirebaseApp.initializeApp(this);
        myAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Liaison des vues
        FirstName = findViewById(R.id.firstname_register);
        Email = findViewById(R.id.email_register);
        Password = findViewById(R.id.password_register);
        ConfirmPassword = findViewById(R.id.confirm_password);
        btnRegister = findViewById(R.id.btnregister);
        tvBack = findViewById(R.id.back_to_login);

        // Action du bouton d'inscription
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = FirstName.getText().toString().trim();
                String mail = Email.getText().toString().trim();
                String password = Password.getText().toString();
                String confirmPassword = ConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(firstname)) {
                    Toast.makeText(Activity_register.this, "Remplir le champ prénom", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(Activity_register.this, "Remplir le champ email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Activity_register.this, "Remplir le champ mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    Email.setError(getString(R.string.invalid_email));
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(Activity_register.this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Activity_register.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Appel de la méthode Firebase
                signup(mail, password, firstname);
            }
        });

        // Action du bouton retour
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Retourne à l'écran précédent
            }
        });
    }

    // Méthode d'inscription avec Firebase
    private void signup(String email, String password, String firstName) {
        btnRegister.setEnabled(false);
        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        btnRegister.setEnabled(true);
                        if (task.isSuccessful()) {
                            com.google.firebase.auth.FirebaseUser newUser = myAuth.getCurrentUser();
                            if (newUser != null) {
                                newUser.updateProfile(new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                        .setDisplayName(firstName).build());
                            }
                            Toast.makeText(Activity_register.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Activity_register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Activity_register.this, R.string.registration_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
