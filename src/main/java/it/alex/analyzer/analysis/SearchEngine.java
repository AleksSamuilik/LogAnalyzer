package it.alex.analyzer.analysis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine implements LogAnalysis {

    private final Pattern LOG_PATTERN = Pattern.compile("(?<date>\\d{4}\\/\\d{2}\\/\\d{2}).+(?<type>\\[\\w+\\])\\s+(?<user>[a-zA-z0-9]+).+\\s-\\s(?<msg>.+)");
    private List<String> findsArgument;
    @Autowired
    private List<LogFilter> filterList;

    @Override
    public void initialArguments() throws ArgumentsException {
        this.findsArgument = new ArrayList<>();
        if (isValidInputArguments()) {
            throw new ArgumentsException("Incorrect arguments.");
        }
    }

    private boolean isValidInputArguments() {
        int count = 0;
        Iterator iterator = filterList.iterator();
        while (iterator.hasNext()) {
            LogFilter filter = (LogFilter) iterator.next();
            if (filter.isEmptyArgsList()) {
                count++;
            }
        }
        if (count == filterList.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean isValid(String input) {
        if (!findsArgument.isEmpty()) {
            findsArgument.clear();
            Iterator iterator = filterList.iterator();
            while (iterator.hasNext()) {
                LogFilter filter = (LogFilter) iterator.next();
                filter.setStatus(true);
            }
        }

        Matcher m = LOG_PATTERN.matcher(input);

        if (m.find()) {
            Iterator iterator = filterList.iterator();
            while (iterator.hasNext()) {
                LogFilter filter = (LogFilter) iterator.next();
                if (!filter.isEmptyArgsList()) {
                    String line = getMatch(m, filter);
                    if (line.equals("")) {
                        filter.setStatus(false);
                    } else {
                        findsArgument.add(line);
                    }
                }
            }
        }
        if (resultMath()) {
            return true;
        } else {
            return false;
        }
    }

    public String getMatch(Matcher m, LogFilter filter) {
        for (int i = 0; i < filter.getFilterArgs().size(); i++) {
            if (m.group(filter.nameGroupPattern()).contains((CharSequence) filter.getFilterArgs().get(i))) {
                return (String) filter.getFilterArgs().get(i);
            }
        }
        return "";
    }

    private boolean resultMath() {
        int count = filterList.size();
        Iterator iterator = filterList.iterator();
        while (iterator.hasNext()) {
            LogFilter filter = (LogFilter) iterator.next();
            if (filter.isStatus()) {
                count--;
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List getAllArguments() {
        List<String> list = new ArrayList();
        int size = 0;
        for (int i = 0; i < filterList.size(); i++) {
            size += filterList.get(i).getFilterArgs().size();
        }
        ((ArrayList<String>) list).ensureCapacity(size);
        for (int i = 0; i < filterList.size(); i++) {
            list.addAll(filterList.get(i).getFilterArgs());
        }
        return list;
    }

    @Override
    public void setFilterList(List<LogFilter> filterList) {
        this.filterList = filterList;
    }

    @Override
    public synchronized List<String> getFindsArgument() {
        return findsArgument;
    }
}