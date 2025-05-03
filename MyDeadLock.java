package Modul_four;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyDeadLock {
    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        MyDeadLock deadlock = new MyDeadLock();
        new Thread(deadlock::operation1, "T1").start();
        new Thread(deadlock::operation2, "T2").start();
    }
    public void operation1(){
        lock1.lock();
        System.out.println("lock1 заблокирован, пытается получить lock2 ");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        System.out.println("заблокирован lock2");
        System.out.println("Выполнение первой операции");

        lock2.unlock();
        lock1.unlock();
    }

    public void operation2(){
        lock2.lock();
        System.out.println("lock2 заблокирован, пытается получить lock1 ");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock1.lock();
        System.out.println("Выполнение второй операции");

        lock1.unlock();
        lock2.unlock();
    }

}
