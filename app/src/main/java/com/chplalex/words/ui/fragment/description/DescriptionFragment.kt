package com.chplalex.words.ui.fragment.description

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chplalex.words.R
import com.chplalex.words.isOnline
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment
import com.chplalex.words.ui.fragment.alert.AlertDialogFragment.Companion.ALERT_DIALOG_FRAGMENT_TAG
import com.chplalex.words.utils.SquareImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var descriptionHeader: TextView
    private lateinit var descriptionBody: TextView
    private lateinit var descriptionImage: SquareImageView
    private lateinit var fragmentLayout: SwipeRefreshLayout

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
        fragmentLayout = view.findViewById(R.id.layout_fragment_description)
        fragmentLayout.setOnRefreshListener { startLoadingOrShowError() }
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
        arguments?.let {
            descriptionHeader.text = "${it.getString(WORD_EXTRA)} (loaded by ${imageLoader.loaderName})"
            descriptionBody.text = it.getString(DESCRIPTION_EXTRA)
            val imageLink = it.getString(URL_EXTRA)
            if (imageLink.isNullOrBlank()) {
                stopRefreshAnimationIfNeeded()
            } else {
                when (imageLoader) {
                    ImageLoader.Picasso -> {
                        loadImageWithPicasso(descriptionImage, imageLink)
                        imageLoader = ImageLoader.Glide
                    }
                    ImageLoader.Glide -> {
                        loadImageWithGlide(descriptionImage, imageLink)
                        imageLoader = ImageLoader.Picasso
                    }
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
                }
                override fun onError(e: Exception?) {
                    stopRefreshAnimationIfNeeded()
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

    companion object {

        private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
        private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"

        fun newInstance(word: String, description: String, url: String?) = DescriptionFragment().also {
            it.arguments = Bundle().apply {
                putString(WORD_EXTRA, word)
                putString(DESCRIPTION_EXTRA, description)
                putString(URL_EXTRA, url)
            }
        }

    }

    private enum class ImageLoader(val loaderName: String) {
        Picasso("Picasso"),
        Glide("Glide")
    }
}