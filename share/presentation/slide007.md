          _________  ___  ____
         / ___/ __ \/ _ \/ __/
        / /__/ /_/ / // / _/
        \___/\____/____/___/

        Javascript style

        class Car {
          constructor(name, year) {
            this.name = name;
            this.year = year;
          }
          age(x) {
            return x - this.year;
          }
        }

        let date = new Date();
        let year = date.getFullYear();

        let myCar = new Car("Ford", 2014);
        document.getElementById("demo").innerHTML=
        "My car is " + myCar.age(year) + " years old.";
















































































slide 007
