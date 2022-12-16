import java.io.File
import kotlin.collections.fill
import java.math.BigInteger

// So... this returned a couple possible solutions for part 2, the first one that came out ended up being correct
// and it's 01:28a so I'm going to bed. Hopefully I'll find time tomorrow to come back to this to clean it up.
fun main() {        

    var validMin: Int = 0
    var validMax: Int = 4000000
    var rowCheck: Int = 2000000
    
    var directions: List<String> = File("Day15.txt").readLines()    
    var sbpairs: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> = directions.map {
        val parts = it.replace("Sensor at x=", "")
                      .replace(", y=", ",")
                      .replace(": closest beacon is at x=", ",")
                      .split(",")
        Pair(Pair(parts[0].toInt(), parts[1].toInt()), Pair(parts[2].toInt(), parts[3].toInt()))
    }.toMutableList()
    
    // get all x and y values with the same euclidian distance to the beacon in a specified row
    var points: MutableSet<Pair<Int, Int>> = mutableSetOf()
    sbpairs.forEach {
        var possPoints = getPoints(it.first, it.second, rowCheck)
        var beacons = sbpairs.map { it.second }
        possPoints.forEach {
            if (!beacons.contains(it)) {
                points.add(it)
            }
        }
    }
    println("Part 1 Answer: ${points.size}")
    println()

    var possPoints: MutableList<Pair<Int, Int>> = mutableListOf()
    sbpairs.forEach {
        //println("Getting Diamond For ${it.first.toString()} to ${it.second.toString()}")
        possPoints.addAll(getDiamondOutsides(it.first, it.second))       
    }

    // Don't run this for the large grid or you're going to have a bad time
    // var innerPoints: MutableList<Pair<Int, Int>> = mutableListOf()
    // sbpairs.forEach {
    //     innerPoints.addAll(getPoints(it.first, it.second, -1))
    // }
    // for (i in validMin..validMax) {
    //     for (j in validMin..validMax) {
    //         if (sbpairs.map { it.second }.contains(Pair(j, i))) {
    //             print("🟣")
    //         }
    //         else if (sbpairs.map { it.first }.contains(Pair(j, i))) {
    //             print("🟢")
    //         }
    //         else if (innerPoints.contains(Pair(j, i))) {
    //             print("⚫️")
    //         } else {
    //             print("🤪")
    //         }
    //     }
    //     println()
    // }

    // Sort the possible points by x and then y so that we can find duplicates
    possPoints.sortWith(compareBy({ it.first }, { it.second }))
    
    // Run through the list sequentially and tally up the duplicates
    var dups: MutableList<Pair<Pair<Int, Int>, Int>> = mutableListOf()
    var i: Int = 0
    while (i <= possPoints.size - 1) {
        if (possPoints[i].first >= validMin && possPoints[i].first <= validMax && possPoints[i].second >= validMin && possPoints[i].second <= validMax && i < possPoints.size - 1) {
            if (possPoints[i] == possPoints[i+1]) {
                var count = 1
                while (possPoints[i] == possPoints[i+count]) {
                    count++
                }
                
                dups.add(Pair(possPoints[i], count))
                i += (count - 1)
            } else {
                i++
            }
        } else {
            i++
        }
    }
    // Sort the duplicates by the number of duplicates to test the most likely locations first
    dups.sortByDescending({ it.second })
    // Get a map of our sensors and beacons and distances for testing
    var beacons: List<SBPair> = sbpairs.map { SBPair(it.first, it.second, getDistance(it.first, it.second)) }
    var distressLocation: Pair<Int, Int> = Pair(0, 0)
    run dups@ {
        dups.forEach { dup ->
            //println("Testing For Distress Location @ ${dup.first.toString()} | ${dup.second} duplicates")

            var isValid = true
            beacons.forEach beacon@ { beacon ->
                if (getDistance(dup.first, beacon.start) <= beacon.distance) {
                    isValid = false
                    return@beacon
                }
            }
            if (isValid) {
                //println("Found our distress location: ${dup.first.toString()}")
                distressLocation = dup.first
                return@dups
            }
        }
    }

    println("Part 2 Answer: | ${(BigInteger(distressLocation.first.toInt().toString()).multiply(BigInteger("4000000"))).plus(BigInteger(distressLocation.second.toInt().toString()))}")
}

class SBPair(start: Pair<Int, Int>, end: Pair<Int, Int>, distance: Int) {
    var start: Pair<Int, Int> = start
    var end: Pair<Int, Int> = end
    var distance: Int = distance
}

enum class DiamondDirection {
    DOWNLEFT, DOWNRIGHT, UPRIGHT, UPLEFT
}

fun getPointsAround(start: Pair<Int,Int>): MutableList<Pair<Int, Int>> {
    val points: MutableList<Pair<Int, Int>> = mutableListOf()
    points.add(Pair(start.first - 1, start.second))
    points.add(Pair(start.first + 1, start.second))
    points.add(Pair(start.first, start.second - 1))
    points.add(Pair(start.first, start.second + 1))
    return points
}

fun getDistance(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val xDiff = start.first - end.first
    val yDiff = start.second - end.second
    return Math.abs(xDiff) + Math.abs(yDiff)
}

fun getDiamondOutsides(start: Pair<Int, Int>, end: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
    val points: MutableList<Pair<Int, Int>> = mutableListOf()
    // Adding 1 to the distance to get the points just outside the diamond
    val distance = getDistance(start, end) + 1
    var iterator = Pair(0,distance)
    do {
        if (iterator.second != 0) points.add(Pair(start.first + iterator.first, start.second + iterator.second))
        if (iterator.second != 0) points.add(Pair(start.first + iterator.first, start.second - iterator.second))
        if (iterator.first != 0) points.add(Pair(start.first - iterator.first, start.second + iterator.second))
        if (iterator.first != 0) points.add(Pair(start.first - iterator.first, start.second - iterator.second))
        iterator = Pair(iterator.first + 1, iterator.second - 1)
    } while (iterator.second != 0)
    
    return points
}

fun getPoints(start: Pair<Int, Int>, end: Pair<Int, Int>, row: Int): List<Pair<Int, Int>> {
    val points: MutableList<Pair<Int, Int>> = mutableListOf()    
    val distance = getDistance(start, end)  
    
    if (row != -1) {
        for (i in (start.first-distance)..(start.first+distance)) {
            var testXDiff = start.first - i
            var testYDiff = start.second - row
            if (Math.abs(testXDiff) + Math.abs(testYDiff) <= distance) {
                points.add(Pair(i, row))
            }              
        }
    } else {
        for (i in (start.first-distance)..(start.first+distance)) {
            for (j in (start.second-distance)..(start.second+distance)) {
                var testXDiff = start.first - i
                var testYDiff = start.second - j
                if (Math.abs(testXDiff) + Math.abs(testYDiff) <= distance) {
                    points.add(Pair(i, j))
                }              
            }
        }
    }
    return points
}