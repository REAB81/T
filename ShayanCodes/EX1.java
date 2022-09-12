package ShayanCodes;

import java.util.*;
import java.io.*;
public class EX1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String status = scanner.nextLine();
        String all = "";
        while (scanner.hasNextLine()) {
            all += scanner.nextLine() + " ";
        }
        String[] words = all.split("\\s");
        int lengthOfLine;
        List<String> result = new ArrayList<>();
        if (status.charAt(0) == 'J') {
            status = status.replace("J", "");
            lengthOfLine = Integer.parseInt(status);
            result = mainFunctionForJustifaction(words, lengthOfLine);
        }
        else {
            status = status.replace("L", "");
            lengthOfLine = Integer.parseInt(status);
            result = mainFunctionForLeftAlign(words, lengthOfLine);
        }
        for (String i : result) {
            System.out.println(i);
        }
        scanner.close();
    }

    static List<String> mainFunctionForJustifaction(String[] words, int lengthOfLine) {
        List<String> result = new ArrayList<>();
        int thisLine = 0;
        int nextLine = wordsForEachLine(words, lengthOfLine, thisLine);
        while (thisLine < words.length) {
            String everyLine = "";
            for (int i = thisLine; i < nextLine; i++)
                everyLine = everyLine.concat(words[i] + " ");
            thisLine = nextLine;
            nextLine = wordsForEachLine(words, lengthOfLine, thisLine);
            result.add(everyLine);
        }
        for (int i = 0; i < result.size() - 1; i++) {
            String justifiedLine = spaceHandling(result.get(i).trim(), lengthOfLine);
            result.remove(i);
            result.add(i, justifiedLine);
        }
        String lastLineString = lastLineHandling(result.get(result.size() - 1).trim(), lengthOfLine);
        result.remove(result.size() - 1);
        result.add(lastLineString);
        return result;
    }

    static int wordsForEachLine(String[] words, int lengthOfLine, int thisLine) {
        int currentLength = 0;
        while(thisLine < words.length && currentLength < lengthOfLine)
            currentLength += words[thisLine++].length() + 1;

        if (currentLength >= lengthOfLine + 2)
            thisLine--;
        return thisLine;
    }

    static String spaceHandling(String line, int lengthOfLine) {
        String justifiedLine = "";
        String[] words = line.split(" ");
        int currentLength = 0;
        for (String eachWord : words)
            currentLength += eachWord.length();
        int remainingLength = lengthOfLine - currentLength;
        int spaceDividing;
        int remainingTimesTwo;
        if (words.length >= 2) {
            spaceDividing = remainingLength / (words.length - 1);
            remainingTimesTwo = remainingLength % (words.length - 1);
        }
        else {
            spaceDividing = remainingLength;
            remainingTimesTwo = 0;
        }
        for (int i = 0; i < words.length - 1; i++) {
            justifiedLine = justifiedLine.concat(words[i]);
            for (int j = 0; j < spaceDividing; j++)
                justifiedLine = justifiedLine.concat(" ");
            if (remainingTimesTwo > 0) {
                justifiedLine = justifiedLine.concat(" ");
                remainingTimesTwo--;
            }
        }
        justifiedLine = justifiedLine.concat(words[words.length - 1]);
        while (remainingTimesTwo > 0) {
            justifiedLine = justifiedLine.concat(" ");
            remainingTimesTwo--;
        }
        return justifiedLine;
    }

    static String lastLineHandling(String line, int lengthOfLine) {
        int currentLength = line.length();
        String lastLineString = line;
        for (int i = 0; i < lengthOfLine - currentLength; i++)
            lastLineString = lastLineString.concat(" ");
        return lastLineString;
    }

static List<String> mainFunctionForLeftAlign(String[] words, int lengthOfLine) {
        List<String> result = new ArrayList();
        int thisLine = 0;
        int nextLine = wordsForEachLine(words, lengthOfLine, thisLine);
        while (thisLine < words.length) {
            String everyLine = "";
            for (int i = thisLine; i < nextLine; i++)
                everyLine = everyLine.concat(words[i] + " ");
            thisLine = nextLine;
            nextLine = wordsForEachLine(words, lengthOfLine, thisLine);
            result.add(everyLine); 
        }
        for (int i = 0; i < result.size(); i++) {
            String leftAlignedLine = lastLineHandling(result.get(i).trim(), lengthOfLine);
            result.remove(i);
            result.add(i, leftAlignedLine);
        }
        return result;
    }
}