# Advent of Code 2022

This year I decided I'd learn the [Kotlin](https://kotlinlang.org) language while working on solutions for Advent of Code. 😅 Hopefully I can get past the day 10 hump without life getting in the way this year, but should be a fun learning experience nonetheless. 

If you'd like to follow along, I'll provide the steps below that I took to get this all up and running. This should allow you to go from Fresh Windows install to running the code in this repository.

# Getting Started With Kotlin in VS Code on Windows 11 🏃

## Install the Java JDK

First we'll start by installing the [JDK](https://www.oracle.com/java/technologies/downloads/).

1. Click the link above to go to the JDK download page.  
Note: Currently I've installed the Java 19 JDK.
2. Choose the Windows tab and download the x64 installer.
3. Run the installer and let it take all the default options.

## Install the Kotlin Command-Line Compiler

Next we'll install the [Kotlin Command-Line Compiler](https://github.com/JetBrains/kotlin/releases). 

1. Download the `kotlin-compiler-[version].zip` file from the link above. 
2. Extract the zip file into a folder at `c:\Program Files\kotlinc`. 

## Add The JDK and Kotlin Compiler to Environment Variables

1. Open your start menu and type in `environment variables`, then click `Edit the system environment variables`.
2. Click the button at the bottom labeled `Environment Variables`.
3. Under the section labeled `User variables for ...` click the New Button. 
4. Enter a Variable Name of `JAVA_HOME` and a Variable Value of `c:\Program Files\Java\jdk-19` (note: this assumes you installed version 19).
5. Click OK
6. Under the section labeled `System Variables`, find the variable named `Path`, select it, and click `Edit...`.
7. Click the New button and add `C:\Program Files\kotlinc\bin`. (Make sure that this is the path that contains the kotlinc app).
8. Click the New button again and add `C:\Program Files\Java\jdk-19\bin`. (This again assumes the Java 19 version)
9. Click OK.
10. Click OK again.

## Download and install Git

You'll need to have git installed locally for all this to work. You can download it and install it [here](https://git-scm.com/downloads).

## Restart Windows

This is just the easiest way to make sure your path is updated, it's probably enough to restart any open terminals that you have, but this guarantees that it works well.

## Open Windows Terminal

Next, we'll make sure that your java and kotlin compiler are both installed properly. Open the start menu and type Terminal and open windows terminal. Run the following two commands and you should see similar output to the below:

```bash
javac -version
# javac 19.0.1

kotlinc -version
# info: kotlinc-jvm 1.7.21 (JRE 19.0.1+10-21)
```

As long as you see the above, you should be in good shape.

While we're here, let's run the following code to pull this repository and get it ready to run:

```bash
mkdir gitroot

cd gitroot

git clone https://github.com/joshpierce/advent-of-code-22
```

Now you should have the repository locally to tinker with.

## Download and install VS Code

Next, we'll download and install [Visual Studio (VS) Code](https://code.visualstudio.com/). This should be pretty self explanatory.

## Open the repository in Visual Studio Code

Open up a new Windows Terminal window like above, and run the following commands:

```bash
cd gitroot

cd advent-of-code-22

code .
```

This is shorthand to open VS Code with the scope of whatever folder you're in, it's handy.

## Install a Few VS Code Extensions

- [Code Runner](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner) - This we'll use to run our Kotlin code in VS Code.
- [Kotlin Language](https://marketplace.visualstudio.com/items?itemName=mathiasfrohlich.Kotlin) - This will help us with syntax highlighting and code completion. 
- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) - I don't know if this is needed, but might be nice for Java development. Remember, I'm new here too. 🙃
- [vscode-icons](https://marketplace.visualstudio.com/items?itemName=vscode-icons-team.vscode-icons) - Because they're awesome. ❤️

## Run the Code

Inside VS Code, you should now be able to navigate to the src/DayXX.kt files, and right click on them and choose `Run Code`. Any output from the files should be placed in the output window at the bottom of VS Code.

## If I Forgot Anything

I'm sorry 😞 Send me a message and I'd be happy to help if possible.

# Links 🔗

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]


[^aoc]:
    [Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
    Every year since then, beginning on the first day of December, a programming puzzle is published every day for twenty-five days.
    You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/joshpierce
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
