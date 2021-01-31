package design.pattern.in.java.singleton;

// Java program to create Thread Safe 
// Singleton class 
public class GFGThreadSafe {
    // private instance, so that it can be
    // accessed by only by getInstance() method
    private static GFGThreadSafe instance;

    private GFGThreadSafe() {
        // private constructor
    }

    // synchronized method to control simultaneous access
    synchronized public static GFGThreadSafe getInstance() {
        if (instance == null) {
            // if instance is null, initialize
            instance = new GFGThreadSafe();
        }
        return instance;
    }
}

// - 优点
// 1.可以进行延迟初始化
// 2.是线程安全的

// - 缺点
// 1.由于getInstance()方法是同步的，因此会导致性能降低，因为多个线程无法同时访问它