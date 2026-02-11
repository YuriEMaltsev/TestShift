/*
 
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

        // Имена целевых файлов по умолчанию
        static String fNameLong   = "integers.txt";
        static String fNameDouble = "floats.txt";
        static String fNameString = "strings.txt";

        // префикс для имен файлов
        static String filePrefix;

        // пути до целевых файлов
        static Path filePathLong  ;
        static Path filePathDouble ;
        static Path filePathString ;

         // целевая директория
        static String destDirectory;

        // добавлять в целевые файлы
        static boolean fAppendDescFile = true;

        // полный формат отчета
        static boolean fFullRep = false;

        static int countAll = 0;
        static int countLong = 0;
        static int countDouble = 0;
        static int countString;

        static long  minLong = Long.MAX_VALUE;
        static long  maxLong = Long.MIN_VALUE;
        static long  sumLong = 0;

        static double  minDouble = Double.MAX_VALUE;
        static double  maxDouble = Double.MIN_VALUE;
        static double  sumDouble = 0;

        static int  minString = 2147483647;
        static int  maxString = 0;


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
                sumLong += l;
                if (minLong >= l)
                       minLong = l;
                if (maxLong <= l)
                    maxLong = l;
                countLong++;
                return(r_long);
            } catch (NumberFormatException ignored) {}

            // пробую Double
            try {
                double d = Double.parseDouble(str);
                countDouble++;
                sumDouble += d;
                if (minDouble >= d)
                    minDouble = d;
                if (maxDouble <= d)
                    maxDouble = d;
                return(r_double);
            } catch (NumberFormatException ignored) {}

            // если не long b не double, то String
            int len = str.length();
            if (minString >= len)
                minString = len;
            if (maxString <= len)
                maxString = len;
            countString++;
            return r_string;

        } // ParseStr

          // удаление и создание новых исходных файлов
          static void DescFilesReset(){

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
          } //  DescFilesReset

          // создать целевые файлы, если указана опция -a
          static void CreateIfAppend (){

              try {
                  // создать файл  для Long
                  Files.createFile(filePathLong);
              }
              catch (IOException e) {
              }

              try {
                  // создать файл  для Long
                  Files.createFile(filePathDouble);
              }
              catch (IOException e) {
              }

              try {
                  // создать файл  для Long
                  Files.createFile(filePathString);
              }
              catch (IOException e) {
              }

          } //  CreateIfAppend

        static void Param(String param) {

              //  целевая директория
              if (param.charAt(1)== 'o') {
                  destDirectory  = new String(param.substring(2));
              }

            // дописывать в существующие файлы
              if (param.charAt(1)== 'a')
                fAppendDescFile = false;

            // дописывать в существующие файлы
            if (param.charAt(1)== 'f')
                fFullRep = true;

            if (param.charAt(1)== 'p') {
                filePrefix  = new String(param.substring(2) + "_");
            }

        } // Param

        static void  WorkFile (String fileName) {

           List<String> lines = new ArrayList<String>();  // буфер строк

           System.out.println("Обработка файла " + fileName);

           try {
               lines =  readSourceFile(fileName);
           }
           catch(IOException e) {
               System.err.println(" Не могу прочитать исходный файл" + fileName + "\n");
               e.printStackTrace();
           }

          // String buffers
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

           // Write content to file
           switch (ParseStr(s)) {
             case r_long:
                 try {
                     byteLongStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
               case r_double:
                 try {
                     byteDoubleStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
               case r_string:
                 try {
                     byteStringStream.write(cs);
                 }
                 catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
           }
         }
         // запись в файлы
         // todo реализовать потоковое чтение, что позволит обработать файл любой длины. Deadline...
         byte[] tmpLong = byteLongStream.toByteArray();
         byte[] tmpDouble = byteDoubleStream.toByteArray();
         byte[] tmpString = byteStringStream.toByteArray();
         String mes = ". \n Вероятно вы указали параметр -o c некоректной директорией. ";

         // запись в целевые файлы
         try{
           Files.write(filePathLong, tmpLong, StandardOpenOption.APPEND);
         }
         catch (IOException e) {
           System.err.println(" Не могу записать в файл " + filePathLong + mes +"\n");
         }
         try{
           Files.write(filePathDouble, tmpDouble, StandardOpenOption.APPEND);
         }
         catch (IOException e) {
           System.err.println(" Не могу записать в файл " + filePathDouble + mes+ "\n");
         }
           try{
               Files.write(filePathString, tmpString, StandardOpenOption.APPEND);
           }
           catch (IOException e) {
               System.err.println(" Не могу записать в файл " + filePathString + mes + "\n");
           }

       } // WorkFile

       //
       static void Ini() {

           // если целевая директория не указана как -dDestDirName
           if (destDirectory == null)
              destDirectory = Paths.get("").toAbsolutePath().toString();

           // Пути до целевых файлов
           filePathLong = Paths.get(destDirectory, filePrefix+fNameLong);
           filePathDouble = Paths.get(destDirectory, fNameDouble);
           filePathString = Paths.get(destDirectory, fNameString);

           // Создание или пересоздание  целевых файлов
           if (fAppendDescFile)
                DescFilesReset();
           else
               CreateIfAppend();

       } //ini

      //  Report work
      static void Rep() {
          System.out.println("\n Результат работы: \n");

          System.out.println(" Результат: Строк Integer : " + countLong);
          System.out.println(" Результат: Строк Float   : " + countDouble);
          System.out.println(" Результат: Строк String  : " + countString);

          if (fFullRep) {
              System.out.println("\nInteger min = " + minLong + ",max = "+ maxLong + ",avg = " + sumLong/countLong);
              System.out.println(  "Float min = " + minDouble + ",max = "+ maxDouble + ",avg = " +sumDouble/countDouble);
              System.out.println(  "String minLen = " + minString + ",max = "+ maxString);
          }
      }

    } // Work

    public static void main(String[] args) {

        System.out.println("Hello SHIFT!\n");

        // чтение параметров
        for (int i = 0 ; i < args.length; i++) {
            String s = args[i];
            //if (s.indexOf("-") == 0) {
            // найден префикс комментария
            if (s.charAt(0) == '-') {
               char c = s.charAt(1);
               // он известен, как простой параметр из списка:
               if (c == 'a' || c == 's' || c== 'f')
                Work.Param(s);
               else
                   // он известен, как параметр с аргументом из списка:
                   if  (c == 'p' || c == 'o')
                      // параметр последний и пустойЙ
                      if (i+1 < args.length)
                       Work.Param("-" + c + args[i+1]);
                      else
                        System.out.println("Параметр -"+ c + " указан без аргумента" );
                   else
                       System.out.println("Параметр -"+ c + "не известен" );
            }
        }

        // инициализация
        Work.Ini();

        // обработка файлов
        for (int i = 0 ; i < args.length; i++) {
            String s = args[i];
            if (s.charAt(0) != '-')
                if (i == 0)
                    Work.WorkFile(s);
                else
                    // предыдущий аргумент не параметр?
                    if (args[i-1].charAt(0) != '-')
                        Work.WorkFile(s);
        }

        Work.Rep();


    }
}
