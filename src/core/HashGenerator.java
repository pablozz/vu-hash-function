package core;

public class HashGenerator {

    public final int HASH_LENGTH = 64;

    public String getHash(String input) {
        int hashInt = 0;

        for (int inputElem : input.toCharArray()) {
            hashInt = stirChar(hashInt, inputElem);
        }

        StringBuilder hashBuilder = new StringBuilder(Integer.toHexString(hashInt));

        while (hashBuilder.length() != HASH_LENGTH) {
            for (int hashElem : hashBuilder.toString().toCharArray()){
                hashInt = stirChar(hashInt, hashElem);

                hashBuilder.append(Integer.toHexString(hashInt));
            }

            int hashLength = hashBuilder.length();
            if (hashLength > HASH_LENGTH) {
                hashBuilder.delete(HASH_LENGTH, hashLength);
            }
        }

        return  hashBuilder.toString();
    }

    private int stirChar(int hashInt, int elem) {
        hashInt += Math.pow(elem, 3);
        hashInt += ~elem % ~hashInt;
        hashInt += ~hashInt << ~hashInt << 1;

        return hashInt;
    }
}