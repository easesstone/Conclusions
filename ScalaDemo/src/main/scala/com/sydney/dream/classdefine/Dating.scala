class DatingData(var fly : Float, var playGame : Float, var iceCream : Float, var typeLike : Int) {
    def this(fly : Float, playGame : Float, iceCream : Float) {
        this(fly, playGame, iceCream, 0)
    }
    override def toString = s"fly: ${fly}, playGame: ${playGame}, iceCream: ${iceCream}, typeLike: ${typeLike} "
}
object Dating {
    def main(args : Array[String]) : Unit = {
        println("starting...")
    }
}
