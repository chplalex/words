package com.chplalex.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chplalex.main.R
import com.chplalex.utils.ui.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : BottomSheetDialogFragment() {

    private val searchEditText by viewById<TextInputEditText>(R.id.edit_text_search)
    private val clearImageView by viewById<ImageView>(R.id.image_view_clear_search)
    private val searchButton by viewById<TextView>(R.id.button_search)

    private var onSearchClickListener: OnSearchClickListener? = null

    private val onClearClickListener = View.OnClickListener {
        searchEditText.makeEmpty()
        searchButton.makeDisabled()
    }

    private val onSearchButtonClickListener = View.OnClickListener {
        onSearchClickListener?.onClick(searchEditText.text.toString())
        dismiss()
    }

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (searchEditText.isNullOrEmpty()) {
                searchButton.makeDisabled()
                clearImageView.makeGone()
            } else {
                searchButton.makeEnabled()
                clearImageView.makeVisible()
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton.setOnClickListener(onSearchButtonClickListener)
        searchEditText.addTextChangedListener(textWatcher)
        clearImageView.setOnClickListener(onClearClickListener)
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    interface OnSearchClickListener {

        fun onClick(searchWord: String)
    }

    companion object {
        const val SEARCH_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        fun newInstance() = SearchFragment()
    }
}