package it.alex.analyzer.analysis;

public class FindUser implements DataAnalysis {
    @Override
    public String getNameOperation() {
        return "analysis by user name";
    }

    @Override
    public String analyze(String inputLine) {
        return "HAHAHA";
    }
}
