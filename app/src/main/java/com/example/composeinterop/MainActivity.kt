package com.example.composeinterop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.compose.ui.platform.ComposeView

class MainActivity : AppCompatActivity() {
    private var appDialog: AppDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.viewBased).setOnClickListener {
            showDialog(layoutInflater.inflate(R.layout.random_layout, null, false))
        }

        findViewById<Button>(R.id.composeBased).setOnClickListener {
            val composeView = ComposeView(this)
            composeView.setContent { SomeComposable() }
            showDialog(composeView)
        }
    }

    fun showDialog(view: View) {
        AppDialog.Builder(this)
            .setCustomView(view)
            .setAutoDismiss(true)
            .show()
    }
}