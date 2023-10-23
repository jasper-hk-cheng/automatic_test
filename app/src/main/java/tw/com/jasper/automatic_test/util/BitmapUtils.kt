package tw.com.jasper.automatic_test.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable

object BitmapUtils {
    /**
     * resource id to bitmap
     */
    fun Int.toBitmap(res: Resources): Bitmap = BitmapFactory.decodeResource(res, this)

    /**
     *
     */
    fun isTheSameDrawable(drawableA: Drawable, drawableB: Drawable): Boolean {
        fun Drawable.toBitmap(): Bitmap {
            val newBlankBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(newBlankBitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            return newBlankBitmap
        }
        return drawableA.toBitmap().sameAs(drawableB.toBitmap())
    }
}