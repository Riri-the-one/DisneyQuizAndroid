package com.example.projet_quiz;

public final class QuizScoringTest {
    private static int checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("Scoring check " + checks + " failed");
    }
    public static void main(String[] args) {
        int perfect = 0;
        for (int question = 1; question <= 5; question++) {
            perfect = QuizScoring.nextScore(perfect, question, true);
            check(perfect == question);
            for (int previous = -2; previous <= 9; previous++) {
                int correct = QuizScoring.nextScore(previous, question, true);
                int wrong = QuizScoring.nextScore(previous, question, false);
                check(correct >= 1 && correct <= question);
                check(wrong >= 0 && wrong < question);
                check(correct == wrong + 1);
            }
        }
        check(QuizScoring.percentage(perfect) == 100);
        check(QuizScoring.percentage(0) == 0);
        check(QuizScoring.percentage(-5) == 0);
        check(QuizScoring.percentage(99) == 100);
        check(QuizScoring.percentage(3) == 60);
        try { QuizScoring.nextScore(0, 0, true); throw new AssertionError("Invalid question accepted"); }
        catch (IllegalArgumentException expected) { checks++; }
        System.out.println("PASS: " + checks + " scoring regression checks");
    }
}
