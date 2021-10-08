object HelloScala {
    def main(args: Array[String]): Unit = 
        println("Hello World!")

    val float = 1.0

    List(1,2) ::: List(3,4)

    val x = 1
    val y = 2


    def func(name: String) = (x: Int, y: Int) => {
        name match {
            case "add" => x + y
            case _ => 0
        }
    }
}
