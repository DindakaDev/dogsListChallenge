package com.dindaka.dogslistchallenge.domain.usecase

import com.dindaka.dogslistchallenge.data.model.DogData
import com.dindaka.dogslistchallenge.domain.repository.DogRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetDogsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @RelaxedMockK
    private lateinit var repository: DogRepository

    private lateinit var useCase: GetDogsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetDogsUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should call getDogs when forceRefresh is false`() = testScope.runTest {
        // Given
        val expectedDogs = listOf(DogData(name = "Chief", description = "Black with spots", age = 1, image = ""))
        coEvery { repository.getDogs() } returns Result.success(expectedDogs)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedDogs, result.getOrNull())

        coVerify(exactly = 1) { repository.getDogs() }
        coVerify(exactly = 0) { repository.callGetDogs() }
    }

    @Test
    fun `invoke should call callGetDogs when forceRefresh is true`() = testScope.runTest {
        // Given
        val expectedDogs = listOf(DogData(name = "Chief", description = "Black with spots", age = 1, image = ""))
        coEvery { repository.callGetDogs() } returns Result.success(expectedDogs)

        // When
        val result = useCase(forceRefresh = true)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedDogs, result.getOrNull())

        coVerify(exactly = 1) { repository.callGetDogs() }
        coVerify(exactly = 0) { repository.getDogs() }
    }

    @Test
    fun `invoke should propagate error from getDogs`() = testScope.runTest {
        // Given
        val expectedError = IOException("Network error")
        coEvery { repository.getDogs() } returns Result.failure(expectedError)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }

    @Test
    fun `invoke should propagate error from callGetDogs`() = testScope.runTest {
        // Given
        val expectedError = IOException("API unavailable")
        coEvery { repository.callGetDogs() } returns Result.failure(expectedError)

        // When
        val result = useCase(forceRefresh = true)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }
}