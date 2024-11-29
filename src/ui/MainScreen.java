package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {
    private int selectedTextNumber = 1;

    public MainScreen() {
        createGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainScreen::new);
    }

    private void createGUI() {

        setTitle("타자 연습 프로그램");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel mainLabel = new JLabel("타자 연습 프로그램");
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
        mainLabel.setFont(new Font("돋움", Font.BOLD, 36));
        mainLabel.setForeground(Color.BLACK); // 텍스트 색상

        JButton modeButton = createButton("글 선택", e -> onModeButtonClick());
        JButton startButton = createButton("게임 시작", e -> onStartButtonClick());

        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(mainLabel);
        mainPanel.add(Box.createVerticalStrut(150));
        mainPanel.add(modeButton);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(startButton);

        add(mainPanel);

        setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("돋움", Font.PLAIN, 18));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        button.addActionListener(action);
        return button;
    }

    private void onModeButtonClick() {
        String[] options = {"1. 메밀꽃 필무렵", "2. 별헤는 밤", "3. 애국가"};
        String selectedOption = (String) JOptionPane.showInputDialog(
                this,
                "글을 선택하세요:",
                "글 선택",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selectedOption != null) {
            selectedTextNumber = Integer.parseInt(selectedOption.split("/")[0]);
            JOptionPane.showMessageDialog(this, selectedOption + "을(를) 선택하셨습니다.");
        }
    }

    private void onStartButtonClick() {
        PlayScreen playScreen = new PlayScreen(selectedTextNumber);
        playScreen.setVisible(true);
        this.dispose();
    }
}
