package design.pattern.in.java.builder;

public final class Car {
    final String carBody;
    final String tyre;
    public Car() {
        this(new Builder());
    }
    public Car(Builder builder) {
        this.carBody = builder.carBody;
        this.tyre = builder.tyre;
    }
    public static final class Builder {
        String carBody;
        String tyre;
        public Builder() {
            this.carBody = "黑色";
            this.tyre = "米其林";
        }
        public Builder carBody(String carBody) {
            this.carBody = carBody;
            return this;
        }
        public Builder tyre(String tyre) {
            this.tyre = tyre;
            return this;
        }
        public Car build() {
            return new Car(this);
        }
    }

    public static void main(String[] args) {
        new Car.Builder().carBody("白色").tyre("米其林").build();
    }
}
