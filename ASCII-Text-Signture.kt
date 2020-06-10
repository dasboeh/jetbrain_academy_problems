package signature
import java.io.File
import java.util.*
import kotlin.math.abs

class BigFont {
    fun fontReader() :MutableMap<String, MutableList<String>> {
        val inputFile = Scanner(File("D:\\Dokumente\\Fonts\\roman.txt"))
        val fontMap: MutableMap<String, MutableList<String>> = mutableMapOf()

        while (inputFile.hasNext()) {
            val charPlusLength: String = inputFile.nextLine()
            val l: MutableList<String> = mutableListOf()
            // read in the next 10 lines and build a list of them
            for (i in 0..9) {
                l.add(i, inputFile.nextLine())
            }

            // now add the list as value into the map
            val char = charPlusLength[0].toString()
            fontMap[char] = l
        }
        return fontMap
    }
}

class MediumFont {
    val list = mapOf(
            'A' to mapOf(0 to "____", 1 to "|__|", 2 to "|  |"),
            'B' to mapOf(0 to "___ ", 1 to "|__]", 2 to "|__]"),
            'C' to mapOf(0 to "____", 1 to "|   ", 2 to "|___"),
            'D' to mapOf(0 to "___ ", 1 to "|  \\", 2 to "|__/"),
            'E' to mapOf(0 to "____", 1 to "|___", 2 to "|___"),
            'F' to mapOf(0 to "____", 1 to "|___", 2 to "|   "),
            'G' to mapOf(0 to "____", 1 to "| __", 2 to "|__]"),
            'H' to mapOf(0 to "_  _", 1 to "|__|", 2 to "|  |"),
            'I' to mapOf(0 to "_", 1 to "|", 2 to "|"),
            'J' to mapOf(0 to " _", 1 to " |", 2 to "_|"),
            'K' to mapOf(0 to "_  _", 1 to "|_/ ", 2 to "| \\_"),
            'L' to mapOf(0 to "_   ", 1 to "|   ", 2 to "|___"),
            'M' to mapOf(0 to "_  _", 1 to "|\\/|", 2 to "|  |"),
            'N' to mapOf(0 to "_  _", 1 to "|\\ |", 2 to "| \\|"),
            'O' to mapOf(0 to "____", 1 to "|  |", 2 to "|__|"),
            'P' to mapOf(0 to "___ ", 1 to "|__]", 2 to "|   "),
            'Q' to mapOf(0 to "____", 1 to "|  |", 2 to "|_\\|"),
            'R' to mapOf(0 to "____", 1 to "|__/", 2 to "|  \\"),
            'S' to mapOf(0 to "____", 1 to "[__ ", 2 to "___]"),
            'T' to mapOf(0 to "___", 1 to " | ", 2 to " | "),
            'U' to mapOf(0 to "_  _", 1 to "|  |", 2 to "|__|"),
            'V' to mapOf(0 to "_  _", 1 to "|  |", 2 to " \\/ "),
            'W' to mapOf(0 to "_ _ _", 1 to "| | |", 2 to "|_|_|"),
            'X' to mapOf(0 to "_  _", 1 to " \\/ ", 2 to "_/\\_"),
            'Y' to mapOf(0 to "_   _", 1 to " \\_/ ", 2 to "  |  "),
            'Z' to mapOf(0 to "___ ", 1 to "  / ", 2 to " /__"),
            ' ' to mapOf(0 to "    ", 1 to "    ", 2 to "    ")
    )
}

class Calculators {
    fun calcNameWidth(name: String) :Int {
        var nameWidth = 0
        for (char in name) {
            nameWidth += if (char == ' ') {
                10
            } else {
                BigFont().fontReader()[char.toString()]?.get(0)?.length!!
            }
        }
        // add the Spaces which will be added in later output
        return nameWidth
    }

    fun calcStatusWidth(status: String) :Int {
        val mediumFont = MediumFont().list
        var statusWidth = 0
        for (char in status) {
            statusWidth += (mediumFont[char.toUpperCase()]?.get(0)?.length)!!
        }
        // add the Spaces which will be added in later output
        statusWidth += status.length

        return statusWidth
    }

