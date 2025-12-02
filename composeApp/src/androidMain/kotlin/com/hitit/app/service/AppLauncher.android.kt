package com.hitit.app.service

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

actual class AppLauncher(private val context: Context) {

    actual fun openUrl(url: String): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    actual fun canOpenUrl(urlScheme: String): Boolean {
        // First try to check if Deezer package is installed directly
        if (urlScheme.startsWith("deezer")) {
            if (isPackageInstalled(DEEZER_PACKAGE)) {
                return true
            }
        }

        // Fallback: check if any app can handle the intent
        return try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlScheme))
            val packageManager = context.packageManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Android 13+
                val activities = packageManager.queryIntentActivities(
                    intent,
                    PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
                )
                activities.isNotEmpty()
            } else {
                @Suppress("DEPRECATION")
                val activities = packageManager.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                activities.isNotEmpty()
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    companion object {
        private const val DEEZER_PACKAGE = "deezer.android.app"
    }
}
