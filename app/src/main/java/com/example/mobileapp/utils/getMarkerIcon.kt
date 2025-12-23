import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationType
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun getMarkerIcon(
    context: Context,
    type: LocationType,
    scale: Float = 1.5f
): BitmapDescriptor {
    val drawableRes = when(type) {
        LocationType.SUMMIT -> R.drawable.ic_marker_summit
        LocationType.WATER -> R.drawable.ic_marker_water
        LocationType.SHELTER -> R.drawable.ic_marker_shelter
        LocationType.VIEWPOINT -> R.drawable.ic_marker_viewpoint
        LocationType.OTHER -> R.drawable.ic_marker_other
    }

    val drawable = ContextCompat.getDrawable(context, drawableRes)!!.mutate()
    DrawableCompat.setTint(drawable, ContextCompat.getColor(context, android.R.color.white))

    val circleColor = getLocationTypeColor(type)


    val iconWidth = (drawable.intrinsicWidth * 0.6f * scale).toInt()  // smaller icon inside
    val iconHeight = (drawable.intrinsicHeight * 0.6f * scale).toInt()
    val outerSize = Math.max(iconWidth, iconHeight) * 2  // larger circle

    val bitmap = Bitmap.createBitmap(outerSize, outerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Draw main circle (background)
    val circlePaint = Paint().apply {
        isAntiAlias = true
        color = circleColor
        style = Paint.Style.FILL
    }
    val radius = outerSize / 2f
    canvas.drawCircle(radius, radius, radius, circlePaint)

    // Draw white outline
    val outlinePaint = Paint().apply {
        isAntiAlias = true
        color = 0xFFFFFFFF.toInt()
        style = Paint.Style.STROKE
        strokeWidth = outerSize * 0.07f // adjustable outline thickness
    }
    canvas.drawCircle(radius, radius, radius - outlinePaint.strokeWidth / 2f, outlinePaint)

    // Draw the icon centered
    val left = (outerSize - iconWidth) / 2
    val top = (outerSize - iconHeight) / 2
    drawable.setBounds(left, top, left + iconWidth, top + iconHeight)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun getLocationTypeColor(type: LocationType): Int {
    return when(type) {
        LocationType.SUMMIT -> 0xFF059669.toInt() // emerald-600
        LocationType.WATER -> 0xFF2563EB.toInt() // blue-600
        LocationType.SHELTER -> 0xFFF59E0B.toInt() // amber-600
        LocationType.VIEWPOINT -> 0xFF8B5CF6.toInt() // violet-600
        LocationType.OTHER -> 0xFF64748B.toInt() // slate-600
    }
}