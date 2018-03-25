package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI extends JFrame{

    JTextField resultScreen;
    JTextField opScreen;
    Calculator calculator;
    StringBuilder operation;
    StringBuilder numberStr;
    private final int sButton = 45;
    List<JButton> buttons;
    private static ActionListener actionNumbers;
    private static HashMap<Integer, String> opMap;

    public GUI(){
        setTitle("Calculator");
        setSize(300,425);
        setLocation(600,200);
        setMinimumSize(new Dimension(300, 425));

        Container cp = getContentPane();
        cp.setBackground(Color.BLACK);
        cp.setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5));
        buttonPanel.setBackground(Color.BLACK);

        JPanel opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(4, 1, 5, 5));
        opPanel.setBackground(Color.BLACK);

        /*
            Initializing attributes
         */

        calculator = new Calculator();
        generateMap();
        buttons = new ArrayList<>();
        numberStr = new StringBuilder();
        operation = new StringBuilder();
        resultScreen = new JTextField();

        /*
            Initializing Text Fields
         */

        opScreen = new JTextField();
        opScreen.setHorizontalAlignment(JTextField.RIGHT);
        opScreen.setForeground(Color.WHITE);
        opScreen.setBackground(Color.DARK_GRAY);
        opScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        opScreen.setEditable(false);
        opScreen.setPreferredSize(new Dimension(300, 25));
        opScreen.setFont(new Font("Courier new", Font.ITALIC,16));

        resultScreen = new JTextField();
        resultScreen.setHorizontalAlignment(JTextField.RIGHT);
        resultScreen.setBackground(Color.DARK_GRAY);
        resultScreen.setForeground(Color.WHITE);
        resultScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        resultScreen.setEditable(false);
        resultScreen.setPreferredSize(new Dimension(300, 50));
        resultScreen.setFont(new Font("Courier new", Font.BOLD,20));

        //topresultScreen.setFont(new Font("Arial", 18));

        north.add(opScreen, BorderLayout.NORTH);
        north.add(resultScreen, BorderLayout.SOUTH);
        cp.add(north, BorderLayout.NORTH);

        /*
            Generating Action Listeners for each type of JButton
         */

        actionNumbers = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pressed = (JButton) e.getSource();
                String text = pressed.getText();
                numberStr.append(text);
                resultScreen.setText(numberStr.toString());
            }
        };

        ActionListener actionDot = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numberStr.length() != 0) {
                    numberStr.append(".");
                }
                else{
                    numberStr.append("0.");
                }
                resultScreen.setText(numberStr.toString());
            }
        };

        ActionListener actionEquals = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numberStr.length() != 0) {
                    operation.append(numberStr);
                    numberStr = new StringBuilder();
                    if (!calculator.endsWithOperator(operation.toString())) {
                        try {
                            numberStr.append(calculator.makeOperation(operation.toString()));
                            opScreen.setText("");
                            resultScreen.setText(numberStr.toString());
                            operation = new StringBuilder();
                            numberStr = new StringBuilder();
                        } catch (ArithmeticException ex) {
                            operation = new StringBuilder();
                            resultScreen.setText(ex.getMessage());
                        }
                    }
                }

            }
        };

        ActionListener actionErase = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberStr = new StringBuilder();
                operation = new StringBuilder();
                opScreen.setText("");
                resultScreen.setText("");
            }
        };

        /*
            Generating Numbers Panel
         */

        buttons = generateButtons();

        JButton erase = new JButton("C");
        erase.setSize(sButton, sButton);
        erase.setForeground(Color.WHITE);
        erase.setBackground(Color.DARK_GRAY);
        erase.addActionListener(actionErase);
        buttons.add(erase);

        JButton zero = new JButton("0");
        zero.setSize(sButton, sButton);
        zero.setForeground(Color.WHITE);
        zero.setBackground(Color.GRAY);
        zero.addActionListener(actionNumbers);
        buttons.add(zero);

        JButton dot = new JButton(".");
        dot.setSize(sButton, sButton);
        dot.setForeground(Color.WHITE);
        dot.setBackground(Color.DARK_GRAY);
        dot.addActionListener(actionDot);
        buttons.add(dot);

        for(JButton addy : buttons){
            System.out.println(addy.getText());
            buttonPanel.add(addy);
        }

        buttons = generateOperations();
        for(JButton addy : buttons){
            System.out.println(addy.getText());
            opPanel.add(addy);
        }

        cp.add(opPanel, BorderLayout.EAST);
        cp.add(buttonPanel, BorderLayout.CENTER);

        JButton equals = new JButton("=");
        equals.setForeground(Color.WHITE);
        equals.setBackground(Color.DARK_GRAY);
        equals.setPreferredSize(new Dimension(300, 45));
        equals.addActionListener(actionEquals);
        cp.add(equals, BorderLayout.SOUTH);


        String lf = "";
        lf = UIManager.getSystemLookAndFeelClassName();
        //lf = UIManager.getCrossPlatformLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lf);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }

    private void generateMap(){
        opMap = new HashMap<>();
        opMap.put(0,"/");
        opMap.put(1, "*");
        opMap.put(2, "-");
        opMap.put(3, "+");
    }

    private List<JButton> generateButtons(){
        List<JButton> toRet = new ArrayList<>();
        for(int i = 1; i <= 9; i++){
            JButton button = new JButton( String.valueOf(i) );
            button.setSize(sButton, sButton);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.GRAY);
            button.addActionListener(actionNumbers);
            toRet.add(button);
        }
        return toRet;
    }

    private List<JButton> generateOperations(){
        ActionListener actionOp = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pressed = (JButton) e.getSource();
                String op = pressed.getText();
                if(numberStr.toString().equals("0.")){
                    numberStr.append("0");
                }
                if(numberStr.length() != 0) {
                    operation.append(numberStr);
                    operation.append(" ");
                    operation.append(op);
                    operation.append(" ");
                    numberStr = new StringBuilder();
                    resultScreen.setText("");
                }
                else if(operation.length() != 0 && calculator.endsWithOperator(operation.toString())){
                    operation.replace(operation.length() - 2, operation.length(), op);
                    operation.append(" ");
                }
                else {
                    operation.append(0);
                    operation.append(" ");
                    operation.append(op);
                    operation.append(" ");
                    resultScreen.setText("");
                }
                opScreen.setText(operation.toString());
            }
        };

        List<JButton> toRet = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            JButton op = new JButton(opMap.get(i));
            op.setPreferredSize(new Dimension(sButton, sButton));
            op.setForeground(Color.WHITE);
            op.setBackground(Color.DARK_GRAY);
            op.addActionListener(actionOp);
            toRet.add(op);
        }

        return toRet;
    }

}
