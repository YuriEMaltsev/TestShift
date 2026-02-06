/*
  Считаем, что максимальная длина строки 255 символов,
  все что больше будет отбрасываться без предупреждения
  если в конце цифр встретились не цифровые символы - это строка
  Читать из файла посимвольно (если будет огромная строка, можно игнорить остаток)
*/

public class  Main {

    /*
    static void  WorkOption (String param) {
        System.out.println("Обработка параметра "  + param);
    }
    */

    // разбор строки
    static short ParseStr(String str) {

       final short r_int    = 1;   // Int
       final short r_double = 2;   // Double
       final short r_string = 3;   // String

        // если не long b не double, то String
       short ret = r_string;

        // пробую Long
        try {
            long l = Long.parseLong(str);
            System.out.println("Int l = " + l);
            return(r_int);
        } catch (NumberFormatException nfe) {
            null;
        }

        // пробую Double
        try {
            double d = Double.parseDouble(str);
            System.out.println("double d = " + d);
            return(r_double);
        } catch (NumberFormatException nfe) {
            //System.out.println("NumberFormatException: " + nfe.getMessage());
        }

        return ret;
    }

    public static void main(String[] args) {
          String test="-1008.0E2";
          short res;
          res = ParseStr(test);
          System.out.printf("\nres="+res);

/*        System.out.printf("Hello SHIFT!\n");

        for (int i = 0; i < args.length; i++) {
            String s;
            if (args[i].indexOf("-") == 0)
                WorkOption(args[i]);
            else
            WorkFile(args[i]);
        }
*/
    }
}
