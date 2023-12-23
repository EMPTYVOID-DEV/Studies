package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fileReader {

    public static String readFile(String path) {
        try {
            String result = "";
            File descriptor = new File(path);
            Scanner scan = new Scanner(descriptor);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                result += line;
            }
            scan.close();
            return result;
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }
    }

    public static void writeFile(String data, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.write("\n\n" + data);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
