package AGAIN;

import java.util.Scanner;

public class EX2 {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        int sentence_index = scanner.nextInt();
        int questions_index = scanner.nextInt();
        scanner.nextLine();
        String[] sentences = new String[sentence_index];
        String[] questions = new String[questions_index];
        for (int i = 0; i < sentence_index; i++)
            sentences[i] = " " + (scanner.nextLine()).toLowerCase() + " ";
        for (int i = 0; i < questions_index; i++)
            questions[i] = " " + (scanner.nextLine()).toLowerCase() + " ";
        find(sentences, questions);
        scanner.close();
    }

    private static void find(String[] sentences, String[] questions) {
        for (int i = 0; i < questions.length; i++) {
            String[] split_questions_word = questions[i].split("\\s");
            boolean find_at_least_one_sentence = true;
            for (int j = 0; j < sentences.length; j++) {
                int index = 1;
                for (int j2 = 0; j2 < split_questions_word.length; j2++) {
                    if (sentences[j].indexOf(split_questions_word[j2]) == -1)
                        break;
                    if(split_questions_word.length == index) {
                        find_at_least_one_sentence = false;
                        System.out.print(j +1 + " ");
                    }
                    index++;
                }
            }
            if (find_at_least_one_sentence) 
                System.out.print(-1);
            System.out.println();
        }
    }
}
