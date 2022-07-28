package com.fenascimento.astranews.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fenascimento.astranews.R
import com.fenascimento.astranews.core.State
import com.fenascimento.astranews.data.SpaceFlightNewsCategory
import com.fenascimento.astranews.databinding.FragmentHomeBinding
import com.fenascimento.astranews.presentation.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModel()
    private val binding : FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initBinding()
        initObserver()
        initAdapter()
        initOptionMenu()
        initSearchBar()

        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initObserver() {
        viewModel.categoryTitle.observe(viewLifecycleOwner) {
            it?.let {
                with(binding.homeToolbar) {
                    title = it
                    searchView.queryHint = it
                }
            }
        }

        viewModel.snackBar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(
                    binding.root,
                    errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initAdapter() {
        val adapter = PostListAdapter()
        binding.homeRv.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) {
            when(it) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(it.result)
                }
            }
        }
    }

    private fun initOptionMenu() {

        with(binding.homeToolbar) {
            inflateMenu(R.menu.options_menu)

            menu.findItem(R.id.action_get_articles).setOnMenuItemClickListener {
                callFetchLatest(SpaceFlightNewsCategory.ARTICLES)
                true
            }

            menu.findItem(R.id.action_get_blogs).setOnMenuItemClickListener {
                callFetchLatest(SpaceFlightNewsCategory.BLOGS)
                true
            }

            menu.findItem(R.id.action_get_reports).setOnMenuItemClickListener {
                callFetchLatest(SpaceFlightNewsCategory.REPORTS)
                true
            }
        }
    }

    private fun callFetchLatest(category : SpaceFlightNewsCategory) {
        searchView.setQuery("", false)
        viewModel.fetchLatest(category)
    }

    private fun initSearchBar() {
        with(binding.homeToolbar) {

            // Recupera o item do menu como SearchView para dar acesso ao campo query
            val searchItem = menu.findItem(R.id.action_home_search)
            searchView = searchItem.actionView as SearchView

            // abrir o campo de busca por padrao, sem o icone da lupa quando for expandido
            searchView.isIconified = false

            // configurando listeners de mudanca de texto
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // extraindo string digitada
                    val searchString = searchView.query.toString()
                    // fazendo busca na API
                    viewModel.searchPostsTitleContains(searchString)
                    // escondendo teclado
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // executando busca a cada modificacao no campo
                    newText?.let { searchString ->
                        viewModel.searchPostsTitleContains(searchString)
                    }
                    return true
                }

            })
        }
    }
}