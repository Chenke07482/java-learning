package org.cxq.learn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cells.add(new Cell("q+" + i, "v+" + i));
        }
        Map<String, String> map = cells.stream().limit(5).
                collect(Collectors.toMap(k -> k.getQualifier(), v -> v.getValue()));
        System.out.println(map);
    }

    static class Cell {
        String qualifier;
        String value;

        public String getQualifier() {
            return qualifier;
        }

        public void setQualifier(String qualifier) {
            this.qualifier = qualifier;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Cell(String qualifier, String value) {
            this.qualifier = qualifier;
            this.value = value;
        }
    }
}
