import java.util.Arrays;

public class Lab1 {

    public static void main(String[] args) {
        byte i;
        byte j = 0;
        int[] g = new int[(19 - 7) / 2 + 1];
        /* Необходимое количество элементов на промежутке [a;b] = b - a + 1. Т.к. нам необходимы только нечёт, то перед
        добавлением единицы, нужно (b - a) поделить на 2.
         */
        for (i = 7; i <= 19; i++) {
            /* Чтобы не создавать лишние ячейки массива и не использовать вложенные циклы, мы будет заполнять ячейки
            массива только нечётными числа из заданного промежутка. Если i нечёт., то заполняем ячейку, если i чёт.,
            то переходим в следующую.
             */
            if (i % 2 == 1) g[j] = i;
            else j++;
        }
//        System.out.println(Arrays.toString(g));
        float[] x = new float[19];
        for (i = 0; i < 19; i++) x[i] = (float) (Math.random() * 23) - 11;
        /* Заполняем массив псевдослучайными числами, приводя их к типу float, т.к. метод Math.random генерирует
        числа типа double.
         */
//        System.out.println(Arrays.toString(x));
        double[][] p = new double[7][9];
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 9; j++) {
                switch (g[i]) {
                    case 13:
                        p[i][j] = Math.tan(Math.log(Math.pow(Math.cos(x[j]), 2)));
                        break;
                    case 7:
                    case 9:
                    case 11:
                        p[i][j] = Math.pow(((Math.pow(0.5 * (1.0 - x[j]), x[j]) - 0.5) / 0.25), 3);
                        break;
                    default:
                        p[i][j] = Math.asin(Math.sin(Math.log(4.0 * (Math.abs(x[j]) / 5.0))));
                        break;
                }
                System.out.printf("%6.2f", p[i][j]);
            }
        }
    }
}