import java.io.File

fun main() {    
    
    // read the lines from the file
    var lines: List<String> = File("Day06.txt").readLines()
    // split all the characters into an list
    var characters = lines[0].chunked(1)
    var foundStartOfPacket = false
    var foundStartOfMessage = false

    // Iterate through the list of characters testing for unique strings to detect packets
    // Uncomment the printlns to see the iterations in action
    for (i in 0..characters.size-1) {
        var startOfPacketTest = characters.slice(i..i+3)
        var startOfMessageTest = characters.slice(i..i+13)

        // println("Iteration # " + i.toString())
        // println("StartOfPackettest: " + startOfPacketTest.joinToString(""))
        if (!foundStartOfPacket && startOfPacketTest.filter { item -> startOfPacketTest.count { it == item } > 1 }.size == 0) {
            println("Start of Packet Marker Detected at # " + (i.toInt() + 4).toString())
            foundStartOfPacket = true
        }
        // println("StartOfPackettest: " + startOfMessageTest.joinToString(""))
        // println("Filtered: " + startOfMessageTest.filter { item -> startOfMessageTest.count { it == item } > 1 }.joinToString(""))
        if (!foundStartOfMessage && startOfMessageTest.filter { item -> startOfMessageTest.count { it == item } > 1 }.size == 0) {
            println("Start of Message Marker Detected at # " + (i.toInt() + 14).toString())
            foundStartOfMessage = true
        }

        if (foundStartOfPacket && foundStartOfMessage) break
    }

}