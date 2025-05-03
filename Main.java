package Modul_four;
import java.lang.*;

public class Main {
    static int number;
    public static void main(String[] args) {
        OutputNumber number1 = new OutputNumber("1");
        OutputNumber number2 = new OutputNumber("2");

        new Thread(number1::printNum1).start();
        new Thread(number2::printNum2).start();
    }
}

