package com.example.projet_quiz;

/** Score bounds are derived from the current question, never from a display label. */
final class QuizScoring {
    static final int QUESTIONS = 5;
    private QuizScoring() { }
    static int nextScore(int previous, int question, boolean correct) {
        if (question < 1 || question > QUESTIONS) throw new IllegalArgumentException("Invalid question");
        return Math.max(0, Math.min(previous, question - 1)) + (correct ? 1 : 0);
    }
    static int percentage(int score) {
        return Math.max(0, Math.min(score, QUESTIONS)) * 100 / QUESTIONS;
    }
}
