import utils.FileReader;

public class Main {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();

        String fileContent = fileReader.readStringFromFile(args);

        System.out.println(fileContent);
    }
}
