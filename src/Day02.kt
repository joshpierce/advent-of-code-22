import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day02.txt").readLines()
    // setup a new list of Games so we can calculate the outcome of each
    var games: MutableList<Game> = lines.map {
        var parts = it.split(" ")
        return@map Game(parts[0].translateRPS(), parts[1].translateRPS())
    }.toMutableList()

    // calculate the outcome of each game, and sum the scores
    println("The sum of the score of all games is: " + games.sumOf { it.score() }.toString())

    // setup a new list of Games so we can calculate the outcome of each based on the new rules in part 2
    var gamesNew: MutableList<Game> = lines.map {
        var parts = it.split(" ")
        var oppPick = parts[0].translateRPS()        
        var myPick: String
        // Calculate a Loss
        if (parts[1] == "X") {
            myPick = when (oppPick) {
                "Rock" -> "Scissors"
                "Paper" -> "Rock"
                "Scissors" -> "Paper"
                else -> throw Exception ("Invalid Opponent Pick")
            }            
        } 
        // Calculate a Draw
        else if (parts[1] == "Y") {
            myPick = oppPick
        } 
        // Calculate a Win
        else {
            myPick = when (oppPick) {
                "Rock" -> "Paper"
                "Paper" -> "Scissors"
                "Scissors" -> "Rock"
                else -> throw Exception ("Invalid Opponent Pick")
            }            
        }
        return@map Game(parts[0].translateRPS(), myPick)
        
    }.toMutableList()

    // calculate the outcome of each game, and sum the scores
    println("The sum of the score of all games with the new rules is: " + gamesNew.sumOf { it.score() }.toString())
}

class Game(oppPick: String, yourPick: String) {
    var oppPick: String = oppPick
    var yourPick: String = yourPick
}

fun Game.score(): Int {
    var currScore: Int = when (this.yourPick) {
        "Rock" -> 1
        "Paper" -> 2
        "Scissors" -> 3
        else -> throw Exception("Invalid RPS") 
    }

    if (this.oppPick == this.yourPick) {
        currScore += 3
    } else if (this.oppPick == "Rock" && this.yourPick == "Paper" ||
               this.oppPick == "Paper" && this.yourPick == "Scissors" ||
        this.oppPick == "Scissors" && this.yourPick == "Rock") {
        currScore += 6
    } else {
        currScore += 0
    }
        
    return currScore
}

fun String.translateRPS(): String {
    return when (this) {
        "A", "X" -> "Rock"
        "B", "Y" -> "Paper"
        "C", "Z" -> "Scissors"
        else -> throw Exception("Invalid RPS")
    }
}