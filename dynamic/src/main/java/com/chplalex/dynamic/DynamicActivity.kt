package com.chplalex.dynamic

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chplalex.utils.data.ImageLoader
import com.chplalex.utils.ui.makeGone
import com.chplalex.utils.ui.makeVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.*

class DynamicActivity : AppCompatActivity() {

    private lateinit var layout: SwipeRefreshLayout
    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewExplanation: TextView
    private lateinit var progressIndicator: LinearProgressIndicator

    private var imageLoader = ImageLoader.Glide

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener { loadData() }

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, error -> showError("NASA API request error", error) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dynamic_activity)
        initViews()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }

    private fun initViews() {
        layout = findViewById(R.id.nasa_apod_layout)
        layout.setOnRefreshListener(refreshListener)
        imageView = findViewById(R.id.nasa_apod_image)
        textViewTitle = findViewById(R.id.nasa_apod_title)
        textViewExplanation = findViewById(R.id.nasa_apod_explanation)
        progressIndicator = findViewById(R.id.nasa_apod_progress_indicator)
    }

    private fun loadData() {
        cancelJob()
        coroutineScope.launch {
            val data = nasaApi().getApod()
            runOnUiThread { loadImage(data) }
        }
    }

    private fun loadImage(data: DynamicData) {
        if (imageLoader == ImageLoader.Glide) {
            loadImageByGlide(data)
            imageLoader = ImageLoader.Picasso
        } else {
            loadImageByPicasso(data)
            imageLoader = ImageLoader.Glide
        }
    }

    private fun loadImageByGlide(data: DynamicData) {
        imageView.setImageDrawable(null)
        progressIndicator.makeVisible()
        Glide.with(this)
            .load(data.url)
            .fitCenter()
            .error(R.drawable.ic_load_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressIndicator.makeGone()
                    showError("Image loading error", e ?: Throwable("Unknown error"))
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressIndicator.makeGone()
                    textViewTitle.text = data.title
                    textViewExplanation.text = data.explanation
                    return false
                }
            })
            .into(imageView)
    }

    private fun loadImageByPicasso(data: DynamicData) {
    }

    private fun showError(title: String, error: Throwable) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(error.message)
            .setPositiveButton(R.string.ok_button) { _, _ -> finish() }
            .create()
            .show()
    }

    private fun cancelJob() = coroutineScope.coroutineContext.cancelChildren()
}