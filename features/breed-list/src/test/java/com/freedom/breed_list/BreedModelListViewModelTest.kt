package com.freedom.breed_list

import androidx.paging.PagingData
import com.freedom.data.BreedRepository
import com.freedom.model.CatModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedModelListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setupMain() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDownMain() {
        Dispatchers.resetMain()
    }

    private val repository = mockk<BreedRepository>(relaxed = true)
    private lateinit var viewModel: BreedListViewModel

    @Before
    fun setUp() {
        viewModel = BreedListViewModel(repository)
    }

    @Test
    fun `cats emits PagingData from repository`() = runTest {
        val expectedPagingData: PagingData<CatModel> = PagingData.from(listOf(TestData.fakeCatImageDto))

        every { repository.getCatBreed(limit = 10) } returns flowOf(expectedPagingData)


        val actual = viewModel.cats.first()


        assertSame(
            "ViewModel should emit exactly the repository's PagingData",
            expectedPagingData,
            actual
        )
    }

    @Test
    fun `cats requests repository with PAGE_SIZE`() = runTest {
        coEvery { repository.getCatBreed(any()) } returns flowOf(PagingData.empty())

        viewModel.cats.first()

        coVerify { repository.getCatBreed(limit = 10) }
    }
}