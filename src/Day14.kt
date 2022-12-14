import java.io.File

//had to refactor this for part 2, didn't have time to do it in a more elegant way
//won't work for part 1 without a little cleaning up
fun main() {        
    
    var directions: List<String> = File("Day14.txt").readLines()    
    var rockScans: MutableList<MutableList<Pair<Int, Int>>> = directions.map rocks@ {
        var rockPoints: MutableList<Pair<Int, Int>> = it.split(" -> ").map points@ {
            val parts = it.split(",")
            return@points Pair(parts[0].toInt(), parts[1].toInt())
        }.toMutableList()
        return@rocks rockPoints
    }.toMutableList()
 
    var rangeX: Pair<Int, Int> = Pair(99999999, 0)
    var rangeY: Pair<Int, Int> = Pair(99999999, 0)
    rockScans.forEach {
        it.forEach {
            if (it.first < rangeX.first) rangeX = Pair(it.first, rangeX.second)
            if (it.first > rangeX.second) rangeX = Pair(rangeX.first, it.first)
            if (it.second < rangeY.first) rangeY = Pair(it.second, rangeY.second)
            if (it.second > rangeY.second) rangeY = Pair(rangeY.first, it.second)
        }
    }

    var sandEntryPoint: Pair<Int, Int> = Pair(500, 0)

    
    rangeX = Pair(rangeX.first, rangeX.second)
    rangeY = Pair(rangeY.first, rangeY.second + 2)

    // Lets draw our Grid
    var grid: MutableList<MutableList<Char>> = MutableList(rangeY.second + 1) { MutableList(rangeX.second+800) { '.' } }

    // Create the floor for part 2
    grid[grid.size-1].forEachIndexed { idx, _ ->
        grid[grid.size-1][idx] = '#'
    }
    
    rockScans.forEachIndexed { _, row ->
        row.forEachIndexed { idx2, cell ->
            grid[cell.second][cell.first] = '#'
            if (row.size-1 > idx2) {
                var newPoint: Pair<Int, Int> = Pair(cell.first,cell.second)
                while (newPoint.second != row[idx2+1].second ||
                newPoint.first  != row[idx2+1].first) {                    
                    if (newPoint.first < row[idx2+1].first) {
                        newPoint = Pair(newPoint.first + 1, newPoint.second)
                    } else if (newPoint.first > row[idx2+1].first) {
                        newPoint = Pair(newPoint.first - 1, newPoint.second)
                    } else if (newPoint.second < row[idx2+1].second) {
                        newPoint = Pair(newPoint.first, newPoint.second + 1)
                    } else if (newPoint.second > row[idx2+1].second) {                        
                        newPoint = Pair(newPoint.first, newPoint.second - 1)
                    }
                    grid[newPoint.second][newPoint.first] = '#'
                }
            }
        }
    }

    grid[sandEntryPoint.second][sandEntryPoint.first] = '+'

    var sands: MutableList<Pair<Int, Int>> = mutableListOf()
    var sandIsFallingIntoTheEternalAbyss: Boolean = false

    while (!sandIsFallingIntoTheEternalAbyss) {
        sands.add(sandEntryPoint)
        var sandIsFalling: Boolean = true
        while (sandIsFalling) {
            var currPos = Pair(sands.last().first.toInt(), sands.last().second.toInt())
            if (currPos.second == grid.size-1) {
                sandIsFalling = false
                sandIsFallingIntoTheEternalAbyss = true
                sands.removeLast()
            } else {
                if (listOf('.','|').contains(grid[currPos.second + 1][currPos.first])) {
                    sands[sands.size-1] = Pair(currPos.first, currPos.second + 1)
                    grid[sands.last().second][sands.last().first] = '|'
                } else if (currPos.first - 1 >= 0 && listOf('.','|').contains(grid[currPos.second + 1][currPos.first - 1])) {
                    sands[sands.size-1] = Pair(currPos.first - 1, currPos.second + 1)
                    grid[sands.last().second][sands.last().first] = '|'
                } else if (currPos.first + 1 <= grid[0].size - 1 && listOf('.','|').contains(grid[currPos.second + 1][currPos.first + 1])) {
                    sands[sands.size-1] = Pair(currPos.first + 1, currPos.second + 1)
                    grid[sands.last().second][sands.last().first] = '|'
                } 
                else {
                    sandIsFalling = false
                    grid[sands.last().second][sands.last().first] = 'o'
                    if (sands.last().second == 0) {
                        sandIsFalling = false
                        sandIsFallingIntoTheEternalAbyss = true
                    }
                }     
            }       
        }        
    }

    printGrid(grid)

    println("")
    println("The total sand that fell before falling into the abyss was : ${sands.size}")
}

fun printGrid(grid: MutableList<MutableList<Char>>) {
    grid.forEach {
        it.forEach {
            print(it)
        }
        println()
    }
}

enum class rockMovingDirection {
    Up, Down, Left, Right
}