package it.alex.analyzer.analysis;

import java.util.List;

public interface LogFilter {

    boolean isEmptyArgsList();

    String nameGroupPattern();

    void setStatus(boolean status);

    boolean isStatus();

    List getFilterArgs();
}
