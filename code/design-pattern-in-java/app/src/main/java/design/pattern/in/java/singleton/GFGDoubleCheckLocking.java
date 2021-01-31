package design.pattern.in.java.singleton;

// Java code to explain double check locking 
public class GFGDoubleCheckLocking {
    // private instance, so that it can be
    // accessed by only by getInstance() method
    private static GFGDoubleCheckLocking instance;

    private GFGDoubleCheckLocking() {
        // private constructor
    }

    public static GFGDoubleCheckLocking getInstance() {
        if (instance == null) {
            // synchronized block to remove overhead
            synchronized (GFGDoubleCheckLocking.class) {
                if (instance == null) {
                    // if instance is null, initialize
                    instance = new GFGDoubleCheckLocking();
                }

            }
        }
        return instance;
    }
}

// - 优点
// 1.可以进行延迟初始化
// 2.是线程安全的
// 3.用synchronized,降低了性能开销

// - 缺点
// 1.第一次，它会影响性能