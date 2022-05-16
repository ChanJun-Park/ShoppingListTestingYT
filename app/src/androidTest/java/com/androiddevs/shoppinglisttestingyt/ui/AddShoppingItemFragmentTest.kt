package com.androiddevs.shoppinglisttestingyt.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.androiddevs.shoppinglisttestingyt.launchFragmentInHiltContainer
import com.androiddevs.shoppinglisttestingyt.repositories.FakeAndroidShoppingRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickInsertToDb_shoppingItemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(FakeAndroidShoppingRepository())

        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shoppingItem"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue()).contains(ShoppingItem("shoppingItem", 5, 5.5f, ""))
    }

    @Test
    fun clickImage_navigateToImagePickerFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.ivShoppingImage)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun onBackPress_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun onBackPress_resetCurImageUrl() {
        val viewModel = ShoppingViewModel(FakeAndroidShoppingRepository())
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            this.viewModel = viewModel
        }

        pressBack()

        val url = viewModel.curImageUrl.getOrAwaitValue()
        assertThat(url).isEmpty()
    }
}