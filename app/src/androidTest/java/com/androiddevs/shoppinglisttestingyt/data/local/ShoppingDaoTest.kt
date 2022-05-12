package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        shoppingDao = database.shoppingDao()
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun insertShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem("banana", 1, 1f, "url", 1)

        shoppingDao.insertShoppingItem(shoppingItem)

        val data = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(data).contains(shoppingItem)
    }

    @Test
    fun deletingShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem("banana", 1, 1f, "url", 1)

        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)

        val data = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(data).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceTest() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("banana", 10, 2f, "url", 1)
        val shoppingItem2 = ShoppingItem("banana", 2, 10f, "url", 2)
        val shoppingItem3 = ShoppingItem("banana", 0, 100f, "url", 3)

        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val data = shoppingDao.observeTotalPrice().getOrAwaitValue()

        assertThat(data).isEqualTo(10 * 2f + 2 * 10f + 0 * 100f)
    }
}