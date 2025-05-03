package Modul_four;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyLiveLock {
    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        MyLiveLock livelock = new MyLiveLock();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }

    public void operation1() {
        while (true) {
            try {
                lock1.tryLock(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("lock1 заблокирован, пытается получить lock2 ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock2.tryLock()) {
                System.out.println("заблокирован lock2");
            } else {
                System.out.println("Не заблокирован lock2, заблокирован lock1");
                lock1.unlock();
                continue;
            }

            System.out.println("Выполнение первой операции");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }

    public void operation2() {
        while (true) {
            try {
                lock2.tryLock(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("lock2 заблокирован, пытается получить lock1 ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock1.tryLock()) {
                System.out.println("заблокирован lock1");
            } else {
                System.out.println("Не заблокирован lock1, заблокирован lock2");
                lock2.unlock();
                continue;
            }

            System.out.println("Выполнение второй операции");
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}