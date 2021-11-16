package com.citizenme.socialmediaapp.data.local.repository

import com.citizenme.socialmediaapp.data.local.dao.PhotoDao
import com.citizenme.socialmediaapp.utils.getMockPhotoEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalPhotoRepositoryTest {

    @InjectMockKs
    lateinit var localPhotoRepository: LocalPhotoRepository

    @RelaxedMockK
    lateinit var photoDao: PhotoDao

    @Before
    fun before() = MockKAnnotations.init(this)

    @Test
    fun `test insert photos`() {
        runBlocking {
            //given
            val mockPhotos = mutableListOf(getMockPhotoEntity())
            coEvery { photoDao.insertAllPhotos(*mockPhotos.toTypedArray()) } just Runs

            //when
            localPhotoRepository.insertPhotos(*mockPhotos.toTypedArray())

            //then
            coVerify { photoDao.insertAllPhotos(*mockPhotos.toTypedArray()) }
        }
    }

    @Test
    fun `test get all posts`() {
        runBlocking {
            // given
            coEvery { photoDao.getAllPhotos() } returns mutableListOf(getMockPhotoEntity())

            // when
            val expected = localPhotoRepository.getPhotos()

            // then
            coVerify { photoDao.getAllPhotos() }
            assertEquals(expected, mutableListOf(getMockPhotoEntity()))
        }
    }
}