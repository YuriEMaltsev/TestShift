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
import java.io.ByteArrayOutputStream;
//import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

public class  Main {

      static class Work {

        final static short r_long   = 1;   // Int
        final static short r_double = 2;   // Double
        final static short r_string = 3;   // String

        // пути до целевых файлов
        static Path filePathLong  ;
        static Path filePathDouble ;
        static Path filePathString ;

        // указание директории для целевых файлов
        // static boolean is_param_desc_dir = false;
        // целевая директория
        static String destDirectory;

        static int countAll = 0;
        static int countLong = 0;
        static int countDouble = 0;
        static int countString;

        public static List<String> readSourceFile(String filename) throws IOException {

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
        } //readSourceFile

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

          static void OutFilesReset(){

              // удалить файл  для Long
              try {
                  boolean deleteLong = Files.deleteIfExists(filePathLong);
              }
              catch (IOException e) {
                  System.err.println(" Не могу удалить файл" + filePathLong + "\n");
              }
              try {
                  // создать файл  для Long
                  Files.createFile(filePathLong);
              }
              catch (IOException e) {
                  System.err.println(" Не могу создать пустой файл" + filePathLong + "\n");
              }

              // удалить файл  для Double
              try {
                  boolean deleteLong = Files.deleteIfExists(filePathDouble);
              }
              catch (IOException e) {
                  System.err.println(" Не могу удалить файл" + filePathDouble + "\n");
              }
              try {
                  // создать файл  для Long
                  Files.createFile(filePathDouble);
              }
              catch (IOException e) {
                  System.err.println(" Не могу создать пустой файл" + filePathDouble + "\n");
              }

              // удалить файл  для String
              try {
                  boolean deleteLong = Files.deleteIfExists(filePathString);
              }
              catch (IOException e) {
                  System.err.println(" Не могу удалить файл" + filePathString + "\n");
              }
              try {
                  // создать файл  для Long
                  Files.createFile(filePathString);
              }
              catch (IOException e) {
                  System.err.println(" Не могу создать пустой файл" + filePathString + "\n");
              }

          }

        static void Param(String param) {

              if (param.indexOf(2)== 'd') {
                 System.out.println("Целевая Директория "  + param);
                  destDirectory  = new String(param.substring(2));
              }

        }

        static void  WorkFile (String fileName) {
            // if (is_first_file == true) {
               // в данном релизе пишу  в текущую директорию
           //    is_first_file = false;
           //}


           List<String> lines = new ArrayList<String>();  // буфер строк

           System.out.println("\n Обработка файла " + fileName);

           try {
               lines =  readSourceFile(fileName);
           }
           catch(IOException e) {
               System.err.println(" Не могу прочитать исходный файл" + fileName + "\n");
               e.printStackTrace();
           }


          // Названия буферов
          ByteArrayOutputStream byteLongStream = new ByteArrayOutputStream();
          ByteArrayOutputStream byteDoubleStream = new ByteArrayOutputStream();
          ByteArrayOutputStream byteStringStream = new ByteArrayOutputStream();

          // чтение исходного файла
          try {
              lines =  readSourceFile(fileName);
          }
          catch(IOException e) {

              e.printStackTrace();
          }

          for ( String s : lines) {

           // buffer  for write file
           byte[] cs =   ( s + "\r\n").getBytes();
           System.out.print(s);
           // Write content to file
           switch (ParseStr(s)) {
             case r_long:
                 System.out.print("  - long \n");
                 try {

                     byteLongStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
               case r_double:
                 System.out.print("  - double \n");
                 try {
                     byteDoubleStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
               case r_string:
                 System.out.print("  - String \n");
                 try {
                     byteStringStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
           }
         }

         byte[] tmpLong = byteLongStream.toByteArray();
         byte[] tmpDouble = byteDoubleStream.toByteArray();
         byte[] tmpString = byteStringStream.toByteArray();

         // запись в целевые файлы
         try{
           Files.write(filePathLong, tmpLong, StandardOpenOption.APPEND);
         }
         catch (IOException e) {
           System.err.println(" Не могу записать в файл " + filePathLong + "\n");
         }
         try{
           Files.write(filePathDouble, tmpDouble, StandardOpenOption.APPEND);
         }
         catch (IOException e) {
           System.err.println(" Не могу записать в файл " + filePathDouble + "\n");
         }
           try{
               Files.write(filePathString, tmpString, StandardOpenOption.APPEND);
           }
           catch (IOException e) {
               System.err.println(" Не могу записать в файл " + filePathString + "\n");
           }

       } // WorkFile

       //
       static void ini() {

           // если целевая директория не указана как -dDestDirName
           if (destDirectory == null)
              destDirectory = Paths.get("").toAbsolutePath().toString();

           // Пути до целевых файлов
           filePathLong = Paths.get(destDirectory, "long.res");
           filePathDouble = Paths.get(destDirectory, "double.res");
           filePathString = Paths.get(destDirectory, "String.res");

       } //ini

    } // Work

    public static void main(String[] args) {

        System.out.println("Hello SHIFT!\n");

        // чтение параметров
        for (String s : args) {
            if (s.indexOf("-") == 0)
                Work.Param(s);
        } // s

        // инициализация
        Work.ini();

        // обработка файлов
        for (String s : args) {
            if (s.indexOf("-") != 0)
                Work.WorkFile(s);
        } // s

    }
}
