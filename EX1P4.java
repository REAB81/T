import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EX1P4 {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        HashMap<String, HashMap<String, String>> hashMap = new HashMap<>();
        hashMap = getInput();
        printHashMap(hashMap);

    }

    private static void printHashMap(HashMap<String, HashMap<String, String>> hashMap) {
        String hashmap = "";
        var r = hashMap.keySet().toArray();
        for (int i = 0; i < hashMap.size(); i++) {
            hashmap += "**********\n" + "id: " + r[i] + '\n' + "date: " + hashMap.get(r[i]).get("date").substring(0, 4)
                    + '/' + hashMap.get(r[i]).get("date").substring(4, 6) + '/'
                    + hashMap.get(r[i]).get("date").substring(6) + "\nplace: " + hashMap.get(r[i]).get("address")
                    + "\ninfo: " + hashMap.get(r[i]).get("info");
            if (i + 1 == hashMap.size())
                break;
            hashmap += "\n";

        }
        System.out.println(hashmap);

    }

    private static HashMap<String, HashMap<String, String>> getInput() {
        HashMap<String, HashMap<String, String>> hashMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String t = scanner.nextLine();
        Matcher command = Pattern.compile("\\w+\\{[\\w\\u0022\\-\\. ,=]+}").matcher(t);
        double money = scanner.nextDouble();
        String order;
        int ignore = 0;         
        while (command.find()) {
            if (ignore>0) {
                ignore--;
                continue;
            }
            order = command.group().toString();
            String[] words_command = order.split(",");
            switch (order.substring(0, order.indexOf("{"))) {
                case "newTask":
                    if (checkIsValid(hashMap, words_command)) {
                        hashMap.put(words_command[0].substring(words_command[0].indexOf('"') + 1,
                                words_command[0].lastIndexOf('"')), new HashMap<>());
                        hashMap.put(words_command[0].substring(words_command[0].indexOf('"') + 1,
                                words_command[0].lastIndexOf('"')), ArrangeOrder(words_command));
                    }
                    break;

                    case "changePlace":
                    changeAddress(hashMap, words_command);
                    break;
                    
                    
                    case "deleteTask":
                    if (hashMap.containsKey(words_command[0].substring(words_command[0].indexOf('"')+1,words_command[0].lastIndexOf('"')))) {
                        hashMap.remove(words_command[0].substring(words_command[0].indexOf('"')+1,words_command[0].lastIndexOf('"')));
                    }
                    break;

                    case "ignore":
                    if(words_command[0].substring(words_command[0].indexOf('"')+1,words_command[0].lastIndexOf('"')).matches("\\+?\\d"))
                    ignore = Integer.parseInt(words_command[0].substring(words_command[0].indexOf('"')+1,words_command[0].lastIndexOf('"')));
                    
                    break;

                default:
                    break;
            }
        }

        return hashMap;
    }

    private static void changeAddress(HashMap<String, HashMap<String, String>> hashMap, String[] words_command) {
        System.out.println(words_command[0].substring(words_command[0].indexOf('"')+1, words_command[0].lastIndexOf('"')));
        System.out.println(words_command[1].substring(words_command[1].indexOf('"'), words_command[1].lastIndexOf('"')));

        hashMap.get(words_command[0].substring(words_command[0].indexOf('"')+1, words_command[0].lastIndexOf('"')))
                .replace("address",
                        words_command[1].substring(words_command[1].indexOf('"')+1, words_command[1].lastIndexOf('"')));
    }

    private static HashMap<String, String> ArrangeOrder(String[] words_command) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 1; i < words_command.length; i++) {
            hashMap.put(words_command[i].substring(1, words_command[i].indexOf('"')),
                    words_command[i].substring(words_command[i].indexOf('"') + 1, words_command[i].lastIndexOf('"')));
        }
        return hashMap;
    }

    private static boolean checkIsValid(HashMap<String, HashMap<String, String>> hashMap, String[] words_command) {
        if (hashMap.containsKey(
                words_command[0].substring(words_command[0].indexOf('"') + 1, words_command[0].lastIndexOf('"'))))
            return false;
        if (dayIsBusy(hashMap,
                words_command[5].substring(words_command[5].indexOf('"') + 1, words_command[5].lastIndexOf('"'))))
            return false;
        if (!words_command[0].substring(words_command[0].indexOf('"') + 1, words_command[0].lastIndexOf('"'))
                .matches("\\d{1,4}"))
            return false;
        if (!words_command[1].substring(words_command[1].indexOf('"') + 1, words_command[1].lastIndexOf('"'))
                .matches("[\\w&&\\D][\\w_&&[^bahmanBAHMAN]]{6,15}\\d"))
            return false;
        if (!words_command[4].substring(words_command[4].indexOf('"') + 1, words_command[4].lastIndexOf('"'))
                .matches("[\\p{Alpha} ]*[-][\\d]{8,10}"))
            return false;
        if (!words_command[5].substring(words_command[5].indexOf('"') + 1, words_command[5].lastIndexOf('"'))
                .matches("[\\d]{8}"))
            return false;
        if (!words_command[7].substring(words_command[7].indexOf('"') + 1, words_command[7].lastIndexOf('"'))
                .matches("\\d|([0-9][.][0-9]{1,2})"))
            return false;
        return true;
    }

    private static boolean dayIsBusy(HashMap<String, HashMap<String, String>> hashMap, String string) {
        if (Integer.parseInt(string.substring(6)) > 30 || Integer.parseInt(string.substring(4, 6)) > 12)
            return true;
        for (var iterable_element : hashMap.entrySet()) {
            if (iterable_element.getValue().containsValue(string))
                return true;
        }
        return false;
    }

}





ffffffffffffffffffffff



