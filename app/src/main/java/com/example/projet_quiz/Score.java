package com.example.projet_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Score extends AppCompatActivity {

    ProgressBar pb;
    TextView tvscore;
    Button bTryAgain;
    Button bLogout;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pb = findViewById(R.id.progressBar);
        tvscore = findViewById(R.id.textView6);
        bLogout = findViewById(R.id.btnLogOut);
        bTryAgain = findViewById(R.id.button2);

        score = getIntent().getIntExtra("score", 0);

        int percentage = QuizScoring.percentage(score);
        tvscore.setText(getString(R.string.score_percentage, percentage));
        pb.setIndeterminate(false);
        pb.setMax(100);
        pb.setProgress(percentage);

        bTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Quiz1.class));
                finish();
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Score.this, "Merci pour votre participation !", Toast.LENGTH_SHORT).show();
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
