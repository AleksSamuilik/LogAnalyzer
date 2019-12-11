package it.alex.analyzer.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DateFilter implements LogFilter {
    private List<String> argsList;
    private final String SYMBOL = "-d-";
    private final String NAME_GROUP = "date";
    private List<String> dateArgs;
    private boolean status = true;

    public DateFilter(List<String> argsList) {
        this.argsList = argsList;
        createDateArgs();
    }

    private void createDateArgs() {
        dateArgs = new ArrayList<>();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains("-d-")) {
                dateArgs.add(input.replaceAll("-d-", ""));
            }
        }
    }

    @Override
    public boolean isEmptyArgsList() {
        if (dateArgs.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String nameGroupPattern() {
        return NAME_GROUP;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public List getFilterArgs() {
        return dateArgs;
    }
}