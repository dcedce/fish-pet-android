package com.example.fishpet

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var windowManager: WindowManager
    private var petView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // 1. 检查并申请悬浮窗权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(intent)
        } else {
            showFloatingPet()
        }
    }

    private fun showFloatingPet() {
        if (petView != null) return

        // 2. 创建大肥鱼的图像
        petView = ImageView(this).apply {
            setImageResource(android.R.drawable.ic_menu_gallery) 
            layoutParams = WindowManager.LayoutParams(
                400, 400,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.TOP or Gravity.START
                x = 100
                y = 100
            }
        }

        // 3. 把大肥鱼加到屏幕上
        windowManager.addView(petView, petView?.layoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        petView?.let { windowManager.removeView(it) }
    }
}
