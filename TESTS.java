import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TESTS {
    
    public static void main(String[] args) { 
        
        String tp = "zgr zgzgfv zfvvv  vz";
        String reg = "gf";
        System.out.println(tp.replaceAll("z|"+ reg ,"1"));

        String f = "kkkkkkkkkkkkkkkkkkk";
        //f = f.replace(".", "*");
        System.out.println(f);
        f = f.replaceAll(".", "*");
        System.out.println(f);


        String h = "thc6fg70";
        System.out.println("\\\tu");

        String command224220 = "71";
        System.out.println(command224220.matches("\\+?\\d"));


        String command22422 = "szgv-123456789";
        System.out.println(command22422.matches("[\\p{Alpha} ]*-[\\d]{8,10}"));

        
        String command2222 = "4.3";
        System.out.println(command2222.matches("[\\d([0-9][.][0-9]{1,2})]"));


        
        String command222 = "abc12345678";
        System.out.println(command222.matches("[\\p{Alpha} ]*[-][\\d]{8,10}"));

        String command22 = "AyesItsGoood4";
        System.out.println(command22.matches("[\\w&&\\D][\\w_&&[^bahmanBAHMAN]]{6,15}\\d"));
        
        String commandd2 = "al..ial.i";
        System.out.println(commandd2.matches("^((?!ali).)*$"));
    }
    }  
        
     //       "[\\w&&\\D][\\w_&&[^bahmanBAHMAN]]{6,15}\\d"; bihode chon yekie
     //       "[\\w&&\\^d[\\w_&&[^bahmanBAHMAN]]{6,15}\\d";
   
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Matcher t = Pattern.compile("\\w+\\{[\\w =]+}").matcher("inp{ ef=gd }   inp{ rgs=fdg } fhg pp{ vd=fv }");
        //     String
    // while (t.find()) {
    //     String m = t.group().toString();
    //     System.out.println(m);
    //     System.out.println();
    // }


