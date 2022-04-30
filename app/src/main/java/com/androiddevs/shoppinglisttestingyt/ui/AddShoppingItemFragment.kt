package com.androiddevs.shoppinglisttestingyt.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.androiddevs.shoppinglisttestingyt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    private val viewModel: ShoppingViewModel by activityViewModels()
}