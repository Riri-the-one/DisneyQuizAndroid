package com.example.projet_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;

/** Shared question navigation; existing question screens and artwork are preserved. */
public abstract class BaseQuizActivity extends AppCompatActivity {
    protected abstract int layoutId();
    protected abstract int correctAnswerId();
    protected abstract int questionNumber();
    protected abstract Class<?> nextScreen();
    private boolean navigating;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            return;
        }
        EdgeToEdge.enable(this);
        setContentView(layoutId());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
        RadioGroup choices = findViewById(R.id.rg);
        Button next = findViewById(R.id.btnNext);
        final int previousScore = questionNumber() == 1 ? 0 : getIntent().getIntExtra("score", 0);
        next.setOnClickListener(view -> {
            if (navigating) return;
            int selected = choices.getCheckedRadioButtonId();
            if (selected == -1) {
                Toast.makeText(this, R.string.choose_answer, Toast.LENGTH_SHORT).show();
                return;
            }
            navigating = true;
            next.setEnabled(false);
            int nextScore = QuizScoring.nextScore(previousScore, questionNumber(), selected == correctAnswerId());
            startActivity(new Intent(this, nextScreen()).putExtra("score", nextScore));
            finish();
        });
    }
}
