import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileReader {

    private String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name:");

        return scanner.nextLine();
    }

    public String readStringFromFile(String[] args) {
        String filePath = args.length == 1 ? args[0] : readUserInput();

        try {
            return Files.readString(Path.of("./inputs/" + filePath));
        } catch (IOException e) {
            System.out.println("Couldn't read this file");
        }

        return "";
    }
}
