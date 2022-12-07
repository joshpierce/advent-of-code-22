import java.io.File

// I'm not proud of this, but it works.
fun main() {        
    var lines: List<String> = File("Day07.txt").readLines()    
    var fileSystem: MutableList<Obj> = mutableListOf()    
    fileSystem.add(Obj("/", ObjectType.Directory, 0))
    var currentObj: Obj = fileSystem[0]
    for (i in 0..lines.size - 1) {
        //println("Current Line: " + lines[i])
        //currentObj.children.forEach { println("Object under " + currentObj.objName + " | " + it.objName + " | " +it.objType.toString() + " | " + it.parent!!.objName) }
        var parts = lines[i].split(" ")
        when (parts[0]) {
            "$" -> {
                when (parts[1]) {
                    "cd" -> {
                        when (parts[2]) {
                            "/" -> {
                                // Go to root               
                                //println("Changing current obj to root")                 
                                currentObj = fileSystem[0]
                            }
                            ".." -> {                           
                                //println("Changing current obj to parent " + currentObj.parent!!.objName)     
                                currentObj = currentObj.parent!!
                            }
                            else -> {                 
                                var target = currentObj.children.filter { it.objName == parts[2] }.first()
                                //println("Changing current obj to " + target.objName)
                                currentObj = target
                            }
                        }
                    }
                    "ls" -> {
                        //println("Listing directory: " + currentObj.objName)
                        var nextCommandLine = lines.slice(i+1..lines.size - 1).indexOfFirst { it.split(" ")[0] == "$" } + i + 1  
                        if (i == nextCommandLine) {
                            nextCommandLine = lines.size
                        }   
                        //println("nextCommandLine is " + nextCommandLine.toString())      
                        //println("i is " + i.toString())      
                               
                        for (idx in i+1..nextCommandLine - 1) {
                            var lsParts = lines[idx].split(" ")
                            when (lsParts[0]) {
                                "dir" -> {                                    
                                    if (currentObj.children.filter { it.objName == lsParts[1] && it.objType == ObjectType.Directory }.count() == 0) {                                        
                                        //println("Adding directory: " + lsParts[1] + " to " + currentObj.objName)
                                        currentObj.children.add(Obj(lsParts[1], ObjectType.Directory, 0, currentObj))                                        
                                    }                                    
                                }    
                                else -> {                                    
                                    if (currentObj.children.filter { it.objName == lsParts[1] && it.objType == ObjectType.File }.count() == 0) {                                      
                                        //println("Adding file: " + lsParts[1] + " to " + currentObj.objName)
                                        currentObj.children.add(Obj(lsParts[1], ObjectType.File, lsParts[0].toInt(), currentObj))
                                    }                                    
                                }
                            }
                            
                        }
                    }
                }
            }                    
            else -> {
                // This is a file
                print("")
            }
        }
    }
    println("------------")
    printStructure(fileSystem[0], 0)
    var sizes = getDirectoriesWithSizes(fileSystem[0])    
    sizes.sortByDescending { it.size }
    sizes.forEach { println(it.path + " | " + it.size.toString()) }    
    println("Part 1 size: " + sizes.filter { it.size < 100000 }.sumOf { it.size }.toString())

    var freeSpace = 70000000 - sizes[0].size
    println("Total Free Space is " + freeSpace.toString())
     var validSizes = sizes.filter { it.size + freeSpace > 30000000 }.toMutableList()
    validSizes.sortBy { it.size }
    validSizes.forEach {
        println("Valid Size: " + it.path + " | " + it.size.toString())
    }
    println("Smallest Directory To Achieve Goal is " + validSizes[0].path.toString() + " | " + validSizes[0].size.toString())

}

fun printStructure(obj: Obj, level: Int) {
    var indent = ""
    for (i in 0..level) {
        indent += "  "
    }
    indent += "- "
    if (obj.objType == ObjectType.Directory) {
        println(indent + obj.objName)

    } else {
        println(indent + obj.objName + " | " + obj.objSize.toString())
    }    
    obj.children.forEach {
        printStructure(it, level + 1)
    }
}

fun getDirectoriesWithSizes(obj: Obj): MutableList<DirectoryInfo> {
    var directorySizes: MutableList<DirectoryInfo> = mutableListOf()
    if (obj.objType == ObjectType.Directory) {
        directorySizes.add(DirectoryInfo(obj.objName, 0))
    }
    obj.children.forEach {
        if (it.objType == ObjectType.Directory) {
            directorySizes.add(DirectoryInfo(it.objName, 0))
        } else {
            directorySizes[0].size += it.objSize
        }
    }
    obj.children.forEach {
        if (it.objType == ObjectType.Directory) {
            var childSizes = getDirectoriesWithSizes(it)
            directorySizes[0].size += childSizes[0].size
            directorySizes.addAll(childSizes)
        }
    }
    return directorySizes.filter { it.size > 0 }.toMutableList()
}

enum class ObjectType {
    Directory, 
    File
}

class DirectoryInfo(path: String, size: Int) {
    var path: String = path
    var size: Int = size
}

class Obj(objName: String, objType: ObjectType, objSize: Int, parent: Obj? = null) {
    var objName: String = objName
    var objType: ObjectType = objType
    var objSize: Int = objSize
    var children: MutableList<Obj> = mutableListOf()
    var parent: Obj? = parent
}



