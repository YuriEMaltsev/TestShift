/*
  Считаем, что максимальная длина строки 255 символов,
  все что больше будет отбрасываться без предупреждения
  если в конце цифр встретились не цифровые символы - это строка
  Читать из файла посимвольно (если будет огромная строка, можно игнорить остаток)


*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

public class  Main {



      static class Work {
        final static short r_long    = 1;   // Int
        final static short r_double = 2;   // Double
        final static short r_string = 3;   // String
        //StandardCharsets utf8 = StandardCharsets.UTF_8;

      public static List<String> readMyFile(String filename) throws IOException {

          Path filePath = Paths.get(filename);
          List<String> lines = new ArrayList<String>();
          try {
              lines = Files.readAllLines(filePath);
          } catch (IOException e) {
              // Handle I/O errors, such as file not found, permission issues, etc.
              System.err.println("Ошибка при работе с исходым файлом: " + filename +
                      "подробная информация" + e.getMessage());
          }
          return lines;
      } //readMyFile

        // разбор строки
        static short ParseStr(String str) {

            // пробую Long
            try {
                long l = Long.parseLong(str);
                //System.out.println("Int l = " + l);
                return(r_long);
            } catch (NumberFormatException ignored) {}

            // пробую Double
            try {
                double d = Double.parseDouble(str);
                //System.out.println("double d = " + d);
                return(r_double);
            } catch (NumberFormatException ignored) {}

            // если не long b не double, то String
            return r_string;

        } // ParseStr

//        static void WriteToFile(String str, int tp){
//
//        }

        static void Param(String param) {
              System.out.println("Обработка параметра "  + param);
        }

       static void  WorkFile (String param) {
         String userDirectory = Paths.get("").toAbsolutePath().toString();
            //   System.out.println ("Current directory : " + userDirectory);
         Path filePath  = Paths.get(userDirectory, "long.res");

         List<String> lines = new ArrayList<String>();  // буфер строк
         System.out.println("\n Обработка файла " + param);


         try {
            lines =  readMyFile(param);
         }
         catch(IOException e) {
             e.printStackTrace();
         }

          for ( String s : lines) {

            System.out.print(s);
            switch (ParseStr(s)) {
              case r_long:
                  System.out.print("  - long \n");
                  try {
                      // Write content to file
                      byte[] cs =   (s+"\r\n").getBytes();
                      Files.write(filePath, cs, StandardOpenOption.APPEND);
                  }
                  catch (IOException e) {
                      e.printStackTrace();
                  }
                  break;
                case r_double:
                  System.out.print("  - double \n");
                  break;
                case r_string:
                  System.out.print("  - String \n");
                  break;
            }
          }
       } // WorkFile

    } // Work

    public static void main(String[] args) {

        System.out.printf("Hello SHIFT!\n");

        for (String s : args) {
            if (s.indexOf("-") == 0)
                Work.Param(s);
            else
                Work.WorkFile(s);
        } // s
    }
}
