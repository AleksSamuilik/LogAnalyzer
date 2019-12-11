package it.alex.analyzer.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageFilter implements LogFilter {
    private List<String> argsList;
    private final String SYMBOL = "-p-";
    private final String NAME_GROUP = "msg";
    private List<String> msgArgs;
    private boolean status = true;

    public MessageFilter(List<String> argsList) {
        this.argsList = argsList;
        createMsgArgs();
    }

    private void createMsgArgs() {
        msgArgs = new ArrayList<>();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains(SYMBOL)) {
                StringBuilder builder = new StringBuilder();
                builder.append(input.replaceAll(SYMBOL, ""));
                while (iterator.hasNext()) {
                    input = (String) iterator.next();
                    builder.append(" ");
                    builder.append(input);
                }
                msgArgs.add(builder.toString());
            }
        }
    }

    @Override
    public boolean isEmptyArgsList() {
        if (msgArgs.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String nameGroupPattern() {
        return NAME_GROUP;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean isStatus() {
        return status;
    }

    @Override
    public List getFilterArgs() {
        return msgArgs;
    }
}