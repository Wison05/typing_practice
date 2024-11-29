package ui;

import txt.TxtReader;
import util.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class PlayScreen extends JFrame {
    private JTextField[] sentenceFields;
    private JTextField[] inputFields;
    private JTextField accuracyField;
    private JTextField currentTypingSpeedField;
    private JTextField timeField;
    private List<String> lines;
    private int currentIndex = 0;
    private Calculator calculator;
    private Timer timer;

    public PlayScreen(int fileNum) {
        try {
            TxtReader txtReader = new TxtReader();
            this.lines = txtReader.txtRead(fileNum);
            this.calculator = new Calculator();
            createGUI();
            updateSentences();
            startTimers();
            calculator.start();

            inputFields[0].requestFocusInWindow();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다.");
            this.dispose();
        }
    }

    private void createGUI() {
        setTitle("타자 연습 프로그램");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(1, 4));
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> {
            timer.stop();
            new MainScreen().setVisible(true);
            this.dispose();
        });

        accuracyField = new JTextField("정확도: 0%");
        currentTypingSpeedField = new JTextField("현재 타수: 0타/분");
        timeField = new JTextField("시간: 0초");

        accuracyField.setEditable(false);
        currentTypingSpeedField.setEditable(false);
        timeField.setEditable(false);

        infoPanel.add(backButton);
        infoPanel.add(accuracyField);
        infoPanel.add(currentTypingSpeedField);
        infoPanel.add(timeField);

        add(infoPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        sentenceFields = new JTextField[5];
        inputFields = new JTextField[5];

        for (int i = 0; i < 5; i++) {
            sentenceFields[i] = new JTextField("문장 출력창");
            inputFields[i] = new JTextField();

            sentenceFields[i].setEditable(false);

            int index = i;
            inputFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (index + 1 < inputFields.length) {
                            inputFields[index + 1].requestFocus();
                        } else {
                            processInputs();
                        }
                    } else {
                        updateStats();
                    }
                }
            });

            contentPanel.add(sentenceFields[i]);
            contentPanel.add(inputFields[i]);
        }

        add(contentPanel, BorderLayout.CENTER);
    }

    private void updateSentences() {
        for (int i = 0; i < 5; i++) {
            if (currentIndex + i < lines.size()) {
                sentenceFields[i].setText(lines.get(currentIndex + i));
                inputFields[i].setText("");
            } else {
                sentenceFields[i].setText("");
                inputFields[i].setText("");
            }
        }
        inputFields[0].requestFocusInWindow();
    }

    private void startTimers() {
        timer = new Timer(1000, e -> {
            calculator.updateElapsedTime();
            timeField.setText("시간: " + calculator.getElapsedTime() + "초");
            accuracyField.setText(String.format("정확도: %.2f%%", calculator.getAccuracy()));
            currentTypingSpeedField.setText("현재 타수: " + calculator.getTypingSpeed() + "타/분");
        });
        timer.start();
    }

    private void processInputs() {
        for (int i = 0; i < 5; i++) {
            if (currentIndex + i < lines.size()) {
                String expected = lines.get(currentIndex + i);
                String userInput = inputFields[i].getText();

                int correctCount = calculateCorrectChars(expected, userInput);
                calculator.addCorrectChars(correctCount);
                calculator.addInputChars(userInput.length());
            }
        }

        currentIndex += 5;

        if (currentIndex >= lines.size()) {
            timer.stop();
            calculator.updateElapsedTime();
            showResults();
        } else {
            updateSentences();
        }
    }

    private void updateStats() {
        int tempCorrectChars = 0;
        int tempTotalInputChars = 0;

        // 각 입력 필드의 데이터를 기반으로 계산
        for (int i = 0; i < 5; i++) {
            if (currentIndex + i < lines.size()) {
                String expected = lines.get(currentIndex + i);
                String userInput = inputFields[i].getText();

                tempCorrectChars += calculateCorrectChars(expected, userInput);
                tempTotalInputChars += userInput.length();
            }
        }

        calculator.updateStats(tempCorrectChars, tempTotalInputChars);

        accuracyField.setText(String.format("정확도: %.2f%%", calculator.getAccuracy()));
        currentTypingSpeedField.setText("현재 타수: " + calculator.getTypingSpeed() + "타/분");
    }

    private int calculateCorrectChars(String expected, String userInput) {
        int correct = 0;
        for (int i = 0; i < Math.min(expected.length(), userInput.length()); i++) {
            if (expected.charAt(i) == userInput.charAt(i)) {
                correct++;
            }
        }
        return correct;
    }

    private void showResults() {
        long totalTime = calculator.getElapsedTime();
        double finalAccuracy = calculator.getAccuracy();
        int finalTypingSpeed = calculator.getTypingSpeed();

        new ResultScreen((int) totalTime, finalAccuracy, finalTypingSpeed).setVisible(true);
        this.dispose();
    }
}
