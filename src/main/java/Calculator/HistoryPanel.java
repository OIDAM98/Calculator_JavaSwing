package Calculator;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel extends JPanel{

    private LogHistory log;
    private JTextArea textLog;
    private JTextField title;

    public HistoryPanel(){
        setLayout(new BorderLayout());
        log = new LogHistory();


        textLog = new JTextArea();
        textLog.setFont(new Font("Courier new", Font.ITALIC,20));
        textLog.setForeground(Color.WHITE);
        textLog.setBackground(Color.DARK_GRAY);
        textLog.setEditable(false);
        textLog.setPreferredSize(new Dimension(300, 100));

        //JScrollPane scrollBar = new JScrollPane(textLog);
        //scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(textLog, BorderLayout.CENTER);

        title = new JTextField("History");
        title.setFont(new Font("Courier new", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);
        title.setEditable(false);
        title.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(title, BorderLayout.NORTH);
    }

    public void addOperation(String[] op, double result){
        log.addOperation(op, result);
    }

    public void updateLog(){
        String[] toAdd = log.getLogHistory();
        StringBuilder sb = new StringBuilder();
        for(String op : toAdd){
            sb.append(op);
        }
        textLog.setText(sb.toString());
    }

    public void resetLog(){
        log.resetLog();
        textLog.setText("");
    }

}
