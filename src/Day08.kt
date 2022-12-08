import java.io.File

// I'm not proud of this, but it works.
fun main() {        
    var lines: List<String> = File("Day08.txt").readLines()    
    // Setup our Forest List of Lists
    var forest = lines.map { it.chunked(1) }
    // Variable for Tracking Tree Score in Part Two
    var maxTreeScore = 0    
    // Prints out your forest for you to see
    //forest.forEach { it.forEach { print(it+" ") }; println() }

    // Iterate Our Forest Rows
    var visibleForest = forest.mapIndexed { idx, row ->
        // Iterate Our Forest Columns
        row.mapIndexed row@ { idx2, tree ->
            // Get All Trees Above, Below, Left, and Right of Current Tree
            var treesLeft = forest[idx].subList(0, idx2).reversed()
            var treesRight = forest[idx].subList(idx2+1, forest[idx].size)
            var treesAbove = forest.subList(0, idx).map { it[idx2] }.reversed()
            var treesBelow = forest.subList(idx+1, forest.size).map { it[idx2] }   
            // For Part Two, we need to know how many trees are visible from each tree
            var visibleTreesLeft  = treesLeft.takeWhileInclusive  { it < tree }.count()         
            var visibleTreesRight = treesRight.takeWhileInclusive { it < tree }.count()
            var visibleTreesAbove = treesAbove.takeWhileInclusive { it < tree }.count()
            var visibleTreesBelow = treesBelow.takeWhileInclusive { it < tree }.count()
            //println("Tree: $idx, $idx2 -> $visibleTreesLeft, $visibleTreesRight, $visibleTreesAbove, $visibleTreesBelow")
            // For Part Two Calculate The Tree Score and see if it's the new Max Tree Score
            var treeScore = visibleTreesLeft * visibleTreesRight * visibleTreesAbove * visibleTreesBelow
            if (treeScore > maxTreeScore) {
                //println("Tree: $idx, $idx2 -> $treeScore [Current Max Tree Score]")
                maxTreeScore = treeScore
            } else {
                //println("Tree: $idx, $idx2 -> $treeScore")
            }            

            // If this is an edge tree, it's visible
            if (idx == 0 || idx2 == 0 || idx == forest.size - 1 || idx2 == row.size - 1) {
                //println("Edge: $idx, $idx2")
                return@row 1
            } else {
                // If this is not an edge tree, check if it's visible from one of the edges
                if (tree > treesLeft.sortedDescending()[0] ||
                    tree > treesRight.sortedDescending()[0] ||
                    tree > treesAbove.sortedDescending()[0] ||
                    tree > treesBelow.sortedDescending()[0])
                {
                    //println("Not Edge: $idx, $idx2 -> Visible")
                    return@row 1
                } else {
                    //println("Not Edge: $idx, $idx2 -> Not Visible")
                    return@row 0
                }
            }            
        }
    }
    // Print out the Visible Forest
    //visibleForest.forEach { it.forEach { print(it.toString() + " ") }; println() }

    // Display the Total Visible Trees and Max Tree Score
    println("Total Visible Trees: " + visibleForest.map { it.sum() }.sum().toString())
    println("Max Tree Score: " + maxTreeScore.toString())
}

// Added this function which I found here: https://stackoverflow.com/questions/56058246/takewhile-which-includes-the-actual-value-matching-the-predicate-takewhileinclu
// The existing TakeWhile in Kotlin doesn't include the value that matches the predicate, which is what we need for this problem.
fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean) = sequence {
    with(iterator()) {
        while (hasNext()) {
            val next = next()
            yield(next)
            if (!predicate(next)) break
        }
    }
}



