import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

class long_responses {

    private static final String R_EATING = "I don't like eating anything because I'm a bot obviously!";
    private static final String R_ADVICE = "If I were you, I would go to the internet and type exactly what you wrote there!";

    public static String unknown() {
        String[] responses = {
            "Could you please re-phrase that? ",
            "...",
            "Sounds about right.",
            "What does that mean?",
            "I was unable to understand. Can you explain me again?"
        };

        Random random = new Random();
        return responses[random.nextInt(4)];
    }

    public static void main(String[] args) {
        // You can test the unknown() function or add more functionality as needed.
    }
}

public class Main {

    private static final String R_EATING = "I don't like eating anything because I'm a bot obviously!";
    private static final String R_ADVICE = "If I were you, I would go to the internet and type exactly what you wrote there!";

    public static int messageProbability(String[] userMessage, String[] recognisedWords, boolean singleResponse,
            String[] requiredWords) {
        int messageCertainty = 0;
        boolean hasRequiredWords = true;

        // Counts how many words are present in each predefined message
        for (String word : userMessage) {
            if (contains(recognisedWords, word)) {
                messageCertainty++;
            }
        }

        // Calculates the percent of recognized words in a user message
        float percentage = (float) messageCertainty / (float) recognisedWords.length;

        // Checks that the required words are in the string
        for (String word : requiredWords) {
            if (!contains(userMessage, word)) {
                hasRequiredWords = false;
                break;
            }
        }

        // Must either have the required words, or be a single response
        if (hasRequiredWords || singleResponse) {
            return (int) (percentage * 100);
        } else {
            return 0;
        }
    }

    public static String checkAllMessages(String[] message) {
        Map<String, Integer> highestProbList = new HashMap<>();

        // Simplifies response creation / adds it to the dict
        ResponseConsumer response = (botResponse, listOfWords, singleResponse, requiredWords) -> highestProbList
                .put(botResponse, messageProbability(message, listOfWords, singleResponse, requiredWords));

        // Responses
        // -------------------------------------------------------------------------------------------------------
        response.accept("Hello!", new String[]{"hello", "hi", "hey", "sup", "heyo"}, true, new String[]{});
        response.accept("See you!", new String[]{"bye", "goodbye"}, true, new String[]{});
        response.accept("I'm doing fine, and you?", new String[]{"how", "are", "you", "doing"}, false, new String[]{"how"});
        response.accept("You're welcome!", new String[]{"thank", "thanks"}, true, new String[]{});
        response.accept("Thank you!", new String[]{"i", "love", "code", "palace"}, false, new String[]{"code", "palace"});
        response.accept(R_ADVICE, new String[]{"give", "advice"}, false, new String[]{"advice"});
        response.accept(R_EATING, new String[]{"what", "you", "eat"}, false, new String[]{"you", "eat"});
        

        // Longer responses
        response.accept(R_ADVICE, new String[] { "give", "advice" }, false, new String[] { "advice" });
        response.accept(R_EATING, new String[] { "what", "you", "eat" }, false, new String[] { "you", "eat" });

        String bestMatch = highestProbList.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        return (highestProbList.get(bestMatch) < 1) ? unknown() : bestMatch;
    }

    public static String getResponse(String userInput) {
        String[] splitMessage = userInput.toLowerCase().split("\\s+|[,;?!.-]\\s*");
        return checkAllMessages(splitMessage);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Testing the response system
        while (true) {
            System.out.print("You: ");
            System.out.println("Megumin: " + getResponse(scanner.nextLine()));
        }
    }

    private static String unknown() {
        String[] responses = {
                "Could you please re-phrase that? ",
                "...",
                "Sounds about right.",
                "What does that mean?",
                "I was unable to understand. Can you explain me again?"
        };

        return responses[(int) (Math.random() * 4)];
    }

    private static boolean contains(String[] array, String element) {
        for (String str : array) {
            if (str.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    private interface ResponseConsumer {
        void accept(String botResponse, String[] listOfWords, boolean singleResponse, String[] requiredWords);
    }
   
}