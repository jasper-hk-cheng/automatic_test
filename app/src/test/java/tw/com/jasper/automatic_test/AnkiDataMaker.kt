package tw.com.jasper.automatic_test

import java.io.FileInputStream
import java.io.FileOutputStream
import org.junit.jupiter.api.Test

class AnkiDataMaker {

    @Test
    fun makeAllAnkiSentenceCard() {
        for (i in 1..22) {
            // println("unit $i start")
            makeEachSourceData(i, "sentence")
            // println("unit $i end")
        }
    }

    @Test
    fun makeSingleAnkiSentenceCard() {
        makeEachSourceData(1, "sentence")
    }

    @Test
    fun makeAllAnkiVocabularyCard() {
        for (i in 1..22) {
            // println("unit $i start")
            makeEachSourceData(i, "vocabulary")
            // println("unit $i end")
        }
    }

    @Test
    fun makeSingleAnkiVocabularyCard() {
        makeEachSourceData(1, "vocabulary")
    }

    private fun makeEachSourceData(unitNo: Int, fileName: String) {
        fun getSourceFilePath(unitNo: Int): String {
            val padUnitNo = unitNo.toString().padStart(2, '0')
            return "/Users/jasper/Documents/kito_note/English/看一次就懂徹底學會英文句型/unit$padUnitNo/"
        }
        val sourceFilePath = getSourceFilePath(unitNo)
        FileInputStream("${sourceFilePath}$fileName.txt").use { fis ->
            FileOutputStream("${sourceFilePath}${fileName}_anki.txt").use { fos ->
                val fisBufReader = fis.bufferedReader()
                val fosBufWriter = fos.bufferedWriter()
                val lines: List<String> = fisBufReader.readLines()
                for (i in lines.indices step 2) {
                    val qaItem = "${lines[i + 1]}\t${lines[i]}"
                    println(qaItem)
                    fosBufWriter.appendLine(qaItem)
                }
                fosBufWriter.flush()
            }
        }
    }


}