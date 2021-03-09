package com.chplalex.main.description

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chplalex.main.R
import com.chplalex.utils.network.isOnline
import com.chplalex.utils.ui.AlertDialogFragment
import com.chplalex.utils.ui.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.utils.ui.SquareImageView
import com.chplalex.utils.ui.makeInVisible
import com.chplalex.utils.ui.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var descriptionHeader: TextView
    private lateinit var descriptionBody: TextView
    private lateinit var descriptionImage: SquareImageView
    private lateinit var descriptionFooter: TextView
    private lateinit var fragmentLayout: SwipeRefreshLayout
    private lateinit var progress: LinearProgressIndicator

    private var imageLoader = ImageLoader.Picasso

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionbarHomeButtonAsUp()
        initViews(view)
        setData()
    }

    private fun setActionbarHomeButtonAsUp() = activity?.actionBar?.let {
        it.setHomeButtonEnabled(true)
        it.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews(view: View) {
        descriptionHeader = view.findViewById(R.id.description_header)
        descriptionBody = view.findViewById(R.id.description_body)
        descriptionImage = view.findViewById(R.id.description_image)
        descriptionFooter = view.findViewById(R.id.description_footer)
        fragmentLayout = view.findViewById(R.id.fragment_description)
        fragmentLayout.setOnRefreshListener { startLoadingOrShowError() }
        progress = view.findViewById(R.id.description_progress)
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireContext())) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                parentFragmentManager,
                ALERT_DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (fragmentLayout.isRefreshing) {
            fragmentLayout.isRefreshing = false
        }
    }

    private fun setData() {
        descriptionHeader.text = arguments?.getString("word") ?: "no arguments"
        descriptionBody.text = arguments?.getString("description") ?: "no arguments"
        descriptionFooter.text = "(image loaded by %s)".format(imageLoader.loaderName)
        val imageUrl: String? = arguments?.getString("image_url")
        if (imageUrl.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            showProgress()
            when (imageLoader) {
                ImageLoader.Picasso -> {
                    loadImageWithPicasso(descriptionImage, imageUrl)
                    imageLoader = ImageLoader.Glide
                }
                ImageLoader.Glide -> {
                    loadImageWithGlide(descriptionImage, imageUrl)
                    imageLoader = ImageLoader.Picasso
                }
            }
        }
    }

    private fun loadImageWithPicasso(imageView: ImageView, imageLink: String) {
        Picasso.get()
            .load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                    hideProgress()
                }

                override fun onError(e: Exception?) {
                    stopRefreshAnimationIfNeeded()
                    hideProgress()
                    imageView.setImageResource(R.drawable.ic_load_error)
                }
            })
    }

    private fun loadImageWithGlide(imageView: ImageView, imageLink: String) {
        Glide.with(imageView)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    hideProgress()
                    imageView.setImageResource(R.drawable.ic_load_error)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    stopRefreshAnimationIfNeeded()
                    hideProgress()
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_no_photo)
                    .centerCrop()
            )
            .into(imageView)
    }

    private fun showProgress() {
        progress.makeVisible()
    }

    private fun hideProgress() {
        progress.makeInVisible()
    }

    private enum class ImageLoader(val loaderName: String) {
        Picasso("Picasso"),
        Glide("Glide")
    }
}

