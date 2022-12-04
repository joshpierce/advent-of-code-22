import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day04.txt").readLines()

    // read out our section pairs into arrays
    var sectionPairs: List<MutableList<List<Int>>> = lines.map outer@ {
        var sectionPair: MutableList<List<Int>> = it.split(",").map inner@ {
            var section: List<Int> = (it.split("-")[0].toInt()..it.split("-")[1].toInt()).toList()
            return@inner section
        }.toMutableList()
        sectionPair.sortByDescending { it.size }        
        return@outer sectionPair
    }

    var fullyEncapsulated = sectionPairs.filter {
        return@filter it[0][0] <= it[1][0] && it[0][it[0].size - 1] >= it[1][it[1].size - 1]
    }

    println("The number of fully encapsulated sections is: " + fullyEncapsulated.size.toString())

    var overlapping = sectionPairs.filter {
        return@filter it[0].intersect(it[1]).count() > 0
    }

    println("The number of overlapping sections is: " + overlapping.size.toString())
}

