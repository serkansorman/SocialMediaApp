package com.citizenme.socialmediaapp.viewmodel

import androidx.lifecycle.Observer
import com.citizenme.socialmediaapp.data.local.repository.LocalPhotoRepository
import com.citizenme.socialmediaapp.data.local.repository.LocalPostRepository
import com.citizenme.socialmediaapp.data.remote.repository.PhotoRepository
import com.citizenme.socialmediaapp.data.remote.repository.PostRepository
import com.citizenme.socialmediaapp.model.PostAndPhotoModel
import com.citizenme.socialmediaapp.utils.getMockPostAndPhotoModel
import com.citizenme.socialmediaapp.view.state.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseTestViewModel() {

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var postRepository: PostRepository

    @MockK
    private lateinit var photoRepository: PhotoRepository

    @MockK
    private lateinit var localPostRepository: LocalPostRepository

    @MockK
    private lateinit var localPhotoRepository: LocalPhotoRepository

    @MockK
    private lateinit var viewStateObserver: Observer<ViewState<*>>

    @MockK
    private lateinit var postAndPhotoListObserver: Observer<List<PostAndPhotoModel>>


    private val viewStates = mutableListOf<ViewState<*>>()
    private val postAndPhotoList = mutableListOf<List<PostAndPhotoModel>>()


    private val viewStateSlot = slot<ViewState<*>>()
    private val postListSlot = slot<List<PostAndPhotoModel>>()

    @Before
    fun before() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    postRepository,
                    photoRepository,
                    localPostRepository,
                    localPhotoRepository,
                    application
                )
            )
    }

    @Test
    fun `test get all post and photos`() {
        testCoroutineRule.runBlockingTest {
            // given
            viewModel.viewState.observeForever(viewStateObserver)
            viewModel.postAndPhotoModels.observeForever(postAndPhotoListObserver)

            every { viewStateObserver.onChanged(capture(viewStateSlot)) } answers {
                viewStates.add(
                    viewStateSlot.captured
                )
            }

            every { postAndPhotoListObserver.onChanged(capture(postListSlot)) } answers {
                postAndPhotoList.add(
                    postListSlot.captured
                )
            }

            coEvery { viewModel.getPostsAndPhotos() } returns listOf(getMockPostAndPhotoModel())


            // when
            viewModel.getAllPosts()


            // then
            assertEquals(2, viewStates.size)
            assertTrue(viewStates.first() is ViewState.Loading)
            assertTrue(viewStates[1] is ViewState.Success)
            assertEquals(1, postAndPhotoList.size)
            assertEquals(mutableListOf(getMockPostAndPhotoModel()), postAndPhotoList.first())
        }
    }


}