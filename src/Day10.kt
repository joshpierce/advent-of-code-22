import java.io.File

// I'm not proud of this, but it works.
fun main() {        
    
    var directions: List<String> = File("Day10.txt").readLines()    
    var instructions: MutableList<Int> = mutableListOf()
    instructions.add(1)
    directions.forEach {
        if (it.split(" ")[0] != "noop") {            
            instructions.add(0)
            instructions.add(it.split(" ")[1].toInt())
        } else {
            instructions.add(0)
        }        
    }    

    var totalSignalStrength = IntProgression.fromClosedRange(20, 220, 40).map {
        instructions.take(it).sum() * it
    }.sum()

    println(totalSignalStrength)
    
    var pixels: MutableList<String> = mutableListOf()
    pixels.add("")    
        
    for (i in 1..240) {        
        //println("Cycle $i | Register X is at ${instructions.take(i).sum()} | # Between ${instructions.take(i).sum() - 1} and ${instructions.take(i).sum() + 1} | Test Is ${(i-1) % 40}")    
        pixels[pixels.size - 1] += if (((i-1) % 40) >= (instructions.take(i).sum() - 1) && ((i-1) % 40) <= (instructions.take(i).sum() + 1)) "#" else "."
         
        if (i % 40 == 0) {
            pixels.add("")
        }
    }    

    pixels.forEach {
        println(it)
    }    
}
