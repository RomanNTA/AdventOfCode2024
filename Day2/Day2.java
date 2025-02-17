package cz.sliva.Day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day2 {

    String FILE_PATH = "src/main/java/cz/sliva/Day2/";
    String FILE_NAME = "input.txt";
    List<String> rows = new ArrayList<>();
    private Integer totalSum = 0;

    public static void main(String[] args) {
        (new Day2()).run();
    }

    public void run(){
        System.out.printf("\r\nSTART.\r\n\r\n");
        if (!loadFile()){
            return;
        }
        process();
        System.out.printf("Result %d \r\n\r\n", this.totalSum);
    }

    private void process(){

        rows.forEach(s -> {
            String[] inp = s.split(" ");
            Integer[] np = Arrays.stream(inp)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);

            boolean b = true;
            if (np[0] < np[1]){
                // pole je vzestupné
                for (int i = 0; i < np.length-1; i++) {
                    if (!(np[i+1] - np[i] > 0 && np[i+1] - np[i] <= 3)) {
                        b = false;
                        break;
                    }
                }
                this.totalSum += b ? 1 : 0;

            } else {
                // pole je sestupné
                for (int i = 0; i < np.length-1; i++) {
                    if (!(np[i] - np[i+1] > 0 && np[i] - np[i+1] <= 3)) {
                        b = false;
                        break;
                    }
                }
                this.totalSum += b ? 1 : 0;
            }
        });
    }

    public boolean loadFile() {

        Path file = FileSystems.getDefault().getPath(FILE_PATH,FILE_NAME);
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            rows = lines.toList();
        } catch (IOException e) {
            System.out.println("Error : Load file.");
            return false ;
        }
        return true;
    }
}
