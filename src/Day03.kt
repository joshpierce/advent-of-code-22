import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day03.txt").readLines()
    // sum up the total of all priorities
    var total: Int = lines.map {     
        // Find the priority by intersecting the two lists of items to get the common item
        // and then running our itemNumber function
        var priority: Int = it.substring(0, it.length / 2).toCharArray().toList()
                   .intersect(it.substring(it.length / 2).toCharArray().toList())
                   .elementAt(0).itemNumber()        
        // Return the priority
        return@map priority        
    }.toMutableList().sum()

    // Solution to Part 1
    println("The total is: " + total.toString())

    // Create an Int Array for the total number of groups of elves
    val groups = IntArray((lines.size / 3)) { it }    
    // Sum up the total of the matching items across each group
    val sum = groups.map {
        // Figure out the base index for the group
        val base = it * 3
        // Find the priority by intersecting all three lists of items from the elves
        return@map lines[base].toCharArray().toList()
                   .intersect(lines[base + 1].toCharArray().toList())
                   .intersect(lines[base + 2].toCharArray().toList())
                   .elementAt(0).itemNumber()
    }.sum()

    // Solution to Part 2
    println("The total of the badge priorities is: " + sum.toString())
}

// This extension function finds the index of the Character inside the alphabet
fun Char.itemNumber(): Int {
    val items = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return items.indexOf(this) + 1
} 