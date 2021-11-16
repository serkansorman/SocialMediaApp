package com.citizenme.socialmediaapp.data.remote.repository

import com.citizenme.socialmediaapp.data.remote.service.PhotoService
import com.citizenme.socialmediaapp.data.remote.service.PostService
import com.citizenme.socialmediaapp.utils.getMockPhotoModel
import com.citizenme.socialmediaapp.utils.getMockPostModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Response

class PhotoRepositoryTest {

    @InjectMockKs
    lateinit var photoRepository: PhotoRepository

    @RelaxedMockK
    lateinit var photoService: PhotoService

    @Before
    fun before() = MockKAnnotations.init(this)

    @Test
    fun `test get photos`() {

        runBlocking {
            //given
            coEvery { photoService.getPhotos() } returns Response.success(
                mutableListOf(
                    getMockPhotoModel()
                )
            )

            //when
            val expected = photoRepository.getPhotos().body()

            //then
            coVerify { photoService.getPhotos() }
            assertEquals(expected, mutableListOf(getMockPhotoModel()))
        }

    }
}