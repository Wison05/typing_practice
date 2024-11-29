package util;

public class Calculator {
    private long startTime;
    private long totalElapsedTime;
    private int correctChars;
    private int totalInputChars;

    public Calculator() {
        resetSession();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void resetSession() {
        startTime = System.currentTimeMillis();
        totalElapsedTime = 0;
        correctChars = 0;
        totalInputChars = 0;
    }

    public void updateElapsedTime() {
        totalElapsedTime = (System.currentTimeMillis() - startTime) / 1000;
    }

    public void addCorrectChars(int count) {
        correctChars += count;
    }

    public void addInputChars(int count) {
        totalInputChars += count;
    }

    public double getAccuracy() {
        return totalInputChars == 0 ? 0 : (correctChars * 100.0 / totalInputChars);
    }

    public int getTypingSpeed() {
        return totalElapsedTime > 0 ? (int) ((60.0 * correctChars) / totalElapsedTime) : 0;
    }

    public long getElapsedTime() {
        return totalElapsedTime;
    }

    public void updateStats(int tempCorrectChars, int tempTotalInputChars) {
        correctChars = tempCorrectChars;
        totalInputChars = tempTotalInputChars;
    }
}