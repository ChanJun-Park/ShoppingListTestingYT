package com.androiddevs.shoppinglisttestingyt.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androiddevs.shoppinglisttestingyt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel: ShoppingViewModel by activityViewModels()

}