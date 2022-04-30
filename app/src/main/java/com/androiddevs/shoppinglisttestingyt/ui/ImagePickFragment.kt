package com.androiddevs.shoppinglisttestingyt.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androiddevs.shoppinglisttestingyt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePickFragment : Fragment(R.layout.fragment_image_pick) {

    private val viewModel: ShoppingViewModel by activityViewModels()
}