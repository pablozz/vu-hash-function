public class HashGenerator {

    public final int HASH_LENGTH = 64;

    private String stringToHash(String input) {
        int hash = 0;

        for (int i = 0; i < input.length(); i++) {
            hash = (input.charAt(i)) + (hash << 5) + (hash << 15);
        }

        return  Integer.toHexString(hash);
    }

    public String getHash(String input) {
        StringBuilder hashBuilder = new StringBuilder();

        int counter = 0;
        for (int i = 0; i < HASH_LENGTH; i++) {
            String hash = stringToHash(input);

            if (counter < hash.length()) {
                hashBuilder.append(hash.charAt(counter));
                counter++;
            } else {
                counter = 0;
                hashBuilder.append(stringToHash(input).charAt(counter));
            }
        }

        return hashBuilder.toString();
    }
}
