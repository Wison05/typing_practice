package ui;

import javax.swing.*;
import java.awt.*;

public class ResultScreen extends JFrame {
    public ResultScreen(int totalTime, double finalAccuracy, int finalTypingSpeed) {
        setTitle("결과 화면");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        JLabel timeLabel = new JLabel("총 시간: " + totalTime + "초");
        JLabel accuracyLabel = new JLabel("평균 정확도: " + String.format("%.2f", finalAccuracy) + "%");
        JLabel typingSpeedLabel = new JLabel("평균 타수: " + finalTypingSpeed + "타/분");

        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accuracyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typingSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statsPanel.add(timeLabel);
        statsPanel.add(accuracyLabel);
        statsPanel.add(typingSpeedLabel);
        add(statsPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("뒤로 가기");
        backButton.addActionListener(e -> {
            new MainScreen().setVisible(true);
            this.dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
}