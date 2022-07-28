package com.fenascimento.astranews.presentation.ui.home

import androidx.lifecycle.*
import com.fenascimento.astranews.core.Query
import com.fenascimento.astranews.core.RemoteException
import com.fenascimento.astranews.core.State
import com.fenascimento.astranews.data.Post
import com.fenascimento.astranews.data.SpaceFlightNewsCategory
import com.fenascimento.astranews.domain.GetLatestPostsTitleContainsUseCase
import com.fenascimento.astranews.domain.GetLatestPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*


/**
 * Essa classe dá suporte à tela principal (Home).
 */
class HomeViewModel(
    private val getLatestPostsUseCase: GetLatestPostsUseCase,
    private val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase)
    : ViewModel() {

    /**
     * Esse campo controla o texto que será exibido
     * na Toolbar
     */
    private val _categoryTitle = MutableLiveData<String?>(null)
    val categoryTitle : LiveData<String?>
        get() = _categoryTitle

    /**
     * Esse campo controla a visibilidade da progressBar
     */
    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible : LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    /**
     * Esse campo controle a visibilidade do Snackbar
     */
    private val _snackBar = MutableLiveData<String?>(null)
    val snackBar : LiveData<String?>
        get() = _snackBar

    fun onSnackBarShown() {
        _snackBar.value = null
    }

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts(Query(SpaceFlightNewsCategory.ARTICLES.value))
    }

    fun fetchLatest(category: SpaceFlightNewsCategory) {
        fetchPosts(Query(category.value))
    }

    private fun fetchPosts(query: Query) {
        viewModelScope.launch {
            getLatestPostsUseCase(query)
                .onStart {
                    // faca algo no comeco do flow
                    _listPost.postValue(State.Loading)
                    delay(800)
                }

                .catch {
                    // trate uma excecao
                    val exception = RemoteException("unable to connect to SpaceFlight")
                    _listPost.postValue(State.Error(exception))
                    _snackBar.value = exception.message
                }

                .collect { listPost ->
                    _listPost.postValue(State.Success(listPost))
                    _categoryTitle.value = query.type.replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(Locale.getDefault())
                        else it.toString()
                    }
                }
        }
    }

    fun searchPostsTitleContains(searchString : String) {
        fetchPostsTitleContains(Query(_categoryTitle.value.toString(), searchString))
    }

    private fun fetchPostsTitleContains(query: Query) {
        viewModelScope.launch {
            getLatestPostsTitleContainsUseCase(query)
                .onStart {
                    // faca algo no comeco do flow
                    _listPost.postValue(State.Loading)
                }

                .catch {
                    // trate uma excecao
                    val exception = RemoteException("unable to connect to SpaceFlight")
                    _listPost.postValue(State.Error(exception))
                    _snackBar.value = exception.message
                }

                .collect { listPost ->
                    _listPost.postValue(State.Success(listPost))
                    _categoryTitle.value = query.type.replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(Locale.getDefault())
                        else it.toString()
                    }
                }
        }
    }

    val helloText = Transformations.map(listPost) { state ->
        when(state) {
            State.Loading -> {
                "🚀 Loading latest news..."
            }
            is State.Error -> {
                "Houston, we've had a problem!!"
            }
            else -> {""}
        }
    }
}