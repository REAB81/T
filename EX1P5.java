import java.util.LinkedList;
import java.util.Scanner;

public class EX1P5 {
    public static void main(String[] args) {
        Cattle cattle = new Cattle();
        cattle.run();
    }

}


,,,,,,,,,,
/**
 * Cattle
 */
class Cattle {
    private LinkedList<Sheeps> sheeps = new LinkedList();
    private LinkedList<Sheepcote> sheepcotes = new LinkedList<>();
    private LinkedList<Persenel> persenels = new LinkedList<>();
    private int[] Today = new int[3];

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        boolean date_is_set = false;
        while (!input.contains("init")) {
            input = scanner.nextLine();
            if (!input.equals("init"))
                System.out.println("error: invalid command");
        }
        while (true) {
            input = scanner.nextLine();
            if (input.startsWith("add sheep ")) {
                if (shipIdDoesntExist(input.split(" ")))
                    sheeps.add(addNewSheep(input.split(" ")));
            } else if (input.startsWith("add sheepcote")) {
                String[] tempinput = input.split(" ");
                if (!Sheepcote.SheepcoteIdExist(tempinput[2], sheepcotes)) {
                    String[] temp = input.split(" ");
                    Sheepcote newSheepcote = new Sheepcote(Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
                    sheepcotes.add(newSheepcote);
                } else {
                    System.out.println("error: already exists");
                }
            } else if (input.startsWith("add shepherd")) {
                if (!Persenel.nameExist(input.substring(input.lastIndexOf(" ")+1), persenels)) {
                    Persenel persenel = new Persenel(input.substring(input.lastIndexOf(" ")+1));
                    persenels.add(persenel);
                } else {
                    System.out.println("error: already exists");
                }
            } else if (input.startsWith("today ")) {
                int[] date = ConvertDateToInt(input.substring(input.indexOf(" ") + 1));
                if (checkCalenderIsValid(input.substring(input.indexOf(" ") + 1))) {
                    Today = date;
                    date_is_set = true;
                } else {
                    System.out.println("error: invalid command");
                }
            } else if (input.equals("start")) {
                if (!date_is_set) {
                    System.out.println("error: system date is not set");
                } else {
                    startProgram();
                    break;
                }
            } else {
                System.out.println("error: invalid command");
            }
        }
    }

    private int[] ConvertDateToInt(String substring) {
        int[] temp = new int[3];
        String[] temp_string_date = substring.split("/");
        temp[0] = Integer.parseInt(temp_string_date[0]);
        temp[1] = Integer.parseInt(temp_string_date[1]);
        temp[2] = Integer.parseInt(temp_string_date[2]);
        return temp;
    }

    private void startProgram() {
        Scanner scanner = new Scanner(System.in);
        boolean day_pass = true;
        boolean isunpause = true;
        String input = new String();
        while (input != "end") {
            input = scanner.nextLine();
            if (input.startsWith("pause"))
                isunpause = false;
            else if (input.startsWith("unpause"))
                isunpause = true;
            else if(!isunpause) {
                continue;    
            }
            else if (isunpause) {
                if (input.startsWith("assign"))
                    checkAssignConditions(input);
                else if (input.startsWith("add forage")) {
                    checkAddForageConditions(input);
                }
                else if (input.startsWith("move")) {
                    moveSheepConditions(input);
                }
                else if (input.equals("feed") && day_pass) {
                    if (feedSheeps())
                        day_pass = false;
                }
                else if (input.equals("feed") && !day_pass) {
                    System.out.println("error: you cannot feed sheep more than once");
                }
                else if (input.startsWith("info sheep ")) {
                    infoOfTheSheep(input.substring(input.lastIndexOf(" ") + 1));
                }
                else if (input.startsWith("info sheepcote ")) {
                    infoOfTheSheepcote(input.substring(input.lastIndexOf(" ")+1));
                }
                else if (input.equals("elapse day") && day_pass) {
                    System.out.println("error: poor sheep are not fed");
                }
                else if (input.equals("elapse day") && !day_pass) {
                    day_pass = true;
                    Today[2]++;
                    Today[1] += Today[2] / 31;
                    Today[0] += Today[1] / 13;
                    Today[2] = Today[2] % 30;
                    Today[1] = Today[1] % 12;
                }
                else {
                    System.out.println("error: invalid command");
                }
            } 
            else {
                System.out.println("error: invalid command");
            }
        }
    }

