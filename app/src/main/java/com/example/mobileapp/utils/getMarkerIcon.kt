import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationType
import com.example.mobileapp.ui.theme.Amber600
import com.example.mobileapp.ui.theme.Blue600
import com.example.mobileapp.ui.theme.Emerald600
import com.example.mobileapp.ui.theme.Gray600
import com.example.mobileapp.ui.theme.Slate600
import com.example.mobileapp.ui.theme.Violet600
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun getMarkerIcon(
    context: Context,
    type: LocationType,
    scale: Float = 1.5f
): BitmapDescriptor {
    val drawableRes = getLocationTypeDrawable(type)  // reuse function

    val drawable = ContextCompat.getDrawable(context, drawableRes)!!.mutate()
    DrawableCompat.setTint(drawable, ContextCompat.getColor(context, android.R.color.white))

    val circleColor = getLocationTypeColor(type)

    val iconWidth = (drawable.intrinsicWidth * 0.6f * scale).toInt()
    val iconHeight = (drawable.intrinsicHeight * 0.6f * scale).toInt()
    val outerSize = Math.max(iconWidth, iconHeight) * 2

    val bitmap = Bitmap.createBitmap(outerSize, outerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val circlePaint = Paint().apply {
        isAntiAlias = true
        color = circleColor.toArgb()
        style = Paint.Style.FILL
    }
    val radius = outerSize / 2f
    canvas.drawCircle(radius, radius, radius, circlePaint)

    val outlinePaint = Paint().apply {
        isAntiAlias = true
        color = 0xFFFFFFFF.toInt()
        style = Paint.Style.STROKE
        strokeWidth = outerSize * 0.07f
    }
    canvas.drawCircle(radius, radius, radius - outlinePaint.strokeWidth / 2f, outlinePaint)

    val left = (outerSize - iconWidth) / 2
    val top = (outerSize - iconHeight) / 2
    drawable.setBounds(left, top, left + iconWidth, top + iconHeight)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun getLocationTypeColor(type: LocationType): Color {
    return when(type) {
        LocationType.SUMMIT -> Emerald600
        LocationType.WATER -> Blue600
        LocationType.SHELTER -> Amber600
        LocationType.VIEWPOINT -> Violet600
        LocationType.OTHER -> Slate600
        LocationType.PARKING -> Gray600
    }
}

fun getLocationTypeDrawable(type: LocationType): Int {
    return when (type) {
        LocationType.SUMMIT -> R.drawable.ic_marker_summit
        LocationType.WATER -> R.drawable.ic_marker_water
        LocationType.SHELTER -> R.drawable.ic_marker_shelter
        LocationType.VIEWPOINT -> R.drawable.ic_marker_viewpoint
        LocationType.OTHER -> R.drawable.ic_marker_other
        LocationType.PARKING -> R.drawable.ic_parking
    }
}
