package com.chplalex.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.chplalex.navigation.NavGraphDirections
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TranslatorFragment : Fragment() {

    private val navController by inject<NavController> { parametersOf(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ):
        View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController.navigate(NavGraphDirections.actionGlobalFlowMain())
    }
}