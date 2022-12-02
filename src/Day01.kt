import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day01.txt").readLines()
    // setup a new list of Elves so we can track their meals being carried
    var elves: MutableList<Elf> = mutableListOf()
    // instantiate our first elf
    elves.add(Elf())

    // Add all the meals to the elves
    for (line: String in lines) {
        // When we get to a blank line, we need to instantiate a new elf
        if (line == "") {
            // prints the meals for each elf
            // println(elves.last().meals.map { it.calories }.toString())
            elves.add(Elf())
        } else {
            // add the meal to the last elf in the list (current elf we're tracking)
            elves.last().meals.add(Meal(line.toInt()))
        }        
    }

    // sum up and sort the calories for each elf
    var elvesSummedAndSorted: List<Int> = elves.map { it.meals.sumOf {it.calories} }.sortedDescending()

    // find the elf with the largest sum of calories (the top one)
    var maxCalories: Int = elvesSummedAndSorted.first()    

    // Print out the total calories of the elf with the most
    println("The Most Calories Carried By an Elf is: " + maxCalories.toString())

    // Print out the sum of the top 3 elves calories
    println("The Sum Of The Calories For The Top Three Elves Is: " + elvesSummedAndSorted.take(3).sum().toString())
}

class Elf() {
    var meals: MutableList<Meal> = mutableListOf()
}

class Meal(calories: Int) {
    var calories: Int = calories
}