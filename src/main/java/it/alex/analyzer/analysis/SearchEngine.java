package it.alex.analyzer.analysis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine implements LogAnalysis {

    private Map<String, Integer> arguments = new HashMap<>();
    private final Pattern LOG_PATTERN = Pattern.compile("(\\d{4}\\/\\d{2}\\/\\d{2}).+(\\[\\w+\\])\\s+([a-zA-z0-9]+).+\\s-\\s(.+)");
    private final int NUMBER_GROUP_DATE = 1;
    private final int NUMBER_GROUP_TYPE = 2;
    private final int NUMBER_GROUP_NAME = 3;
    private final int NUMBER_GROUP_MESSAGE = 4;
    private List<String> argsList;
    private String findedArgument;

    public SearchEngine() {
    }

    public SearchEngine(List argsList) {
        this.argsList = argsList;

    }

    @Override
    public void initialArguments() throws ArgumentsException {
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains("-d-")) {
                arguments.put(input.replaceAll("-d-", ""), NUMBER_GROUP_DATE);
            } else if (input.contains("-n-")) {
                arguments.put(input.replaceAll("-n-", ""), NUMBER_GROUP_NAME);
            } else if (input.contains("-p-")) {

                StringBuilder builder = new StringBuilder();
                builder.append(input.replaceAll("-p-", ""));

                while (iterator.hasNext()) {
                    input = (String) iterator.next();
                    builder.append(" ");
                    builder.append(input);
                }
                arguments.put(builder.toString(), NUMBER_GROUP_MESSAGE);
            }
        }
        if (arguments.size() == 0) {
            throw new ArgumentsException("Incorrect arguments.");
        }

    }

    @Override
    public boolean isValid(String input) {
        long countValidArg = 0;
        if (arguments.size() == 0) {
            return false;
        }
        Pattern p = LOG_PATTERN;
        Matcher m = p.matcher(input);
        if (m.find()) {
            for (Map.Entry entry : arguments.entrySet()) {
                if (m.group((Integer) entry.getValue()).contains((String) entry.getKey())) {
                    findedArgument =(String) entry.getKey();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> getArguments() {
        return arguments;
    }

    @Override
    public String getFindedArgument() {
        return findedArgument;
    }
}


