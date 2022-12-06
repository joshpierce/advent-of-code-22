import java.io.File

fun main() {        
    var lines: List<String> = File("Day06.txt").readLines()    
    var characters = lines[0].chunked(1)    
    var foundStartOfPacket = false
    var foundStartOfMessage = false
    for (idx in 0..characters.size) {        
        if (!foundStartOfPacket && characters.slice(idx..idx+3).filter { item -> characters.slice(idx..idx+3).count { it == item } > 1 }.size == 0) {
            println("Start of Packet Marker Detected at # " + (idx.toInt() + 4).toString())
            foundStartOfPacket = true
        }
        if (!foundStartOfMessage && characters.slice(idx..idx+13).filter { item -> characters.slice(idx..idx+13).count { it == item } > 1 }.size == 0) {
            println("Start of Message Marker Detected at # " + (idx.toInt() + 14).toString())
            foundStartOfMessage = true
        }        
        if (foundStartOfPacket && foundStartOfMessage) break
    }
}