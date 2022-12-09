import java.io.File

// I'm not proud of this, but it works.
fun main() {        
    
    var directions: List<String> = File("Day09.txt").readLines()    
    //Let's start by creating a list of all the moves that need to be made
    var moves: List<Move> = translateDirections(directions)    
    var movesMade: MutableList<String> = mutableListOf("Head and Tail Started at (0, 0)")
    var tailMovesMade: MutableList<String> = mutableListOf("Tail Started at (0, 0)")
    var knotCount: Int = 10   
    var knotLocations: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()
    // Setup our first list of knots
    var firstKnots: MutableList<Pair<Int, Int>> = mutableListOf()
    for (i in 1..knotCount) {
        firstKnots.add(Pair(0, 0))
    }
    knotLocations.add(firstKnots)
    
    moves.forEach { 
        // Uncomment to display the move instructions for the head
        // println(it.number.toString() + " " + it.direction.toString())
        
        // For the number of times the move needs to be made
        for (i in 1..it.number) {
            movesMade.add("Head Moved: " + it.direction.toString())
            // Get the last location
            var lastHeadLocation = knotLocations.last().first().copy()
            
            // Create a new location based on the direction
            var newLocation = when (it.direction) {
                Direction.Up -> Pair(lastHeadLocation.first, lastHeadLocation.second - 1)
                Direction.Down -> Pair(lastHeadLocation.first, lastHeadLocation.second + 1)
                Direction.Right -> Pair(lastHeadLocation.first + 1, lastHeadLocation.second)
                else -> Pair(lastHeadLocation.first - 1, lastHeadLocation.second)
            }                    

            // Based on the location of the head, if the tail isn't touching it, we need to move it
            var lastKnotsLocation: MutableList<Pair<Int, Int>> = mutableListOf()
            lastKnotsLocation.addAll(knotLocations.last())
            lastKnotsLocation[0] = newLocation       
            
            // Start tracking our new knots locations for this move step
            var newLastKnotsLocation = mutableListOf<Pair<Int, Int>>()
            var newTailMovesMade: String = ""
            lastKnotsLocation.forEachIndexed { idx, knot ->
                // we're only working on the tail here
                if (idx == 0) {
                    newLastKnotsLocation.add(knot)
                    return@forEachIndexed
                }
                if (Math.abs(knot.first  - newLastKnotsLocation[idx-1].first)  <= 1 && 
                    Math.abs(knot.second - newLastKnotsLocation[idx-1].second) <= 1) {
                    // The tail is touching the head, so we don't need to move it
                    newLastKnotsLocation.add(knot)
                    newTailMovesMade += if (newTailMovesMade == "") "Knot $idx Didn't Move" else " | Knot $idx Didn't Move"
                } else {                
                    var result = tailFollow(newLastKnotsLocation[idx-1], knot)
                    newLastKnotsLocation.add(result.newTailLocation)
                    newTailMovesMade += if (newTailMovesMade == "") result.moveMade else " | " + result.moveMade                    
                }
            }
            knotLocations.add(newLastKnotsLocation)
            tailMovesMade.add(newTailMovesMade)            
        }        
    }

    // Can be uncommented to "see" the moves that were made and the locations of the knots
    // knotLocations.forEachIndexed { idx, it ->
    //     println(movesMade[idx] + " | Head Location Was: " + it.first().toString() + " | " + tailMovesMade[idx] + " | Trailing Knots Were: " + knotLocations[idx].subList(1, knotLocations[idx].size).toString())
    // }

    println("Total Knots in this Simulation: " + knotCount)
    println("Head Ended at: " + knotLocations.last().first().toString())
    println("Total Tail Locations Visited Was: " + knotLocations.map { it.last() }.distinct().size)
}

enum class Direction {
    Up, 
    Down,
    Right,
    Left
}

class Move(number: Int, direction: Direction) {
    var number: Int = number
    var direction: Direction = direction
}

fun translateDirections(directions: List<String>): List<Move> {
    return directions.map {
        var parts = it.split(" ")
        var dir = when (parts[0]) {
            "U" -> Direction.Up
            "D" -> Direction.Down
            "R" -> Direction.Right
            else -> Direction.Left
        }
        return@map Move(parts[1].toInt(), dir)
    }    
}

class TailFollowResult(newTailLocation: Pair<Int, Int>, moveMade: String) {
    var newTailLocation: Pair<Int, Int> = newTailLocation
    var moveMade: String = moveMade
}

fun tailFollow(head: Pair<Int, Int>, tail: Pair<Int, Int>): TailFollowResult {
    var newTailLocation: Pair<Int, Int> = Pair(0, 0)
    var moveMade: String = ""
    // They share the same column
    if (head.first == tail.first) {
        if (head.second > tail.second) {
            moveMade = "Tail Moved Down"
            newTailLocation = Pair(first = tail.first, second = tail.second + 1)
        } else {
            moveMade = "Tail Moved Up"
            newTailLocation = Pair(first = tail.first, second = tail.second - 1)
        }
    }
    // They share the same row
    if (head.second == tail.second) {
        if (head.first > tail.first) {
            moveMade = "Tail Moved Right"
            newTailLocation = Pair(first = tail.first + 1, second = tail.second)
        } else {
            moveMade = "Tail Moved Left"
            newTailLocation = Pair(first = tail.first - 1, second = tail.second)
        }
    }

    // They are diagonal
    if (head.second != tail.second && head.first != tail.first) {
        if (head.first > tail.first) {
            if (head.second > tail.second) {
                moveMade = "Tail Moved Down and Right"
                newTailLocation = Pair(first = tail.first + 1, second = tail.second + 1)
            } else {
                moveMade = "Tail Moved Up and Right"
                newTailLocation = Pair(first = tail.first + 1, second = tail.second - 1)
            }
        } else {
            if (head.second > tail.second) {
                moveMade = "Tail Moved Down and Left"
                newTailLocation = Pair(first = tail.first - 1, second = tail.second + 1)
            } else {
                moveMade = "Tail Moved Up and Left"
                newTailLocation = Pair(first = tail.first - 1, second = tail.second - 1)
            }
        }
    }

    return TailFollowResult(newTailLocation, moveMade)
}
