import java.util.*;
import java.util.regex.*;

public class EX5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input;
        /// first scanning in order to init
        while (true) {
            input = scanner.nextLine();
            if (input.trim().equals("init"))
                break;
        }

        /// second scanning to add sheeps and ...
        boolean notFindId = true;
        boolean setTime = false;
        ArrayList<HashMap<String, String>> listOfSheeps = new ArrayList<>();
        ArrayList<HashMap<String, String>> listOfSheepcotes = new ArrayList<>();
        ArrayList<String> listOfShepherds = new ArrayList<>();
        ArrayList<HashMap<String, Integer>> forageAndNumber = new ArrayList<>();
        HashMap<String, String> anyAdding;
        HashMap<String, Integer> forageAndNumberHashMap;
        String time = "";
        /// first round
        while (true) {
            input = scanner.nextLine();
            String[] all = input.trim().split("\\s+");
            /// start working
            if (input.trim().equals("start")) {
                if (setTime) {
                    break;
                } else {
                    System.out.println("error: system date is not set");
                }
            }
            /// adding sheep
            else if (Pattern.matches("\\s*(add)\\s+(sheep)\\s+\\d+\\s+\\w+\\s+\\d{2}/\\d{2}/\\d{2}\\s+\\d+\\s*", input)
                    && Integer.parseInt(all[2]) > 0 && Integer.parseInt(all[5]) > 0) {
                boolean n = true;
                for (int i = 0; i < listOfSheeps.size(); i++) {
                    if (listOfSheeps.get(i).get("id").equals(all[2])) {
                        n = false;
                        System.out.println("error: already exists");
                        i = listOfSheeps.size();
                    }
                }
                if (n) {
                    anyAdding = new HashMap<>();
                    anyAdding.put("id", all[2]);
                    anyAdding.put("name", all[3]);
                    anyAdding.put("birthDay", all[4]);
                    anyAdding.put("forage", all[5]);
                    anyAdding.put("sheepcote", "N/A");
                    anyAdding.put("shepherd", "N/A");
                    listOfSheeps.add(anyAdding);
                }
            }
            /// adding sheepcote
            else if (Pattern.matches("\\s*(add)\\s+(sheepcote)\\s+\\d+\\s+\\d+\\s*", input)
                    && Integer.parseInt(all[2]) > 0 && Integer.parseInt(all[3]) > 0) {
                notFindId = true;
                for (int i = 0; i < listOfSheepcotes.size(); i++) {
                    if (listOfSheepcotes.get(i).get("id").equals(all[2])) {
                        System.out.println("error: already exists");
                        notFindId = false;
                        i = listOfSheepcotes.size();
                    }
                }
                if (notFindId) {
                    anyAdding = new HashMap<>();
                    anyAdding.put("id", all[2]);
                    anyAdding.put("capacity", all[3]);
                    anyAdding.put("forage", "0");
                    anyAdding.put("shepherd", "N/A");
                    forageAndNumberHashMap = new HashMap<>();
                    forageAndNumberHashMap.put("id", Integer.parseInt(all[2]));
                    forageAndNumberHashMap.put("number", 0);
                    forageAndNumberHashMap.put("forage", 0);
                    forageAndNumberHashMap.put("baseForage", 0);
                    forageAndNumberHashMap.put("capacity", Integer.parseInt(all[3]));
                    listOfSheepcotes.add(anyAdding);
                    forageAndNumber.add(forageAndNumberHashMap);
                }
            }
            /// adding shepherd
            else if (Pattern.matches("\\s*(add)\\s+(shepherd)\\s+[\\w//(//)]+\\s*", input)) {
                notFindId = true;
                for (int i = 0; i < listOfShepherds.size(); i++) {

                    if (listOfShepherds.get(i).equals(all[2])) {
                        notFindId = false;
                        i = listOfShepherds.size();
                        System.out.println("error: already exists");
                    }
                }
                if (notFindId) {
                    listOfShepherds.add(all[2]);
                }
            }
            /// time setting
            else if (Pattern.matches("\\s*(today)\\s+\\d{2}/\\d{2}/\\d{2}\\s*", input) && checkTheTime(all[1])) {
                setTime = true;
                time = all[1];
            }
            /// if the input didn't match the desired format
            else {
                System.out.println("error: invalid command");
            }
        }

        HashMap<String, String> assignments = new HashMap<>();
        boolean isFed = false;
        /// second round
        while (true) {
            input = scanner.nextLine();
            String[] all = input.trim().split("\\s+");
            /// end
            if (input.trim().equals("end")) {
                break;
            }
            /// pause
            else if (input.trim().equals("pause")) {
                boolean stop = true;
                while (stop) {
                    input = scanner.nextLine();
                    if (input.trim().equals("unpause")) {
                        stop = false;
                    }
                }
            }
            /// assign shepherds
            else if (Pattern.matches("\\s*(assign)\\s+\\w+\\s+\\d+\\s*", input)) {
                if (assignments.containsKey(all[2]) && assignments.get(all[2]).equals(all[1])) {

                } else if (!listOfShepherds.contains(all[1])) {
                    System.out.println("error: name does not exist");
                } else {
                    boolean found = false;
                    for (int i = 0; i < listOfSheepcotes.size(); i++) {
                        if (listOfSheepcotes.get(i).get("id").equals(all[2])) {
                            found = true;
                            i = listOfSheepcotes.size();
                        }
                    }
                    if (!found) {
                        System.out.println("error: id does not exist");
                    } else if (assignments.containsValue(all[1])) {
                        System.out.println("error: already assigned");
                    } else {
                        assignments.put(all[2], all[1]);
                    }

                }
            }
            /// add forage
            else if (Pattern.matches("\\s*(add)\\s+(forage)\\s+\\d+\\s+\\d+\\s*", input)
                    && Integer.parseInt(all[2]) > 0) {
                boolean found = false;
                int indexOfId = 0;
                for (int i = 0; i < forageAndNumber.size(); i++) {
                    if (forageAndNumber.get(i).get("id") == Integer.parseInt(all[3])) {
                        found = true;
                        indexOfId = i;
                        i = forageAndNumber.size();
                    }
                }
                if (found) {
                    int first = forageAndNumber.get(indexOfId).get("baseForage");
                    forageAndNumber.get(indexOfId).remove("baseForage");
                    forageAndNumber.get(indexOfId).put("baseForage", Integer.parseInt(all[2]) + first);
                } else {
                    System.out.println("error: id does not exist");
                }
            }
            /// move the sheeps to sheepcotes
            else if (Pattern.matches("\\s*(move)\\s+\\d+\\s+\\d+\\s*", input)) {
                boolean findSheep = false;
                boolean findSheepcote = false;
                int indexOfSheep = 0;
                int indexOfSheepcote = 0;
                for (int i = 0; i < listOfSheeps.size(); i++) {
                    if (listOfSheeps.get(i).get("id").equals(all[1])) {
                        findSheep = true;
                        indexOfSheep = i;
                        i = listOfSheeps.size();
                    }
                }
                for (int i = 0; i < forageAndNumber.size(); i++) {
                    if (forageAndNumber.get(i).get("id") == Integer.parseInt(all[2])) {
                        findSheepcote = true;
                        indexOfSheepcote = i;
                        i = forageAndNumber.size();
                    }
                }
                if (findSheep && findSheepcote) {
                    if (listOfSheeps.get(indexOfSheep).get("sheepcote")
                            .equals(Integer.toString(forageAndNumber.get(indexOfSheepcote).get("id")))) {

                    } else if (forageAndNumber.get(indexOfSheepcote).get("number")
                            .equals(forageAndNumber.get(indexOfSheepcote).get("capacity"))) {
                        System.out.println("error: capacity limit exceeded");
                    } else {
                        /// if the sheep did not belong to any sheepcote
                        if (listOfSheeps.get(indexOfSheep).get("sheepcote").equals("N/A")) {
                            listOfSheeps.get(indexOfSheep).remove("sheepcote");
                            listOfSheeps.get(indexOfSheep).remove("shepherd");
                            listOfSheeps.get(indexOfSheep).put("sheepcote", all[2]);
                            listOfSheeps.get(indexOfSheep).put("shepherd", assignments.get(all[2]));
                            int firstForage = forageAndNumber.get(indexOfSheepcote).get("forage");
                            int firstNumber = forageAndNumber.get(indexOfSheepcote).get("number");
                            forageAndNumber.get(indexOfSheepcote).remove("forage");
                            forageAndNumber.get(indexOfSheepcote).remove("number");
                            forageAndNumber.get(indexOfSheepcote).put("forage",
                                    firstForage + Integer.parseInt(listOfSheeps.get(indexOfSheep).get("forage")));
                            forageAndNumber.get(indexOfSheepcote).put("number", firstNumber + 1);
                        } else {
                            /// first delete the information of the sheep in sheepcote's list
                            for (int i = 0; i < forageAndNumber.size(); i++) {
                                if (forageAndNumber.get(i).get("id") == Integer
                                        .parseInt(listOfSheeps.get(indexOfSheep).get("sheepcote"))) {
                                    int firstForage2 = forageAndNumber.get(i).get("forage");

                                    int firstNumber2 = forageAndNumber.get(i).get("number");
                                    forageAndNumber.get(i).remove("forage");
                                    forageAndNumber.get(i).remove("number");
                                    forageAndNumber.get(i).put("forage", firstForage2
                                            - Integer.parseInt(listOfSheeps.get(indexOfSheep).get("forage")));
                                    forageAndNumber.get(i).put("number", firstNumber2 - 1);
                                    i = forageAndNumber.size();
                                }
                            }
                            /// add the sheep to the new sheepcote
                            listOfSheeps.get(indexOfSheep).remove("sheepcote");
                            listOfSheeps.get(indexOfSheep).remove("shepherd");
                            listOfSheeps.get(indexOfSheep).put("sheepcote", all[2]);
                            listOfSheeps.get(indexOfSheep).put("shepherd", assignments.get(all[2]));
                            int firstForage = forageAndNumber.get(indexOfSheepcote).get("forage");
                            int firstNumber = forageAndNumber.get(indexOfSheepcote).get("number");
                            forageAndNumber.get(indexOfSheepcote).remove("forage");
                            forageAndNumber.get(indexOfSheepcote).remove("number");
                            forageAndNumber.get(indexOfSheepcote).put("forage",
                                    firstForage + Integer.parseInt(listOfSheeps.get(indexOfSheep).get("forage")));
                            forageAndNumber.get(indexOfSheepcote).put("number", firstNumber + 1);
                        }
                    }
                } else {
                    System.out.println("error: id does not exist");
                }
            }
            /// feed the poor sheeps
            else if (input.trim().equals("feed")) {
                if (isFed) {
                    System.out.println("error: you cannot feed sheep more than once");
                } else {
                    boolean isThereAnyLeftSheep = true;
                    for (int i = 0; i < listOfSheeps.size(); i++) {
                        if (listOfSheeps.get(i).get("sheepcote").equals("N/A")) {
                            System.out.println("error: one or more sheep left out");
                            isThereAnyLeftSheep = false;
                            i = listOfSheeps.size();
                        }
                    }
                    if (isThereAnyLeftSheep) {
                        boolean lastSanction = true;
                        for (int i = 0; i < forageAndNumber.size(); i++) {
                            if (forageAndNumber.get(i).get("baseForage") < forageAndNumber.get(i).get("forage")) {
                                System.out.println("error: insufficient forage");
                                lastSanction = false;
                                i = forageAndNumber.size();
                            }
                        }
                        if (lastSanction) {
                            for (int i = 0; i < forageAndNumber.size(); i++) {
                                forageAndNumber.get(i).remove("baseForage");
                                forageAndNumber.get(i).put("baseForage", 0);
                                isFed = true;
                            }
                        }
                    }
                }
            }
            /// information of sheep
            else if (Pattern.matches("\\s*(info)\\s+(sheep)\\s+\\d+\\s*", input)) {
                boolean found = true;
                for (int i = 0; i < listOfSheeps.size(); i++) {
                    if (listOfSheeps.get(i).get("id").equals(all[2])) {
                        found = false;

                        System.out.println("id: " + all[2]);
                        System.out.println("name: " + listOfSheeps.get(i).get("name"));

                        System.out.println("age: " + findAge(listOfSheeps.get(i).get("birthDay"), time));
                        System.out.println("sheepcote: " + listOfSheeps.get(i).get("sheepcote"));

                        if (listOfSheeps.get(i).get("sheepcote").equals("N/A"))
                            System.out.println("shepherd: N/A");

                        else if (assignments.containsKey(listOfSheeps.get(i).get("sheepcote")))
                            System.out.println("shepherd: " + assignments.get(listOfSheeps.get(i).get("sheepcote")));

                        else
                            System.out.println("shepherd: N/A");
                        i = listOfSheeps.size();
                    }
                }
                if (found) {
                    System.out.println("error: id does not exist");
                }
            }
            /// information of sheepcote
            else if (Pattern.matches("\\s*(info)\\s+(sheepcote)\\s+\\d+\\s*", input)) {
                boolean found = true;
                for (int i = 0; i < listOfSheepcotes.size(); i++) {
                    if (listOfSheepcotes.get(i).get("id").equals(all[2])) {
                        found = false;
                        for (int j = 0; j < forageAndNumber.size(); j++) {
                            if (forageAndNumber.get(j).get("id") == Integer.parseInt(all[2])) {
                                System.out.println("id: " + all[2]);
                                System.out.println(
                                        "number of sheep: " + Integer.toString(forageAndNumber.get(j).get("number")));
                                System.out.println("capacity: " + listOfSheepcotes.get(i).get("capacity"));
                                int forageBalance = forageAndNumber.get(j).get("baseForage")
                                        - forageAndNumber.get(j).get("forage");
                                if (forageBalance > 0)
                                    System.out.println("forage balance: +" + Integer.toString(forageBalance));
                                else
                                    System.out.println("forage balance: " + Integer.toString(forageBalance));
                                if (assignments.containsKey(all[2])) {
                                    System.out.println("shepherd: " + assignments.get(all[2]));
                                } else {
                                    System.out.println("shepherd: N/A");
                                }
                                j = forageAndNumber.size();
                            }
                        }
                        i = listOfSheepcotes.size();
                    }
                }
                if (found) {
                    System.out.println("error: id does not exist");
                }
            }
            /// elapse day
            else if (Pattern.matches("\\s*(elapse)\\s+(day)\\s*", input)) {
                if (isFed) {
                    time = elapse(time);
                    System.out.println("today: " + time);
                    isFed = false;
                } else {
                    System.out.println("error: poor sheep are not fed");
                }
            } else {
                System.out.println("error: invalid command");
            }
        }

        scanner.close();
    }

    static boolean checkTheTime(String time) {

        String month = time.substring(3, 5);
        String day = time.substring(6, 8);
        return Integer.parseInt(month) <= 12 && Integer.parseInt(month) >= 1 && Integer.parseInt(day) <= 30
                && Integer.parseInt(day) >= 1;
    }

    static String findAge(String birth, String today) {
        int birthYear = Integer.parseInt(birth.substring(0, 2));
        int birthMonth = Integer.parseInt(birth.substring(3, 5));
        int birthDay = Integer.parseInt(birth.substring(6, 8));
        int todayYear = Integer.parseInt(today.substring(0, 2));
        int todayMonth = Integer.parseInt(today.substring(3, 5));
        int todayDay = Integer.parseInt(today.substring(6, 8));
        int totalDifferenceInDay = (todayYear - birthYear) * 360 + (todayMonth - birthMonth) * 30
                + (todayDay - birthDay);
        return Integer.toString((totalDifferenceInDay + 359) / 360);
    }

    static String elapse(String today) {
        int year = Integer.parseInt(today.substring(0, 2));
        int month = Integer.parseInt(today.substring(3, 5));
        int day = Integer.parseInt(today.substring(6, 8));
        if (day < 9) {
            day++;
            return today.substring(0, 6) + "0" + Integer.toString(day);
        } else if (day < 30) {
            day++;
            return today.substring(0, 6) + Integer.toString(day);
        } else if (month < 9) {
            month++;
            return today.substring(0, 3) + "0" + Integer.toString(month) + "/01";
        } else if (month < 12) {
            month++;
            return today.substring(0, 3) + Integer.toString(month) + "/01";
        } else if (year < 9) {
            year++;
            return "0" + Integer.toString(year) + "/01/01";
        } else {
            year++;
            return Integer.toString(year) + "/01/01";
        }
    }
}