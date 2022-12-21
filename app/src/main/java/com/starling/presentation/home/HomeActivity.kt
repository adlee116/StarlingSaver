package com.starling.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.starling.presentation.PresentationError
import com.starling.presentation.transactions.TransactionsActivity
import kotlinx.coroutines.flow.collectLatest
import src.starling.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private lateinit var binding: ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private var adapter: AccountAdapter = AccountAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setObservers()
    }

    private fun setAdapter() {
        binding.accountList.adapter = adapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.accountSelected.collectLatest {
                TransactionsActivity.start(
                    this@HomeActivity,
                    it.first,
                    it.second,
                    "2022-12-15T12:34:56.000Z",
                    "2022-12-22T12:34:56.000Z"
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.failure.collectLatest {
                binding.loadingPanel.isVisible = false
                val error = when(it) {
                    is PresentationError.IntError -> getString(it.error)
                    is PresentationError.StringError -> it.error
                }
                Toast.makeText(this@HomeActivity, error, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.homeState.collectLatest {
                when(it) {
                    HomeViewModel.HomeState.Loading -> binding.loadingPanel.isVisible = true
                    is HomeViewModel.HomeState.Reading -> {
                        binding.loadingPanel.isVisible = false
                        adapter.updateItems(it.accounts)
                    }
                }
            }
        }
    }

}