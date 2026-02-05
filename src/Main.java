import com.sun.org.apache.xpath.internal.objects.XString;

public class  Main {

    static void  WorkOption (String param) {
        System.out.println("Обработка параметра "  + param);
    }

    // разбор строки
    static short ParseStr(String str) {
       final short max_double = 4; // максимальная точность double
       final short a_minus = 45;  // "-"
       final short a_point = 46;  // "."
       final short a_0 = 48;      // "0"
       final short a_9 = 57;      // "9"

       short ret = 3;        // код завершения 1-long, 2-double, 3- String
       short f_lead_minus=0; // встречен лидирующий минус
       short f_digit = 0;    // 1 -встречена цифра
       short f_point = 0;    // 1 -встречена точка
       short f_count_p =0;   // 1 -17 - количество десятичных знаков показателя степени точки
       short f_e = 0;        // 1 -встречена E или e (показатель степени)
       short f_e_minus=0;    // 1 -встречен  минус после E
       short f_count_e=0;    // 1,2 - количество десятичных знаков показателя степени после E-

       int a=0; // код ASCII
       int i=0;
       for ( ; i< str.length(); i++) {
           a = (int) str.charAt(i);
           if (i == 0 || a == a_minus) { // встречен лидирующий минус
               f_lead_minus = 1;
               continue;
           }
           if (a >= a_0 && a <= a_9) { // встречен символы 0..9
               f_digit = 1;
               if (f_point == 1) f_count_p++;
               if (f_count_p > max_double) break; // максимальная точность double
               continue;
           }
           else if (a == a_point){
               if(f_point == 1) break; // если встречена вторая точка - выход
               f_point = 1;
               continue;
           }
           // если не обработано - это строка!
           break ;
       }

       // проверка флагов, если просмотрены все символы
       if (i == str.length()) {
           if ( f_point == 0 )
             ret = 1;
           else {
             if (f_count_p >0)
                 ret = 2;
           }
       }
       return ret;
    }

    public static void main(String[] args) {
          String test="-1008.96";
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
/*
  Считаем, что максимальная длина строки 255 символов,
  все что больше будет отбрасываться без предупреждения
  если в конце цифр встретились не цифровые символы - это строка
  Читать из файла посимвольно (если будет огромная строка, можно игнорить остаток)


  Тип double в Java — это 64-битное число с плавающей запятой (стандарт IEEE 754),
  обеспечивающее точность примерно 15–17 значимых десятичных цифр.
  Диапазон значений составляет от \(4.9\times 10^{-324}\) до \(1.7976931348623157\times 10^{308}\)
*/