    fun calcMinWidth(name: String, status: String): Int {
        // calc width of BigFont
        val nameWidth = calcNameWidth(name)

        // calc width of MediumFont
        val statusWidth = calcStatusWidth(status)

        return if (nameWidth >= statusWidth) nameWidth else statusWidth
    }

    fun calcFill(name: String, status: String) :Array<Int> {
        val nameWidth = Calculators().calcNameWidth(name)
        val statusWidth = Calculators().calcStatusWidth(status)

        val differ = abs(nameWidth - statusWidth)
        val leftFill: Int
        val rightFill: Int

        when {
            // Differenz zwischen Titel und Status ist gerade
            (differ % 2 == 0) -> {
                // untersscheide ob Titel länger oder Status
                if (nameWidth >= statusWidth) {
                    rightFill = differ / 2
                    leftFill = rightFill
                } else {
                    // rechte Füllung muss 1 kleiner sein, wegen leerzeichen nach Buchstabe
                    leftFill = differ / 2
                    rightFill = leftFill - 1
                }
            }
            // Differenz ist ungerade
            else -> {
                if (nameWidth >= statusWidth) {
                    leftFill = differ / 2
                    rightFill = leftFill + 1
                } else {
                    // rechte Füllung genauso groß wie linke, weil eh schon ein Leerzeichen mehr!
                    leftFill = differ / 2
                    rightFill = leftFill
                }
            }
        }
        return arrayOf(leftFill, rightFill)
    }
}

class LineGens {
    fun genTop(totalWidth: Int, name: String, status: String) {
        println("Enter name and surname: $name")
        println("Enter person's status: $status")
        repeat(totalWidth) {
            print("8")
        }
        print("\n")
    }

    fun genBottom(totalWidth: Int) {
        repeat(totalWidth) {
            print("8")
        }
    }

    fun genStatus(status: String, nameWidth: Int, statusWidth: Int, leftFill: Int, rightFill: Int) {
        var i = 0
        while (i < 3) {
            if (nameWidth >= statusWidth) {
                print("88  ")
                repeat(leftFill) {
                    print(" ")
                }
            } else {
                print("88  ")
            }
            for (char in status) {
                print("${MediumFont().list[char.toUpperCase()]?.get(i)} ")
            }
            if (nameWidth >= statusWidth) {
                repeat(rightFill) {
                    print(" ")
                }
                print("  88\n")
            } else {
                print("  88\n")
            }
            i++
        }
    }

    fun genName(name: String, nameWidth: Int, statusWidth: Int, leftFill: Int, rightFill: Int) {
        var i = 0
        while (i < 10) {
            if (nameWidth >= statusWidth) {
                print("88  ")
            } else {
                print("88  ")
                repeat(leftFill) {
                    print(" ")
                }
            }
            for (char in name) {
                print("${
                    if (char == ' ') {
                        "          "
                    } else {
                        BigFont().fontReader()[char.toString()]?.get(i)
                    }
                }")
            }
            if (nameWidth >= statusWidth) {
                print("  88\n")
            } else {
                repeat(rightFill) {
                    print(" ")
                }
                print("   88\n")
            }
            i++
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val name = scanner.nextLine()
    val status = scanner.nextLine()

    val nameWidth = Calculators().calcNameWidth(name)
    val statusWidth = Calculators().calcStatusWidth(status)

    val leftFill = Calculators().calcFill(name, status)[0]
    val rightFill = Calculators().calcFill(name, status)[1]

    // calc width of picture
    val totalWidth = Calculators().calcMinWidth(name, status) + 8

    // generate top lines
    LineGens().genTop(totalWidth, name, status)

    // generate title
    LineGens().genName(name, nameWidth, statusWidth, leftFill, rightFill)

    // generate status
    LineGens().genStatus(status, nameWidth, statusWidth, leftFill, rightFill)

    // generate bottom lines
    LineGens().genBottom(totalWidth)

}
