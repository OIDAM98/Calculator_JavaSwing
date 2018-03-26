package Calculator;

import java.util.ArrayList;
import java.util.List;

public class LogHistory {
    private StringBuilder log;
    private List<String> logList;

    public LogHistory(){
      log = new StringBuilder();
      logList = new ArrayList<>();
    }

    public void addOperation(String[] operation, double result){
        for(String check : operation){
            log.append(check);
            log.append(" ");
        }
        log.append("= ");
        log.append(result);
        log.append("\n");
        logList.add(log.toString());
        log = new StringBuilder();
    }

    public String[] getLogHistory(){
        return logList.toArray(new String[logList.size()]);
    }

    public void resetLog(){
        log = new StringBuilder();
        logList = new ArrayList<>();
    }

}
