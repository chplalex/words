package com.chplalex.words.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chplalex.words.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : BottomSheetDialogFragment() {

    private lateinit var searchEditText: TextInputEditText
    private lateinit var clearImageView: ImageView
    private lateinit var searchButton: TextView

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
        searchEditText = view.findViewById(R.id.edit_text_search)
        clearImageView = view.findViewById(R.id.image_view_clear_search)
        searchButton = view.findViewById(R.id.button_search)

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

        fun newInstance() = SearchFragment()
    }
}