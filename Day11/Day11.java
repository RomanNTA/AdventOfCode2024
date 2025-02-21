package cz.sliva.Day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {

    private String inpString = "5688 62084 2 3248809 179 79 0 172169";
    //private String inpString = "125 17";
    private int[] buffer;
    private int bufferLength = 0;

    public static void main(String[] args) {
        Day11 t = new Day11();
        t.init();
        t.run();
    }

    // 186175  - OK !!!

    public void run() {
        System.out.println("Start.");

        String[] inp = inpString.split(" ");
        List<String> result = Arrays.stream(inp).toList();
        int i = 0;
        String q;
        while (i++ < 25) {
            ArrayList<String> tmp = new ArrayList<>();
            for (String s : result) {
                if ("0".equals(s)) {
                    tmp.add("1");
                    continue;
                }
                if ("1".equals(s) ) {
                    tmp.add("2024");
                    continue;
                }
                if (s.length() % 2 == 0) {
                    tmp.add(String.valueOf(Long.parseLong(s.substring(0, s.length() / 2))));
                    tmp.add(String.valueOf(Long.parseLong(s.substring(s.length() / 2, s.length()))));
                    continue;
                }
                tmp.add(String.valueOf(Long.parseLong(s) * 2024));
            }
            result = new ArrayList<>();
            result.addAll(tmp);
        }
        System.out.println("Celkem kamenů: " + result.size() + ". Správně je = " +  55312);
    }

    private static void init() {
        System.out.println("-".repeat(60));
        System.out.println(" ADVENT OF CODE");
        System.out.println("-".repeat(60));
        System.out.println(" https://adventofcode.com/2024/day/11");
        System.out.println(" Day 11, first part");
        System.out.println(" autor: Roman Sliva");
        System.out.println(" portfolio: sliva-roman.cz");
        System.out.println("-".repeat(60));
        System.out.println(" IntelliJ Community Edition 2023.3.3 Java 17 SDK");
        System.out.println("-".repeat(60));
    }

}
