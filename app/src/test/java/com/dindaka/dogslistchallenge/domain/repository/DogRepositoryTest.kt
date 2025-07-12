package com.dindaka.dogslistchallenge.domain.repository

import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogDao
import com.dindaka.dogslistchallenge.data.persistence.db.dog.DogEntity
import com.dindaka.dogslistchallenge.data.remote.ApiService
import com.dindaka.dogslistchallenge.data.remote.dto.DogDto
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DogRepositoryTest{
    @RelaxedMockK
    private lateinit var apiService: ApiService

    @RelaxedMockK
    private lateinit var dogDao: DogDao

    private lateinit var repository: DogRepository

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = DogRepositoryImpl(apiService, dogDao, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getDogs should return local data when available`() = testScope.runTest {
        // Given
        val localDogs = listOf(
            DogEntity(uid = 1, name = "Chief", description = "Black with spots", age = 1, image = ""),
            DogEntity(uid = 2, name = "Spots", description = "White with black spots", age = 2, image = "")
        )
        coEvery { dogDao.getDogs() } returns localDogs

        // When
        val result = repository.getDogs()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Chief", result.getOrNull()?.first()?.name)

        coVerify(exactly = 0) { apiService.getDogs() }
    }

    @Test
    fun `getDogs should call API and save when no local data`() = testScope.runTest {
        // Given
        val apiDogs = listOf(
            DogDto(dogName = "Chief", description = "Black with spots", age = 1, image = ""),
            DogDto(dogName = "Spots", description = "White with black spots", age = 2, image = "")
        )

        coEvery { dogDao.getDogs() } returns emptyList()
        coEvery { apiService.getDogs() } returns apiDogs
        coEvery { dogDao.insertAll(any()) } just Runs

        // When
        val result = repository.getDogs()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)

        coVerify(exactly = 1) { apiService.getDogs() }

        coVerify {
            dogDao.insertAll(withArg { entities ->
                assertEquals(2, entities.size)
                assertEquals("Chief", entities.first().name)
            })
        }
    }

    @Test
    fun `getDogs should return failure when API call fails`() = testScope.runTest {
        // Given
        val expectedException = IOException("Network error")

        coEvery { dogDao.getDogs() } returns emptyList()
        coEvery { apiService.getDogs() } throws expectedException

        // When
        val result = repository.getDogs()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 0) { dogDao.insertAll(any()) }
    }

    @Test
    fun `callGetDogs should return API data and save to DB`() = testScope.runTest {
        // Given
        val apiDogs = listOf(
            DogDto(dogName = "Chief", description = "Black with spots", age = 1, image = ""),
            DogDto(dogName = "Spots", description = "White with black spots", age = 2, image = "")
        )

        coEvery { apiService.getDogs() } returns apiDogs
        coEvery { dogDao.insertAll(any()) } just Runs

        // When
        val result = repository.callGetDogs()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Chief", result.getOrNull()?.first()?.name)

        coVerify {
            dogDao.insertAll(withArg { entities ->
                assertEquals(2, entities.size)
                assertEquals("Chief", entities.first().name)
            })
        }
    }

    @Test
    fun `callGetDogs should return failure when API fails`() = testScope.runTest {
        // Given
        val expectedException = IOException("Timeout")
        coEvery { apiService.getDogs() } throws expectedException

        // When
        val result = repository.callGetDogs()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
        coVerify(exactly = 0) { dogDao.insertAll(any()) }
    }
}