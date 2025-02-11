package cz.sliva.Day5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.stream;


public class Day5 {

    String FILE_PATH = "src/main/java/cz/sliva/Day5/";
    String FILE_NAME_1 = "input1.txt";
    String FILE_NAME_2 = "input2.txt";

    private Integer totalSum = 0;

    private List<Prvek> pravidla;

    private List<int[]> aktualizace;
    private List<int[]> dobre = new ArrayList<>();


    public static void main(String[] args) {

        Day5 t = new Day5();

        if (t.loadFiles()) {
            System.out.println("START !\r\n");
            t.runTask();
            t.stredniHodnota();
        }
    }

    private boolean jeAvPravidlech(int klic, int hledej) {
        List<Prvek> a = pravidla.stream().filter(x -> (klic == x.a)).toList();
        for (Prvek x : a) {
            if (x.b == hledej) {
                return true;
            }
        }
        return false;
    }

    private void stredniHodnota() {

        int citac = 0;
        while (citac < dobre.size()) {
            int[] p = dobre.get(citac);
            this.totalSum += p[Math.floorDiv(p.length,2)];
            citac++;
        }
        System.out.printf("KONEC: Výsledek je %d.\r\n\r\n\r\n", this.totalSum);
    }

    public void runTask() {

        for (int[] p : aktualizace) {

            int i = 0;
            boolean end = true;

            while (i < p.length - 1) {

                if (jeAvPravidlech(p[i], p[i + 1])) {
                    i++;
                    continue;
                } else {
                    end = false;
                    break;
                }
            }
            if (end) {
                dobre.add(p);
            }
        }
    }


    public boolean loadFiles() {

        Path file1 = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME_1);
        try (Stream<String> lines = Files.lines(file1, StandardCharsets.UTF_8)) {

            pravidla = new ArrayList<Prvek>();

            for (String s : lines.toList()) {
                String[] inp = s.split("[|]");
                pravidla.add(new Prvek(Integer.parseInt(inp[0]), Integer.parseInt(inp[1])));
            }
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }

        Path file2 = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME_2);
        try (Stream<String> lines = Files.lines(file2, StandardCharsets.UTF_8)) {
            aktualizace = new ArrayList<>();
            for (String s : lines.toList()) {
                aktualizace.add(stream(s.split("[,]")).mapToInt(Integer::parseInt).toArray());
            }
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false;
        }
        return true;


    }
}
