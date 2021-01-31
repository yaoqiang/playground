package design.pattern.in.java.singleton;

//Java Code to create singleton class 
// With Lazy initialization 
public class GFGEagerLazy {
  // private instance, so that it can be
  // accessed by only by getInstance() method
  private static GFGEagerLazy instance;

  private GFGEagerLazy() {
    // private constructor
  }

  // method to return instance of class
  public static GFGEagerLazy getInstance() {
    if (instance == null) {
      // if instance is null, initialize
      instance = new GFGEagerLazy();
    }
    return instance;
  }
}

// - 优点
// 1.仅在需要时才创建对象。它可以克服资源浪费和CPU时间的浪费
// 2.方法中也可以进行异常处理

// - 缺点
// 1.每次必须检查null条件
// 2.实例无法直接访问
// 3.在多线程环境中，它可能会破坏单例属性