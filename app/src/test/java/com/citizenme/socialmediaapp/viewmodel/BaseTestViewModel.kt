package com.citizenme.socialmediaapp.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.citizenme.socialmediaapp.utils.TestCoroutineRule
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseTestViewModel {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var application: Application
}