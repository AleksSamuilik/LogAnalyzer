package it.alex.analyzer.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine implements LogAnalysis {

    private final Pattern LOG_PATTERN = Pattern.compile("(?<date>\\d{4}\\/\\d{2}\\/\\d{2}).+(?<type>\\[\\w+\\])\\s+(?<user>[a-zA-z0-9]+).+\\s-\\s(?<msg>.+)");
    private List<String> argsList;
    private List<String> findedArgument;
    private List<String> dateArgs;
    private List<String> userArgs;
    private List<String> msgArgs;

    public SearchEngine() {
    }

    public SearchEngine(List argsList) {
        this.argsList = argsList;
    }

    @Override
    public void initialArguments() throws ArgumentsException {
        findedArgument = new ArrayList<>();
        dateArgs = new ArrayList<>();
        userArgs = new ArrayList<>();
        msgArgs = new ArrayList<>();
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains("-d-")) {
                dateArgs.add(input.replaceAll("-d-", ""));
            } else if (input.contains("-n-")) {
                userArgs.add(input.replaceAll("-n-", ""));
            } else if (input.contains("-p-")) {

                StringBuilder builder = new StringBuilder();
                builder.append(input.replaceAll("-p-", ""));

                while (iterator.hasNext()) {
                    input = (String) iterator.next();
                    builder.append(" ");
                    builder.append(input);
                }
                msgArgs.add(builder.toString());
            }
        }
        if (dateArgs.size() == 0 && userArgs.size() == 0 && msgArgs.size() == 0) {
            throw new ArgumentsException("Incorrect arguments.");
        }
    }

    private String check(String nameGroup, Matcher m, List argsList) {
        for (int i = 0; i < argsList.size(); i++) {
            if (m.group(nameGroup).contains((CharSequence) argsList.get(i))) {
                return (String) argsList.get(i);
            }
        }
        return "";
    }

    @Override
    public synchronized boolean isValid(String input) {
        if (!findedArgument.isEmpty()) {
            findedArgument.clear();
        }
        boolean isDate = true;
        boolean isUser = true;
        boolean isMSG = true;

        Pattern p = LOG_PATTERN;
        Matcher m = p.matcher(input);

        if (m.find()) {
            if (dateArgs.size() != 0) {
                String line = check("date", m, dateArgs);
                if (line.equals("")) {
                    isDate = false;
                } else {
                    findedArgument.add(line);
                    isDate = true;
                }
            }
            if (userArgs.size() != 0) {
                String line = check("user", m, userArgs);
                if (line.equals("")) {
                    isUser = false;
                } else {
                    findedArgument.add(line);
                    isUser = true;
                }
            }
            if (msgArgs.size() != 0) {
                String line = check("msg", m, msgArgs);
                if (line.equals("")) {
                    isMSG = false;
                } else {
                    findedArgument.add(line);
                    isMSG = true;
                }
            }
        }
        if (isDate && isUser && isMSG) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List getArguments() {
        List<String> list = new ArrayList();
        ((ArrayList<String>) list).ensureCapacity(dateArgs.size() + userArgs.size() + msgArgs.size());
        list.addAll(dateArgs);
        list.addAll(userArgs);
        list.addAll(msgArgs);
        return list;
    }

    @Override
    public synchronized List<String> getFindedArgument() {
        return findedArgument;
    }
}


