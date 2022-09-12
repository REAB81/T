import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EX1P3 {
    public static void main(String[] args) {
        run();

    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        String bomb = scanner.nextLine();
        String bobsBomb = scanner.nextLine();
        int cells = scanner.nextInt();
        List<Integer> component = new ArrayList<Integer>();
        component.add(cells);
        findMinimizwWay(bomb, bobsBomb, cells, component);
        component.remove(0);
        printLeastPath(component);
    }

    private static void printLeastPath(List<Integer> component) {
        for (int i = 0; i < component.size(); i++) {
            if (component.get(i) == -1) {
                component.remove(i);
                i--;
            }
        }
        if (component.size() == 0) {
            System.out.println(-1);
            return;
        } else {
            System.out.println(Collections.min(component));
        }
    }

    private static void findMinimizwWay(String bomb, String bobsBomb, int cells, List<Integer> component) {
        bomb = checkIfMoreThanThreeBombs(bomb);
        if (bomb == "" && cells > -1) {
            component.add(component.get(0) - cells);
            return;
        }
        if (cells < 0 || (bobsBomb == "" && bomb != "")) {
            component.add(-1);
            return;
        }
        calculateAllPaths( bomb,  bobsBomb,  cells,component);
        if (bomb != "")
            component.add(-1);
    }

    private static void calculateAllPaths(String bomb, String bobsBomb, int cells, List<Integer> component) {
        for (int i = 0; i < bomb.length(); i++) {
            if ((i + 1 != bomb.length()) && bomb.charAt(i) == bomb.charAt(i + 1)) {
                if (bomb.length() > 1 && bobsBomb.indexOf(bomb.charAt(i)) != -1) {
                    String new_bobsBomb = bobsBomb.replace(
                            bobsBomb.substring(bobsBomb.indexOf(bomb.charAt(i)), bobsBomb.indexOf(bomb.charAt(i)) + 1),
                            "");
                    String new_bomb = bomb.replace(bomb.substring(i, i + 2), "");
                    cells--;
                    findMinimizwWay(new_bomb, new_bobsBomb, cells, component);
                    i = i + 1;
                }
            } else {
                int x = 0;
                for (int j = 0; j < bobsBomb.length(); j++) {
                    if (bobsBomb.charAt(j) == bomb.charAt(i))
                        x++;
                }
                if (x < 2)
                    continue;
                cells-=2;

                    String t = String.valueOf(bomb.charAt(i)) + String.valueOf(bomb.charAt(i));
                String new_bobsBomb = bobsBomb.replace( t,"");        
                String new_bomb = bomb.replace(bomb.substring(i, i + 1), "");
                findMinimizwWay(new_bomb, new_bobsBomb, cells, component);
            }
        }
    }

    private static String checkIfMoreThanThreeBombs(String bomb) {
        int counter = 0;
        for (int i = 0; i < bomb.length(); i++) {
            counter = 0;
            for (int j = i + 1; j < bomb.length(); j++) {
                if (bomb.charAt(i) != bomb.charAt(j)) {
                    if (counter > 1)
                        bomb = bomb.replace(bomb.substring(i, j), "");
                    break;
                }
                counter++;
            }
        }
        return bomb;
    }
}




/*
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class vv {
    public static void main(String[] args) {
        run();

    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        String bomb = scanner.nextLine();
        String bobsBomb = scanner.nextLine();
        int cells = scanner.nextInt();
        List<Integer> component = new ArrayList<Integer>();
        component.add(cells);
        findMinimizwWay(bomb, bobsBomb, cells, component);
        component.remove(0);
        printLeastPath(component);
    }

    private static void printLeastPath(List<Integer> component) {
        for (int i = 0; i < component.size(); i++) {
            if (component.get(i) == -1) {
                component.remove(i);
                i--;
            }
        }
        if (component.size() == 0) {
            System.out.println(-1);
            return;
        } else {
            System.out.println(Collections.min(component));
        }
    }

    private static void findMinimizwWay(String bomb, String bobsBomb, int cells, List<Integer> component) {
        bomb = checkIfMoreThanThreeBombs(bomb);
        if (bomb == "" && cells > -1) {
            component.add(component.get(0) - cells);
            return;
        }
        if (cells < 0 || (bobsBomb == "" && bomb != "")) {
            component.add(-1);
            return;
        }
        calculateAllPaths( bomb,  bobsBomb,  cells,component);
        if (bomb != "")
            component.add(-1);
    }

    private static void calculateAllPaths(String bomb, String bobsBomb, int cells, List<Integer> component) {
        for (int i = 0; i < bomb.length(); i++) {
            if ((i + 1 != bomb.length()) && bomb.charAt(i) == bomb.charAt(i + 1)) {
                i = i + 1;
                if (bomb.length() > 1 && bobsBomb.indexOf(bomb.charAt(i)) != -1) {
                    String new_bobsBomb = bobsBomb.replace(
                            bobsBomb.substring(bobsBomb.indexOf(bomb.charAt(i)), bobsBomb.indexOf(bomb.charAt(i)) + 1),
                            "");
                    String new_bomb = bomb.replace(bomb.substring(i, i + 2), "");
                    cells--;
                    findMinimizwWay(new_bomb, new_bobsBomb, cells, component);
                }
            } else {
                int x = 0;
                for (int j = 0; j < bobsBomb.length(); j++) {
                    if (bobsBomb.charAt(j) == bomb.charAt(i))
                        x++;
                }
                if (x < 2)
                    continue;
                cells-=2;

                    String t = String.valueOf(bomb.charAt(i)) + String.valueOf(bomb.charAt(i));
                String new_bobsBomb = bobsBomb.replace( t,"");        
                String new_bomb = bomb.replace(bomb.substring(i, i + 1), "");
                findMinimizwWay(new_bomb, new_bobsBomb, cells, component);
            }
        }
    }

    private static String checkIfMoreThanThreeBombs(String bomb) {
        int counter = 0;
        for (int i = 0; i < bomb.length(); i++) {
            counter = 0;
            for (int j = i + 1; j < bomb.length(); j++) {
                if (bomb.charAt(i) != bomb.charAt(j)) {
                    if (counter > 1)
                        bomb = bomb.replace(bomb.substring(i, j), "");
                    break;
                }
                counter++;
            }
        }
        return bomb;
    }
}
 */