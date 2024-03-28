package com.rais.chucknorris.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rais.chucknorris.R
import com.rais.chucknorris.adapter.JokesAdapter
import com.rais.chucknorris.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var jokesAdapter: JokesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jokesAdapter = JokesAdapter()

        observeProgressbar()
        searchJokes()
        showViewModel()
        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.rvJokes.setHasFixedSize(true)
        binding.rvJokes.layoutManager = LinearLayoutManager(this)
        binding.rvJokes.adapter = jokesAdapter
    }

    private fun showViewModel() {
        viewModel.listJokes.observe(this) {
            showLoading(true)
            if (it.size != 0) {
                binding.ivNotFound.visibility = View.GONE
                showLoading(false)
                binding.rvJokes.visibility = View.VISIBLE
                Log.d("TAG", "onResponse: ${it}")
                jokesAdapter.setJokesList(it)
            } else {
                binding.ivNotFound.visibility = View.VISIBLE
                showLoading(false)
                binding.rvJokes.visibility = View.GONE
                Toast.makeText(this, "Jokes not found", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun searchJokes() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText.setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchBar.text
                    searchView.hide()
                    searchView.clearFocus()
                    viewModel.searchJokes(searchView.text.toString())
                    binding.ivNotFound.visibility = View.GONE
                    true
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        viewModel.isLoading.observe(this) {
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun observeProgressbar() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
}