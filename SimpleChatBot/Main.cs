using System;
using System.Collections.Generic;
using System.Linq;

public class MainBot
{
    private static readonly string REating = "I don't like eating anything because I'm a bot obviously!";
    private static readonly string RAdvice = "If I were you, I would go to the internet and type exactly what you wrote there!";

    public static int MessageProbability(string[] userMessage, string[] recognisedWords, bool singleResponse, string[] requiredWords)
    {
        int messageCertainty = 0;
        bool hasRequiredWords = true;

        // Counts how many words are present in each predefined message
        foreach (string word in userMessage)
        {
            if (Contains(recognisedWords, word))
            {
                messageCertainty++;
            }
        }

        // Calculates the percent of recognized words in a user message
        float percentage = (float)messageCertainty / (float)recognisedWords.Length;

        // Checks that the required words are in the string
        foreach (string word in requiredWords)
        {
            if (!Contains(userMessage, word))
            {
                hasRequiredWords = false;
                break;
            }
        }

        // Must either have the required words, or be a single response
        if (hasRequiredWords || singleResponse)
        {
            return (int)(percentage * 100);
        }
        else
        {
            return 0;
        }
    }

    public static string CheckAllMessages(string[] message)
    {
        Dictionary<string, int> highestProbList = new Dictionary<string, int>();

        // Simplifies response creation / adds it to the dict
        Action<string, string[], bool, string[]> response = (botResponse, listOfWords, singleResponse, requiredWords) =>
        {
            highestProbList.Add(botResponse, MessageProbability(message, listOfWords, singleResponse, requiredWords));
        };

        // Responses
        // -------------------------------------------------------------------------------------------------------
        response.Invoke("Hello!", new[] { "hello", "hi", "hey", "sup", "heyo" }, true, new string[] { });
        response.Invoke("See you!", new[] { "bye", "goodbye" }, true, new string[] { });
        response.Invoke("I'm doing fine, and you?", new[] { "how", "are", "you", "doing" }, false, new[] { "how" });
        response.Invoke("You're welcome!", new[] { "thank", "thanks" }, true, new string[] { });
        response.Invoke("Thank you!", new[] { "i", "love", "code", "palace" }, false, new[] { "code", "palace" });
        response.Invoke(RAdvice, new[] { "give", "advice" }, false, new[] { "advice" });
        response.Invoke(REating, new[] { "what", "you", "eat" }, false, new[] { "you", "eat" });

        string bestMatch = highestProbList.OrderByDescending(x => x.Value).FirstOrDefault().Key;

        return (highestProbList[bestMatch] < 1) ? Unknown() : bestMatch;
    }

    private static string Unknown()
    {
        string[] responses = {
            "Could you please re-phrase that? ",
            "...",
            "Sounds about right.",
            "What does that mean?",
            "I was unable to understand. Can you explain me again?"
        };

        return responses[new Random().Next(4)];
    }

    private static bool Contains(string[] array, string element)
    {
        return array.Contains(element);
    }

    public static void Main(string[] args)
    {
        Console.WriteLine("Megumin: Hi there! Type 'exit' to end the conversation.");

        // Testing the response system
        while (true)
        {
            Console.Write("You: ");
            string userInput = Console.ReadLine();

            if (userInput.ToLower() == "exit")
            {
                break; // exit the loop if "exit" is entered
            }

            Console.WriteLine("Megumin: " + CheckAllMessages(userInput.ToLower().Split(new[] { ' ', ',', ';', '?', '!', '.', '-' }, StringSplitOptions.RemoveEmptyEntries)));
        }
    }
}
