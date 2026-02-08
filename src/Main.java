/*
  Считаем, что максимальная длина строки 255 символов,
  все что больше будет отбрасываться без предупреждения
  если в конце цифр встретились не цифровые символы - это строка
  Читать из файла посимвольно (если будет огромная строка, можно игнорить остаток)

    ArrayList<String> arr = new ArrayList<String>();
    arr.add("neo");
    arr.add("morpheus");
    arr.add("trinity");
    Iterator<String> foreach = arr.iterator();
    while (foreach.hasNext()) System.out.println(foreach.next())

*/
/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class  Main {

    public static List<String> readMyFile(String filename) throws IOException {

        Path filePath = Paths.get(filename);
        List<String> lines = new ArrayList<String>();
        try {
            // Read the entire file content into a String using the default UTF-8 charset
            lines = Files.readAllLines(filePath);
            System.out.println("File content:");
            for (String s: lines) {
                System.out.println(s);
            }
            return lines;
        } catch (IOException e) {
            // Handle I/O errors, such as file not found, permission issues, etc.
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
        return lines;
    }


      static class Work {
        final static short r_int    = 1;   // Int
        final static short r_double = 2;   // Double
        final static short r_string = 3;   // String

        // разбор строки
        static short ParseStr(String str) {

            // если не long b не double, то String
            short ret = r_string;

            // пробую Long
            try {
                long l = Long.parseLong(str);
                //System.out.println("Int l = " + l);
                return(r_int);
            } catch (NumberFormatException ignored) {}

            // пробую Double
            try {
                double d = Double.parseDouble(str);
                //System.out.println("double d = " + d);
                return(r_double);
            } catch (NumberFormatException ignored) {}

            return ret;
        }
        //@Contract(pure = true)
        static void WriteToFile(String str, int tp){

        }

        static void Param(String param) {
              System.out.println("Обработка параметра "  + param);
        }

        static void  WorkFile (String param) {
          String buf;  // буфер строк
          System.out.println("\n Файл " + param);
          try {
             List<String> lines =  readMyFile(param);
          }
          catch(IOException e) {
              e.printStackTrace();
          }

          System.out.println("buf: \n "  + param);
           System.out.println("\n buf");

        }
    }


    public static void main(String[] args) {
        String test="-1008.0E2";
        short res;
        res = Work.ParseStr(test);
        System.out.printf("res="+res);

        System.out.printf("Hello SHIFT!\n");

        for (int i = 0; i < args.length; i++) {
            String s;
            if (args[i].indexOf("-") == 0)
                Work.Param(args[i]);
            else
                Work.WorkFile(args[i]);
        }

    }
}
