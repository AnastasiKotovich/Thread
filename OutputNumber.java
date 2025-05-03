package Modul_four;

public class OutputNumber {
    private final String message;
    private static volatile boolean canIGo = true;
    private static final Object lock = new Object();

    public OutputNumber(String name){
        this.message = name;
    }

    public void printNum1(){
        while(true){
            synchronized (lock) {
                while (!canIGo) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println(message);
                canIGo = false;
                lock.notifyAll();
            }
        }
    }

    public void printNum2(){
        while(true){
            synchronized (lock) {
                while (canIGo) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println(message);
                canIGo = true;
                lock.notifyAll();
            }
        }
    }

}
