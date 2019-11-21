package it.alex.analyzer.analysis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine implements LogAnalysis {

    private Map<Integer, String> arguments = new HashMap<>();
    private final Pattern LOG_PATTERN = Pattern.compile("(\\d{4}\\/\\d{2}\\/\\d{2}).+(\\[\\w+\\])\\s+([a-zA-z0-9]+).+\\s-\\s(.+)");
    private final int NUMBER_GROUP_DATE = 1;
    private final int NUMBER_GROUP_TYPE = 2;
    private final int NUMBER_GROUP_NAME = 3;
    private final int NUMBER_GROUP_MESSAGE = 4;
    private final List<String> argsList;

    public SearchEngine(List argsList) {
        this.argsList = argsList;
    }

    @Override
    public void initialArguments() throws ArgumentsException {
        Iterator iterator = argsList.iterator();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            if (input.contains("-d-")) {
                arguments.put(NUMBER_GROUP_DATE, input.replaceAll("-d-", ""));
            } else if (input.contains("-n-")) {
                arguments.put(NUMBER_GROUP_NAME, input.replaceAll("-n-", ""));
            } else if (input.contains("-p-")) {
                StringBuilder builder = new StringBuilder();
                builder.append(input.replaceAll("-p-", ""));
                while (iterator.hasNext()) {
                    input = (String) iterator.next();
                    builder.append(" ");
                    builder.append(input);
                }
                arguments.put(NUMBER_GROUP_MESSAGE, builder.toString());
            }
        }
        if (arguments.size() == 0) {
            throw new ArgumentsException("Incorrect arguments.");
        }
    }

    @Override
    public boolean isValid(String input) {
        if (arguments.size() == 0) {
            return false;
        }
        Pattern p = LOG_PATTERN;
        Matcher m = p.matcher(input);
        boolean isValid = false;
        if (m.find()) {
            for (Map.Entry entry : arguments.entrySet()) {
                if (m.group((Integer) entry.getKey()).contains((String) entry.getValue())) {
                    isValid = true;
                } else {
                    return false;
                }
            }
        }
        return isValid;
    }
}


