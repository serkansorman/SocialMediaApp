package com.citizenme.socialmediaapp.data.local.repository

import com.citizenme.socialmediaapp.data.local.dao.CommentDao
import com.citizenme.socialmediaapp.utils.getMockCommentEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalCommentRepositoryTest {

    @InjectMockKs
    lateinit var localCommentRepository: LocalCommentRepository

    @RelaxedMockK
    lateinit var commentDao: CommentDao

    @Before
    fun before() = MockKAnnotations.init(this)

    @Test
    fun `test insert comments`() {
        runBlocking {
            //given
            val mockComments = mutableListOf(getMockCommentEntity())
            coEvery { commentDao.insertAllComments(*mockComments.toTypedArray()) } just Runs

            //when
            localCommentRepository.insertComments(*mockComments.toTypedArray())

            //then
            coVerify { commentDao.insertAllComments(*mockComments.toTypedArray()) }
        }
    }

    @Test
    fun `test get comments`() {
        runBlocking {
            // given
            coEvery { commentDao.getComments(1) } returns mutableListOf(getMockCommentEntity())

            // when
            val expected = localCommentRepository.getComments(1)

            // then
            coVerify { commentDao.getComments(1) }
            assertEquals(expected, mutableListOf(getMockCommentEntity()))
        }
    }
}