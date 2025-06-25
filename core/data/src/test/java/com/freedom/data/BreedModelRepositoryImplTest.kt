package com.freedom.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freedom.data.paging.BreedPagingSource
import com.freedom.model.CatModel
import com.freedom.network.NetworkDatasource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BreedModelRepositoryImplTest {
    private val network:NetworkDatasource = mockk()
    private lateinit var repository: BreedRepositoryImpl

    @Before
    fun setUp() {
        repository = BreedRepositoryImpl(network)
    }

    @Test
    fun `load returns Page when networkDatasource succeeds`() = runTest {
        val datasource = mockk<NetworkDatasource>()
        coEvery { datasource.getCatBreed(page = 0, limit = 2) } returns listOf(TestData.fakeCatImageDto)
        val source = BreedPagingSource(networkDatasource = datasource, limit = 2)

        val params = PagingSource.LoadParams.Refresh(key = 0, loadSize = 2, placeholdersEnabled = false)
        val result = source.load(params)


        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page<Int, CatModel>

        assertEquals(1, page.data.size)
        val cat = page.data.first()
        assertEquals("1", cat.id)
        assertEquals("Birman", cat.breedModels[0].name)

        assertNull(page.prevKey)
        assertNull(page.nextKey)
    }

    @Test
    fun `load returns Error when networkDatasource throws IOException`() = runTest {
        val datasource = mockk<NetworkDatasource>()
        coEvery { datasource.getCatBreed(page = any(), limit = any()) } throws IOException("network down")
        val source = BreedPagingSource(networkDatasource = datasource, limit = 5)

        val params = PagingSource.LoadParams.Append(key = 1, loadSize = 5, placeholdersEnabled = false)
        val result = source.load(params)

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `getRefreshKey returns null when no page covers anchorPosition`() {
        val source = BreedPagingSource(mockk(), limit = 4)
        val page1: PagingSource.LoadResult.Page<Int, CatModel> = PagingSource.LoadResult.Page(emptyList(), prevKey = null, nextKey = 1)
        val page2: PagingSource.LoadResult.Page<Int, CatModel> = PagingSource.LoadResult.Page(emptyList(), prevKey = 0,    nextKey = 2)
        val state = PagingState(
            pages = listOf(page1, page2),
            anchorPosition = 5,
            config = PagingConfig(pageSize = 4),
            leadingPlaceholderCount = 0
        )

        val refreshKey = source.getRefreshKey(state)
        assertNull(refreshKey)
    }


}