package tw.com.jasper.automatic_test.util

object DataProvider {

    const val ROW_TEXT = "ROW_TEXT"
    private const val ITEM_TEXT_FORMAT = "item: %d"

    fun populateData(dataCount: Int): List<Map<String, String>> = mutableListOf<Map<String, String>>().apply {
        for (i in 0 until dataCount) {
            add(createNewItem(i))
        }
    }

    private fun createNewItem(rowPosition: Int): Map<String, String> = mapOf(
        ROW_TEXT to String.format(ITEM_TEXT_FORMAT, rowPosition)
    )
}
