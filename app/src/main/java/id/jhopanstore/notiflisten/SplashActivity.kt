package id.jhopanstore.notiflisten

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Splash: judul + by JhopanStore, tampil ~1.2 detik lalu masuk MainActivity.
 * Dibangun tanpa layout XML — semuanya programmatic (zero-dependency style app ini).
 */
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#F6F7F9"))
        }

        // kotak logo (lingkaran hijau dengan "N")
        val logoBox = TextView(this).apply {
            text = "N"
            textSize = 40f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            val d = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.parseColor("#1A7F37"))
            }
            background = d
        }
        root.addView(logoBox, LinearLayout.LayoutParams(220, 220).apply { gravity = Gravity.CENTER })

        // judul
        val title = TextView(this).apply {
            text = "NotifListen Payment"
            textSize = 24f
            setTextColor(Color.parseColor("#101828"))
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, 48, 0, 0)
        }
        root.addView(title)

        // subjudul by JhopanStore
        val by = TextView(this).apply {
            text = "by JhopanStore"
            textSize = 14f
            setTextColor(Color.parseColor("#667085"))
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 0)
        }
        root.addView(by)

        setContentView(root)

        // lanjut ke MainActivity setelah 1.2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            // transisi: gak ada animasi khusus, cukup default
        }, 1200)
    }
}
