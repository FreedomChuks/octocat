package com.freedom.breed_list

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.freedom.data.BreedRepository
import com.freedom.model.CatModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: BreedRepository
    private lateinit var viewModel: BreedListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        every { repository.getCatBreed(limit = 10) } returns flowOf(
            PagingData.from(listOf(TestData.fakeCatImageDto))
        )

        viewModel = BreedListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cats emits PagingData from repository`() = runTest(testDispatcher) {
        val pagingData = viewModel.cats.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = object : DiffUtil.ItemCallback<CatModel>() {
                override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel) =
                    oldItem == newItem
            },
            updateCallback = NoopListCallback,
            workerDispatcher = testDispatcher
        )

        differ.submitData(pagingData)
        advanceUntilIdle()

        assertEquals(listOf(TestData.fakeCatImageDto), differ.snapshot().items)
    }

    @Test
    fun `cats requests repository with PAGE_SIZE`() = runTest(testDispatcher) {
        every { repository.getCatBreed(any()) } returns flowOf(PagingData.empty())

        viewModel.cats.first()

        verify { repository.getCatBreed(limit = 10) }
    }

    /**
     * A no-op [ListUpdateCallback] to satisfy [AsyncPagingDataDiffer] in unit tests.
     */
    private object NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) = Unit
        override fun onRemoved(position: Int, count: Int) = Unit
        override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
    }
}