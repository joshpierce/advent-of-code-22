import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day05.txt").readLines()
    var stacks: MutableList<MutableList<String>> = mutableListOf()
    var stacksBetterCrane: MutableList<MutableList<String>> = mutableListOf()
    //Now we need to get our list of rules
    var rulesStarted = false;

    // Let's build our stacks first
    lines.forEach {        
        // Once we reach the empty line, we've calculated all our stacks
        if (it == "") {
            rulesStarted = true
            // We need to reverse our stacks to make this a little easier to work with
            stacks.forEach { it.reverse() }
            // Create a Copy for Part 2
            stacksBetterCrane = stacks.map { it.map { it }.toMutableList() }.toMutableList()
            return@forEach
        }
        if (!it.contains("[") && !rulesStarted) return@forEach
        
        if (!rulesStarted) {
            // Break up the Crate Map into a list of strings (every 4 characters is a crate, trimmed and replace the brackets)
            it.chunked(4).map { it.trim().replace("[", "").replace("]", "") }.forEachIndexed { idx, crate ->     
                // If we don't have this stack yet, create it       
                if (stacks.size < idx + 1) stacks.add(mutableListOf())
                // If the space isn't empty, add the crate to the stack
                if (crate != "") stacks[idx].add(crate.toString())
            }           
        } else {
            // Parse the rule
            var rule = it.replace("move ", "").replace(" from ", ",").replace(" to ", ",").split(",")
        
            // For the Crane9000, we need to move the crates 1 at a time
            for (i in 1..rule[0].toInt()) {                
                stacks[rule[2].toInt()-1].add(stacks[rule[1].toInt() - 1].removeLast())
            }
            
            // For the Crane9001, we need to move the crates in chunks, without altering their order
            var cratesToMove = stacksBetterCrane[rule[1].toInt() - 1].takeLast(rule[0].toInt())
            for (i in 1..rule[0].toInt()) stacksBetterCrane[rule[1].toInt() - 1].removeLast()
            stacksBetterCrane[rule[2].toInt() - 1].addAll(cratesToMove)
        }
    }

    println( "The spelled out word is: " + stacks.map { it.last() }.joinToString(""))
    println( "The spelled out word is: " + stacksBetterCrane.map { it.last() }.joinToString("") )    
}