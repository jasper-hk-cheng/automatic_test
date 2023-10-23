package tw.com.jasper.automatic_test.junit4api.utils

import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class CaseTemporaryFolder : BaseTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun testTemporaryFolder() {
        val fileContent = "test content..."
        val fileExtName = ".txt"
        with(temporaryFolder) {
            val test01: File = newFile("test01$fileExtName")
            val test02: File = newFolder("testFolder").toPath().resolve("test02$fileExtName").toFile().apply {
                writeText(fileContent)
            }
        }
    }
}

