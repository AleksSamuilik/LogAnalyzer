package it.alex.analyzer.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserFilter implements LogFilter {
    private List<String> argsList;
    private final String SYMBOL = "-n-";
    private final String NAME_GROUP = "user";
    private List<String> userArgs;
    private boolean status = true;


    public UserFilter(List<String> argsList) {
        this.argsList = argsList;
        createUserArgs();
    }

    private void createUserArgs() {
        userArgs = new ArrayList<>();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains(SYMBOL)) {
                userArgs.add(input.replaceAll(SYMBOL, ""));
            }
        }
    }

    @Override
    public boolean isEmptyArgsList() {
        if (userArgs.size() == 0) {
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
        return userArgs;
    }
}