import java.io.File

// So... this returned a couple possible solutions for part 2, the first one that came out ended up being correct
// and it's 01:28a so I'm going to bed. Hopefully I'll find time tomorrow to come back to this to clean it up.
fun main() {        
    
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
        var possPoints = getPoints(it.first, it.second, 2000000)
        var beacons = sbpairs.map { it.second }
        possPoints.forEach {
            if (!beacons.contains(it)) {
                points.add(it)
            }
        }
    }
    println("Part 1 Answer: ${points.size}")

    // var ranges: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> = sbpairs.map {
    //     val distance = getDistance(it.first, it.second)
    //     Pair(Pair(it.first.first - distance, it.first.first + distance), Pair(it.first.second - distance, it.first.second + distance))
    // }.toMutableList()
    var possPoints: MutableList<Pair<Int, Int>> = mutableListOf()
    sbpairs.forEach {
        possPoints.addAll(getDiamondOutsides(it.first, it.second))       
    }

    var distressLocation: Pair<Int, Int> = Pair(0, 0)
    possPoints.filter({ it.first >= -5 && it.second >= -5 && it.first <= 4000005 && it.second <= 4000005 })
              .forEach poss@ { possPoint ->
        var beacons: List<SBPair> = sbpairs.map { SBPair(it.first, it.second, getDistance(it.first, it.second)) }
        var pointsAround = getPointsAround(possPoint)
        pointsAround.forEach around@ { pointAround ->
            var isValid = true
            beacons.forEach beacon@ { beacon ->
                if (getDistance(pointAround, beacon.start) < beacon.distance) {
                    isValid = false
                    return@beacon
                }
            }
            if (isValid && pointAround.first >= 0 && pointAround.second <= 4000000) {
                println("Found a valid point: ${pointAround.toString()} | ${(pointAround.first.toInt() * 4000000) + pointAround.second.toInt()}")
                distressLocation = pointAround
                return@poss
            }
        }
    }

    println("Part 2 Answer: ${distressLocation.toString()}")
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
    // println("Testing: ${start.toString()} to ${end.toString()}")
    val points: MutableList<Pair<Int, Int>> = mutableListOf()
    val distance = getDistance(start, end)
    // println("Distance: $distance")
    points.add(Pair(start.first, start.second - distance ))
    var direction = DiamondDirection.DOWNLEFT
    // println("Points: ${points.toString()}")
    var i = 0
    do {
        // if (i < 50) {
        //     println("Current Direction: ${direction.toString()}")
        //     println("Current Points: ${points.toString()}")
        //     i++
        // }
        if (direction == DiamondDirection.DOWNLEFT) {
            points.add(Pair(points.last().first - 1, points.last().second + 1))
            
            if (points.last().second == start.second) {
                direction = DiamondDirection.DOWNRIGHT
            }            
        } else if (direction == DiamondDirection.DOWNRIGHT) {
            points.add(Pair(points.last().first + 1, points.last().second + 1))
            
            if (points.last().first == start.first) {
                direction = DiamondDirection.UPRIGHT
            }
        } else if (direction == DiamondDirection.UPRIGHT) {
            points.add(Pair(points.last().first + 1, points.last().second - 1))
            
            if (points.last().second == start.second) {
                direction = DiamondDirection.UPLEFT
            }
        } else if (direction == DiamondDirection.UPLEFT) {
            points.add(Pair(points.last().first - 1, points.last().second - 1))
        }       
    } while(points.last() != Pair(start.first, start.second - distance))
    return points
}

fun getPoints(start: Pair<Int, Int>, end: Pair<Int, Int>, row: Int): List<Pair<Int, Int>> {
    val points: MutableList<Pair<Int, Int>> = mutableListOf()    
    val distance = getDistance(start, end)  
    println("Distance: $distance")  
    
    for (i in (start.first-distance)..(start.first+distance)) {
        var testXDiff = start.first - i
        var testYDiff = start.second - row
        if (Math.abs(testXDiff) + Math.abs(testYDiff) <= distance) {
            points.add(Pair(i, row))
        }              
    }
    return points
}