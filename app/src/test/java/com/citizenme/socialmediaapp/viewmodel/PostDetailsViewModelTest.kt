package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.Observer
import com.citizenme.socialmediaapp.data.local.repository.LocalCommentRepository
import com.citizenme.socialmediaapp.data.remote.repository.PostRepository
import com.citizenme.socialmediaapp.model.CommentModel
import com.citizenme.socialmediaapp.utils.getMockCommentEntity
import com.citizenme.socialmediaapp.utils.getMockCommentModel
import com.citizenme.socialmediaapp.view.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostDetailsViewModelTest : BaseTestViewModel() {

    private lateinit var viewModel: PostDetailsViewModel

    @MockK
    private lateinit var postRepository: PostRepository

    @MockK
    private lateinit var localCommentRepository: LocalCommentRepository


    @MockK
    private lateinit var viewStateObserver: Observer<ViewState<*>>

    @MockK
    private lateinit var commentListObserver: Observer<List<CommentModel>>


    private val viewStates = mutableListOf<ViewState<*>>()
    private val commentList = mutableListOf<List<CommentModel>>()


    private val viewStateSlot = slot<ViewState<*>>()
    private val commentListSlot = slot<List<CommentModel>>()

    @Before
    fun before() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(PostDetailsViewModel(postRepository, localCommentRepository, application))
    }


    @Test
    fun `test get comments`() {
        testCoroutineRule.runBlockingTest {
            // given
            viewModel.viewState.observeForever(viewStateObserver)
            viewModel.commentList.observeForever(commentListObserver)

            every { viewStateObserver.onChanged(capture(viewStateSlot)) } answers {
                viewStates.add(
                    viewStateSlot.captured
                )
            }

            every { commentListObserver.onChanged(capture(commentListSlot)) } answers {
                commentList.add(
                    commentListSlot.captured
                )
            }

            coEvery { postRepository.getComments(1) } returns Response.success(
                mutableListOf(
                    getMockCommentModel()
                )
            )

            coEvery { localCommentRepository.getComments(1) } returns
                    mutableListOf(
                        getMockCommentEntity()
                    )

            coEvery { localCommentRepository.insertComments(*mutableListOf(getMockCommentEntity()).toTypedArray()) } just Runs


            // when
            viewModel.getComments(1)


            // then
            Assert.assertEquals(2, viewStates.size)
            Assert.assertTrue(viewStates.first() is ViewState.Loading)
            Assert.assertTrue(viewStates[1] is ViewState.Success)
            Assert.assertEquals(1, commentList.size)
            Assert.assertEquals(mutableListOf(getMockCommentModel()), commentList.first())
        }
    }

}