    private void infoOfTheSheepcote(String substring) {
        try {
            int index_of_shepcote = Sheepcote.sheepCoteId(substring, sheepcotes);
            sheepcotes.get(index_of_shepcote).printInfo();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void infoOfTheSheep(String substring) {
        try {
            int sheep_index = Sheeps.indexOfSheep(Integer.parseInt(substring), sheeps);
            sheeps.get(sheep_index).printInfo(Today);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean feedSheeps() {
        if (Sheeps.atListMoreSheepDoesntHaveSheepCote(sheeps)) {
            System.out.println("error: one or more sheep left out");
            return false;
        }
        if (Sheepcote.insufficientForage(sheepcotes)) {
            System.out.println("error: insufficient forage");
            return false;
        }
        Sheepcote.eatForages(sheepcotes);
        return true;
    }

    private void moveSheepConditions(String input) {
        String[] inp = input.split(" ");
        try {
            int index_of_sheep = Sheeps.indexOfSheep(Integer.parseInt(inp[1]), sheeps);
            int index_of_sheepcote = Sheepcote.sheepCoteId(inp[2], sheepcotes);
            sheepcotes.get(index_of_sheepcote).hasPlace();
            sheepcotes.get(index_of_sheepcote).addNewSheepInSheepCote(Integer.parseInt(inp[1]), sheeps, index_of_sheep);
            sheeps.get(index_of_sheep).addShepCoteIdAndSheepHerd(Integer.parseInt(inp[2]),
                    sheepcotes.get(index_of_sheepcote).getNameShepherd());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void checkAddForageConditions(String input) {
        String[] spilit = input.split(" ");
        try {
            int shepcote_index = Sheepcote.sheepCoteId(spilit[3], sheepcotes);
            sheepcotes.get(shepcote_index).addForage(Integer.parseInt(spilit[2]));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void checkAssignConditions(String input) {
        String[] spilit = input.split(" ");
        try {
            int index_of_sheepcote = Sheepcote.sheepCoteId(spilit[2], sheepcotes);
            int index_of_sheepherd = Persenel.getshepherdName(spilit[1], persenels);
            persenels.get(index_of_sheepherd).shepHerdNotBusy();
            if (!(sheepcotes.get(index_of_sheepcote).getShepherdName()==null)) {
                int lastShepherd_index = Persenel.getshepherdName(sheepcotes.get(index_of_sheepcote).getShepherdName(),
                        persenels);
                persenels.get(lastShepherd_index).getBusy(false);
            }
            else { persenels.get(index_of_sheepherd).getBusy(true);
            sheepcotes.get(index_of_sheepcote).reserveShepher(spilit[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean checkCalenderIsValid(String substring) {
        String[] calender = substring.split("/");
        if (Integer.parseInt(calender[0]) < 0 || Integer.parseInt(calender[0]) > 99)
            return false;
        if (Integer.parseInt(calender[1]) > 12 || Integer.parseInt(calender[1]) < 0)
            return false;
        if (Integer.parseInt(calender[2]) > 30 || Integer.parseInt(calender[2]) < 0)
            return false;
        return true;
    }

    private Sheeps addNewSheep(String[] input) {
        Sheeps sheep = new Sheeps(Integer.parseInt(input[2]), input[3], input[4], Integer.parseInt(input[5]));
        return sheep;
    }

    private boolean shipIdDoesntExist(String[] input) {
        if (Integer.parseInt(input[2]) < 1) {
            System.out.println("error: invalid command");
            return false;
        }
        for (int i = 0; i < sheeps.size(); i++) {
            if (sheeps.get(i).checkId(Integer.parseInt(input[2]))) {
                System.out.println("error: already exists");
                return false;
            }
        }
        return true;
    }

}

class Sheeps {
    public Sheeps(int id, String name, String date_of_birth, int forage) {
        this.name = name;
        this.id = id;
        this.date_of_birth = date_of_birth;
        this.forage = forage;
    }

    public void addShepCoteIdAndSheepHerd(int id, String nameShepherd) {
        this.id = id;
        shepherd = nameShepherd;
    }

    public void printInfo(int[] today) {
        long age = CalcAge(today, date_of_birth);
        String info;
        info = "id: " + id + "\nname: " + name + "\nage: " + age + "\nsheepcote: ";
        if (this.hasShepCote)
            info += this.shepcote + '\n';
        else {
            info += "N/A\n";
        }
        if (this.shepherd == null) {
            info += "N/A\n";
        } else {
            info += shepherd + '\n';
        }
        System.out.println(info);
    }

    private long CalcAge(int[] today, String date_of_birth) {
        int[] temp = new int[3];
        String[] temp_string = date_of_birth.split("/");
        temp[0] = Integer.parseInt(temp_string[0]);
        temp[1] = Integer.parseInt(temp_string[1]);
        temp[2] = Integer.parseInt(temp_string[2]);
        double age;
        if (today[0] < temp[0]) {
            today[0] += 30;
            today[1]--;
        }
        age = today[0] - temp[0];
        if (today[1] < temp[1]) {
            today[1] += 12;
            today[2]--;
        }
        age += 30 * (today[1] - temp[1]);
        age += 365 * (today[2] - temp[2]);
        return (1 + Math.round(age));
    }

    public boolean checkId(int id) {
        if (id == this.id)
            return true;
        return false;
    }

    public static int indexOfSheep(int id, LinkedList<Sheeps> sheeps) {
        for (int i = 0; i < sheeps.size(); i++) {
            if (sheeps.get(i).id == id) {
                return i;
            }
        }
        throw new ArithmeticException("error: id does not exist");
    }

    public int forageItConsume() {
        return forage;
    }

    public void hasShepCote(boolean situtation, int id) {
        hasShepCote = situtation;
        this.shepcote = id;
    }

    public static boolean atListMoreSheepDoesntHaveSheepCote(LinkedList<Sheeps> sheeps) {
        for (int i = 0; i < sheeps.size(); i++) {
            if (sheeps.get(i).hasShepCote == false) {
                return true;
            }
        }
        return false;
    }

    private int id;
    private String name;
    private String date_of_birth;
    private int forage;
    private boolean hasShepCote = false;
    private String shepherd = null;
    private int shepcote;
}

/**
 * Sheepcote
 */
class Sheepcote {
    private int id;
    private int capacity;
    private String SheoherdNAme = null;
    private int forage = 0;
    private LinkedList<Integer> sheepcote_sheeps_id = new LinkedList<>();
    private int forage_need_to_feed_sheeps = 0;

    public String getNameShepherd() {
        return SheoherdNAme;
    }

    public void printInfo() {
        String print = "";
        print += "id: " + id + "\nnumber of sheep: " + sheepcote_sheeps_id.size() + "\ncapacity: " + capacity
                + "\nforage balance: ";
        if (forage == forage_need_to_feed_sheeps)
            print += "0\n";
        if (forage > forage_need_to_feed_sheeps) {
            print += "+"+ (forage-forage_need_to_feed_sheeps) +"\n";
        } else {
            print += "-"+ (forage-forage_need_to_feed_sheeps) +"\n";
        }
        if (SheoherdNAme == null) {
            print += "N/A\n";
        } else {
            print += SheoherdNAme + "\n";
        }
        System.out.println(print);
    }

    public Sheepcote(int id_, int capacity_) {
        id = id_;
        capacity = capacity_;
    }

    public void addForage(int parseInt) {
        forage += parseInt;
    }

    public static boolean SheepcoteIdExist(String split, LinkedList<Sheepcote> sheepcotes) {
        for (int i = 0; i < sheepcotes.size(); i++) {
            if (sheepcotes.get(i).id == Integer.parseInt(split)) {
                return true;
            }
        }
        return false;
    }

    public static int sheepCoteId(String split, LinkedList<Sheepcote> sheepcotes) {
        for (int i = 0; i < sheepcotes.size(); i++) {
            if (sheepcotes.get(i).id == Integer.parseInt(split)) {
                return i;
            }
        }
        throw new ArithmeticException("error: id does not exist");
    }

    public String getShepherdName() {
        return SheoherdNAme;
    }

    public void reserveShepher(String shepherd_name) {
        SheoherdNAme = shepherd_name;
    }

    public void hasPlace() {
        if (this.capacity < 1) {
            throw new ArithmeticException("error: capacity limit exceeded");
        }
    }

    public void addNewSheepInSheepCote(int id, LinkedList<Sheeps> sheeps, int index_of_sheep) {
        this.capacity--;
        this.sheepcote_sheeps_id.add(id);
        forage_need_to_feed_sheeps += sheeps.get(index_of_sheep).forageItConsume();
        sheeps.get(index_of_sheep).hasShepCote(true, this.id);
    }

    public static boolean insufficientForage(LinkedList<Sheepcote> sheepcotes) {
        for (int i = 0; i < sheepcotes.size(); i++) {
            if (sheepcotes.get(i).forage_need_to_feed_sheeps > sheepcotes.get(i).forage) {
                return true;
            }
        }
        return false;
    }

    public static void eatForages(LinkedList<Sheepcote> sheepcotes) {
        for (int i = 0; i < sheepcotes.size(); i++) {
            if (sheepcotes.get(i).sheepcote_sheeps_id.size() != 0) {
                sheepcotes.get(i).forage = 0;
            }
        }
    }
}

class Persenel {
    private String name;
    private boolean hasSheepHerd;

    public Persenel(String name) {
        this.name = name;
        hasSheepHerd = false;
    }

    public static boolean nameExist(String name, LinkedList<Persenel> persenels) {
        for (int i = 0; i < persenels.size(); i++) {
            if (persenels.get(i).name == name) {
                return true;
            }
        }
        return false;
    }

    public static int getshepherdName(String split, LinkedList<Persenel> persenels) {
        for (int i = 0; i < persenels.size(); i++) {
            if (persenels.get(i).name.equals(split)) {
                return i;
            }
        }
        throw new ArithmeticException("error: name does not exist");
    }

    public void shepHerdNotBusy() {
        if (this.hasSheepHerd)
            throw new ArithmeticException("error: already assigned");
    }

    public void getBusy(boolean situtation) {
        hasSheepHerd = situtation;
    }
}
