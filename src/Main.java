public class Main {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        String fileContent = fileReader.readStringFromFile(args);

        HashGenerator hashGenerator = new HashGenerator();
        String hash = hashGenerator.getHash(fileContent);

        System.out.println(hash);
    }
}
