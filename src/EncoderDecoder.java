import java.util.HashMap;
import java.util.Scanner;

public class EncoderDecoder {

    private static final String referenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
    // To assign index positions to individual Characters 
    // E.g: A:0, B:0, C:2 ... /:43
    private HashMap<Character, Integer> referenceMap; 
    public static void main(String[] args) throws Exception {

        // Instantiate the reference map creation
        EncoderDecoder ed = new EncoderDecoder();

        // Using scanner to get input from user
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter plain text to encode in caps");
            String plainText = sc.nextLine();

            System.out.println("Enter offset character in caps");
            char offsetChar = sc.next().charAt(0);

            String encoded = ed.encode(plainText, offsetChar);
            System.out.println(encoded);

            String decoded = ed.decode(encoded);
            System.out.println(decoded);
        }

    }

    public EncoderDecoder() {
        referenceMap = new HashMap<Character, Integer>();
        // Put the reference table into the reference map 
        for (int i = 0; i < referenceTable.length(); i++) {
            referenceMap.put(referenceTable.charAt(i), i);
        }
    }


    public String encode(String plainText, char offsetChar) {
        StringBuilder encodedText = new StringBuilder();
        int offset = referenceMap.get(offsetChar);

        // Add the offset character in the encoded text as the first character
        encodedText.append(offsetChar);

        // Iterate through plain text and encode each character
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            int index = referenceMap.getOrDefault(c, -1);

            // If the character is not in the reference table, add it to the encoded message
            if (index == -1) {
                encodedText.append(c);
            } else {
                // Apply the negative offset to the index to get encoded character
                int encodedIndex = (index - offset + referenceTable.length()) % referenceTable.length();
                encodedText.append((referenceTable.charAt(encodedIndex)));
            }
        }

        return encodedText.toString();
    }

    public String decode(String encodedText) {
        char offsetChar = encodedText.charAt(0);
        int offset = referenceMap.get(offsetChar);
        StringBuilder plainText = new StringBuilder();

        // Iterate through encoded text and decode each character
        for (int i = 1; i <encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int index = referenceMap.getOrDefault(c, -1);

            // If character is not in reference table, add it to plain text 
            if (index == -1) {
                plainText.append(c);
            } else {
                // Apply the offset to the index to get the original character
                int originalIndex = (index + offset) % referenceTable.length();
                plainText.append(referenceTable.charAt(originalIndex));
            }
        }

        return plainText.toString();
    }

}
