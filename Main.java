import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(330, 420);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));

        JTextField display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.CENTER);
        display.setPreferredSize(new Dimension(300, 60));
        topPanel.add(display, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(8,8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel left = new JPanel(new GridLayout(4,3,8,8));
        String[] leftKeys = {"1","2","3","4","5","6","7","8","9",".","0","="};
        for (String k : leftKeys) left.add(new JButton(k));

        JPanel right = new JPanel(new GridLayout(5,1,0,6));
        String[] rightKeys = {"C","+","-","*","/"};
        for (String k : rightKeys) right.add(new JButton(k));

        centerPanel.add(left, BorderLayout.CENTER);
        centerPanel.add(right, BorderLayout.EAST);

        frame.setLayout(new BorderLayout(8,8));
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        final double[] first = {0};
        final String[] operator = {null};
        final boolean[] newNumber = {true};

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd = ((JButton)e.getSource()).getText();

                if ("0123456789".contains(cmd)) {
                    if (newNumber[0]) {
                        display.setText(cmd);
                        newNumber[0] = false;
                    } else {
                        display.setText(display.getText().equals("0") ? cmd : display.getText() + cmd);
                    }
                } else if (cmd.equals(".")) {
                    if (newNumber[0]) {
                        display.setText("0.");
                        newNumber[0] = false;
                    } else if (!display.getText().contains(".")) {
                        display.setText(display.getText() + ".");
                    }
                } else if (cmd.equals("C")) {
                    display.setText("0");
                    first[0] = 0;
                    operator[0] = null;
                    newNumber[0] = true;
                } else if (cmd.equals("=")) {
                    if (operator[0] != null) {
                        double second = Double.parseDouble(display.getText());
                        double r = 0;
                        if (operator[0].equals("+")) r = first[0] + second;
                        if (operator[0].equals("-")) r = first[0] - second;
                        if (operator[0].equals("*")) r = first[0] * second;
                        if (operator[0].equals("/")) r = second == 0 ? 0 : first[0] / second;
                        display.setText(r == (long) r ? String.valueOf((long) r) : String.valueOf(r));
                        operator[0] = null;
                        newNumber[0] = true;
                    }
                } else {
                    if (newNumber[0] && cmd.equals("-")) {
                        display.setText("-");
                        newNumber[0] = false;
                        return;
                    }

                    double current = Double.parseDouble(display.getText());
                    if (operator[0] == null) {
                        first[0] = current;
                    } else {
                        if (operator[0].equals("+")) first[0] += current;
                        if (operator[0].equals("-")) first[0] -= current;
                        if (operator[0].equals("*")) first[0] *= current;
                        if (operator[0].equals("/")) first[0] = current == 0 ? 0 : first[0] / current;
                        display.setText(first[0] == (long) first[0] ? String.valueOf((long) first[0]) : String.valueOf(first[0]));
                    }
                    operator[0] = cmd;
                    newNumber[0] = true;
                }
            }
        };

        for (Component c : left.getComponents()) if (c instanceof JButton) ((JButton)c).addActionListener(listener);
        for (Component c : right.getComponents()) if (c instanceof JButton) ((JButton)c).addActionListener(listener);

        frame.setVisible(true);
    }
}
