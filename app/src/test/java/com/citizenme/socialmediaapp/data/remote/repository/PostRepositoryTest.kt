package com.citizenme.socialmediaapp.data.remote.repository

import com.citizenme.socialmediaapp.data.remote.service.PostService
import com.citizenme.socialmediaapp.utils.getMockCommentModel
import com.citizenme.socialmediaapp.utils.getMockPostModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PostRepositoryTest {

    @InjectMockKs
    lateinit var postRepository: PostRepository

    @RelaxedMockK
    lateinit var postService: PostService

    @Before
    fun before() = MockKAnnotations.init(this)


    @Test
    fun `test get all posts`() {
        runBlocking {
            // given
            coEvery { postService.getPosts() } returns Response.success(
                mutableListOf(
                    getMockPostModel()
                )
            )

            // when
            val expected = postRepository.getPosts().body()

            // then
            coVerify { postService.getPosts() }
            assertEquals(expected, mutableListOf(getMockPostModel()))
        }
    }


    @Test
    fun `test get comments`() {
        runBlocking {
            // given
            coEvery { postService.getComments(1) } returns Response.success(
                mutableListOf(
                    getMockCommentModel()
                )
            )

            // when
            val expected = postRepository.getComments(1).body()

            // then
            coVerify { postService.getComments(1) }
            assertEquals(expected, mutableListOf(getMockCommentModel()))
        }
    }
}