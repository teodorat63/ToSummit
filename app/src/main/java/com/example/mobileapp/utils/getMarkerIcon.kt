import androidx.compose.ui.res.painterResource
import com.google.android.gms.maps.model.BitmapDescriptor
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.mobileapp.R
import com.example.mobileapp.data.model.LocationType
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

    val width = (drawable.intrinsicWidth * scale).toInt()
    val height = (drawable.intrinsicHeight * scale).toInt()

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
