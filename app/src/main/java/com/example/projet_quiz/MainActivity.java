package com.example.projet_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private boolean pending;
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.Password);
        Button login = findViewById(R.id.btnsignin);
        login.setOnClickListener(view -> {
            if (pending) return;
            String mail = email.getText().toString().trim();
            String secret = password.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                email.setError(getString(R.string.invalid_email));
                return;
            }
            if (secret.isEmpty()) {
                password.setError(getString(R.string.password_required));
                return;
            }
            pending = true;
            login.setEnabled(false);
            login.setText(R.string.signing_in);
            auth.signInWithEmailAndPassword(mail, secret).addOnCompleteListener(this, task -> {
                pending = false;
                login.setEnabled(true);
                login.setText(R.string.btn_signin);
                if (task.isSuccessful()) openQuiz();
                else Toast.makeText(this, R.string.signin_failed, Toast.LENGTH_LONG).show();
            });
        });
        findViewById(R.id.register).setOnClickListener(view ->
                startActivity(new Intent(this, Activity_register.class)));
    }
    @Override protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) openQuiz();
    }
    private void openQuiz() {
        startActivity(new Intent(this, Quiz1.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}
