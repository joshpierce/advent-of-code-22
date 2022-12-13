import java.io.File

fun main() {        
    
    var directions: List<String> = File("Day13.txt").readLines()    
    var pairs = directions.chunked(3).map { Pair(it[0], it[1])}
    var validIndexes: MutableList<Int> = mutableListOf()
    var allPackets: MutableList<Any> = mutableListOf()
    pairs.forEachIndexed { idx, it -> 
        println("== Pair ${idx+1} ==")
        var left = parseList(it.first.substring(1, it.first.length - 1))  
        allPackets.add(left)      
        var right = parseList(it.second.substring(1, it.second.length - 1))        
        allPackets.add(right)
        println("- Compare ${left.toString()} vs ${right.toString()}")
        if(compare(left, right, 0) == 1) {
            validIndexes.add(idx+1)
        }
        println("")
    }
    //Part 1
    println("Sum Of Valid Indexes Is: ${validIndexes.sum()}")

    //Part 2
    val comparator = Comparator { a: Any, b: Any ->
        return@Comparator compare(a as MutableList<Any>, b as MutableList<Any>, 0)
    }
    allPackets.sortWith(comparator)

    var pkt2Index: Int = 0
    var pkt6Index: Int = 0
    
    allPackets.reversed().forEachIndexed { idx, it -> 
        println("${idx+1} - ${it.toString()}")
        if (it.toString() == "[[2]]") pkt2Index = idx + 1
        if (it.toString() == "[[6]]") pkt6Index = idx + 1
    }

    println("The Decoder Key is ${pkt2Index * pkt6Index}")
}

fun compare(left: MutableList<Any>, right: MutableList<Any>, level: Int): Int {
    
    left.forEachIndexed { idx, leftVal ->
        if (right.size < idx + 1) {
            println("Right (${right.toString()}) List Is Shorter (${right.size}) Than Left List, Stopping Checks - INVALID ❌❌❌")
            return -1
        }
        println("${"".padStart(level*2)}- Compare ${leftVal} vs ${right[idx]}")
        if (leftVal is Int && right[idx] is Int) {
            // both Values are ints, make sure that left is <= right to continue
            if (leftVal.toString().toInt() == right[idx].toString().toInt()) {
                return@forEachIndexed
            } else if (leftVal.toString().toInt() < right[idx].toString().toInt()) {
                println("LeftVal: ${leftVal} is < than RightVal: ${right[idx]} - VALID ✅✅✅")
                return 1
            } else {
                println("LeftVal: ${leftVal} is > than RightVal: ${right[idx]} - INVALID ❌❌❌")
                return -1
            }
        } else {
            var leftToCheck: MutableList<Any> = mutableListOf()
            if (leftVal is Int) {
                leftToCheck = mutableListOf(leftVal) 
            } else if (leftVal is MutableList<*>) {
                leftToCheck = leftVal as MutableList<Any>
            }
            var rightToCheck: MutableList<Any> = mutableListOf()
            if (right[idx] is Int) {
                rightToCheck = mutableListOf(right[idx]) 
            } else if (right[idx] is MutableList<*>) {
                rightToCheck = right[idx] as MutableList<Any>
            }
            // The Right Side is a List, Convert the Left Side To A List and Compare
            var innerCheck = compare(leftToCheck, rightToCheck, level + 1)
            if (innerCheck == 0) {
                return@forEachIndexed
            } else {
                return innerCheck
            }
        }
    }

    // According to the Rules we shouldn't get here as our ultimate check??
    if(left.size < right.size) {
        println("Left List Is Smaller Than Right List, But All Items Are The Same - VALID ✅✅✅")
        return 1
    } else {
        return 0
    }
}

fun parseList(str: String): MutableList<Any> {    
    //println("Parser Started for | ${str}")
    var newList: MutableList<Any> = mutableListOf()
    var idx: Int = 0
    while (idx < str.length) {
        //println("Idx ${idx.toString()} | Char ${str.get(idx).toString()}")
        if (str.get(idx) == '[') {
            var endBracketPos = getValidCloseBracket(str.substring(idx, str.length))
            //println("Found Valid Close Bracket | Start ${idx} | End ${endBracketPos}")
            newList.add(parseList(str.substring(idx+1, idx+endBracketPos)))
            idx = idx+endBracketPos + 1
        } else if (listOf(',',']').contains(str.get(idx))) {
            idx++
        } 
        else {
            // println(str)
            //grab addl characters
            var tmpstr = str.get(idx).toString()
            // println("tmpstr ${tmpstr}")
            var tmpidx = idx
            // println("tmpidx ${tmpidx}")
            
            while (str.length > tmpidx+1 && !listOf('[',']',',').contains(str.get(tmpidx+1))) {
                //println("NextChar: ${str.get(tmpidx+1)}")
                tmpstr += str.get(tmpidx+1).toString()
                tmpidx++
            }
            newList.add(tmpstr.toInt())
            idx++
        }
    }
    return newList
}

fun getValidCloseBracket(str: String): Int {
    //println("Getting Close Bracket Position for ${str}")
    var brackets: MutableList<Char> = mutableListOf()
    str.toMutableList().forEachIndexed { idx, char ->
        if (char == '[') {
            brackets.add('[')
        } else if (char == ']') {
            if (brackets.last() == '[') brackets.removeLast()
            if (brackets.size == 0) { return idx }
        }
    }
    return -1
}
