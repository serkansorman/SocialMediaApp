package com.citizenme.socialmediaapp.data.local.repository

import com.citizenme.socialmediaapp.data.local.dao.PostDao
import com.citizenme.socialmediaapp.utils.getMockPostEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalPostRepositoryTest {

    @InjectMockKs
    lateinit var localPostRepository: LocalPostRepository

    @RelaxedMockK
    lateinit var postDao: PostDao

    @Before
    fun before() = MockKAnnotations.init(this)

    @Test
    fun `test insert posts`() {
        runBlocking {
            //given
            val mockPosts = mutableListOf(getMockPostEntity())
            coEvery { postDao.insertAllPosts(*mockPosts.toTypedArray()) } just Runs

            //when
            localPostRepository.insertPosts(*mockPosts.toTypedArray())

            //then
            coVerify { postDao.insertAllPosts(*mockPosts.toTypedArray()) }
        }
    }

    @Test
    fun `test get all posts`() {
        runBlocking {
            // given
            coEvery { postDao.getAllPosts() } returns mutableListOf(getMockPostEntity())

            // when
            val expected = localPostRepository.getPosts()

            // then
            coVerify { postDao.getAllPosts() }
            assertEquals(expected, mutableListOf(getMockPostEntity()))
        }
    }
}