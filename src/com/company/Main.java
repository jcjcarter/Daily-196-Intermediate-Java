package com.company;

public class Main {

    public static String encrypt(int key, String message)
    {
        String[] lines = splitIntoLines(key, message, false);
        String result = "";
        for (String line : lines)
        {
            result += line;
        }
        return result;
    }

    public static String decrypt(int key, String message)
    {
        String[] lines = splitIntoLines(key, message, true);

        /* replace ?s with the actual letter */
        int charCount = 0;
        for (int i = 0; i < key; ++i)
        {
            while (lines[i].contains("?"))
            {
                String letter = String.valueOf(message.charAt(charCount));
                lines[i] = lines[i].replaceFirst("\\?", letter);
                charCount++;
            }
        }

        /* condense zig-zag array into normal string */
        String result = "";
        int lineCount = 0;
        int direction = -1;
        for (int i = 0; i < message.length(); ++i)
        {
            /* Add first letter to result by removing it from the line */
            String letter = String.valueOf(lines[lineCount].charAt(0));
            lines[lineCount] = lines[lineCount].substring(1);
            result += letter;
            direction *= lineCount == 0 || lineCount == key - 1 ? -1 : 1;
            lineCount += direction;
        }
        return result;
    }

    private static String[] splitIntoLines(int numLines, String message, boolean encrypted)
    {
        String[] result = generateEmptyStrings(numLines);
        int lineCount = 0;
        int direction = -1;
        for (char c : message.toCharArray())
        {
            String letter = String.valueOf(c);
            /* if the message is already encrypted, use '?' as placeholder */
            result[lineCount] += encrypted ? "?" : letter;
            direction *= lineCount == 0 || lineCount == numLines - 1 ? -1 : 1;
            lineCount += direction;
        }
        return result;
    }

    private static String[] generateEmptyStrings(int num)
    {
        String[] strings = new String[num];
        for (int i = 0; i < num; ++i)
        {
            strings[i] = "";
        }
        return strings;
    }

    public static void main(String[] args)
    {
        String instruction = args[0].toLowerCase();
        int key = Integer.valueOf(args[1]);
        String message = args[2];
        if (instruction.equals("enc"))
        {
            System.out.println(Main.encrypt(key, message));
        }
        else if (instruction.equals("dec"))
        {
            System.out.println(Main.decrypt(key, message));
        }
        else
        {
            System.err.println("unknown option \"" + args[0] + "\"");
        }
    }
}
