package design.pattern.in.java.singleton;

// Java code for Bill Pugh Singleton Implementaion 
public class GFGBillPugh {

    private GFGBillPugh() {
        // private constructor
    }

    // Inner class to provide instance of class
    private static class BillPughSingleton {
        private static final GFGBillPugh INSTANCE = new GFGBillPugh();
    }

    public static GFGBillPugh getInstance() {
        return BillPughSingleton.INSTANCE;
    }
}