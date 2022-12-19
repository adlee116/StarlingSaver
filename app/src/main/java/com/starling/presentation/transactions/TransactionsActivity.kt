package com.starling.presentation.transactions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.starling.core.formatPound
import com.starling.core.penniesToRoundUp
import com.starling.presentation.PresentationError
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import src.starling.databinding.ActivityTransactionsBinding

private lateinit var binding: ActivityTransactionsBinding

class TransactionsActivity : AppCompatActivity() {

    private val viewModel: TransactionsViewModel by viewModel()
    private var adapter: TransactionsAdapter = TransactionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setClickListeners()
        setObservers()
        getData()
    }

    private fun setClickListeners() {
        binding.submitButton.setOnClickListener {
            viewModel.process(TransactionsViewModel.TransactionsEvent.SubmitClicked)
        }
    }

    private fun getData() {
        val accountUid = intent.getStringExtra(ACCOUNT_UID)
        val categoryUid = intent.getStringExtra(CATEGORY_UID)
        val minTransactionTimestamp = intent.getStringExtra(MIN_TRANSACTION_TIME_STAMP)
        val maxTransactionTimestamp = intent.getStringExtra(MAX_TRANSACTION_TIME_STAMP)

        if (accountUid != null && categoryUid != null && minTransactionTimestamp != null && maxTransactionTimestamp != null) {
            viewModel.process(
                TransactionsViewModel.TransactionsEvent.Initialise(
                    accountUid, categoryUid, minTransactionTimestamp, maxTransactionTimestamp
                )
            )
        } else {
            unableToGetTransactionsError()
        }
    }

    // Update to collect the
    private fun setTotal(transactions: List<TransactionsListItem>) {
        binding.total.text = transactions.sumOf { it.amount.penniesToRoundUp() }.formatPound()
    }

    private fun unableToGetTransactionsError() {
        Toast.makeText(this, "Unable to get transaction information", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun setAdapter() {
        binding.transactionsRecyclerView.adapter = adapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.savingsAdded.collectLatest {
                Toast.makeText(this@TransactionsActivity, "Successfully saved to savings goal", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.failure.collectLatest {
                val error = when (it) {
                    is PresentationError.IntError -> getString(it.error)
                    is PresentationError.StringError -> it.error
                }
                Toast.makeText(this@TransactionsActivity, error, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.homeState.collectLatest {
                when (it) {
                    TransactionsViewModel.TransactionsState.Loading -> binding.progress.show()
                    is TransactionsViewModel.TransactionsState.Reading -> {
                        binding.progress.hide()
                        adapter.updateItems(it.transactions)
                        setTotal(it.transactions)
                    }
                }
            }
        }
    }

    companion object {

        private const val ACCOUNT_UID = "accountUid"
        private const val CATEGORY_UID = "categoryUid"
        private const val MIN_TRANSACTION_TIME_STAMP = "minTransactionTimestamp"
        private const val MAX_TRANSACTION_TIME_STAMP = "maxTransactionTimestamp"

        fun start(
            context: Context,
            accountUid: String,
            categoryUid: String,
            minTransactionTimestamp: String,
            maxTransactionTimestamp: String
        ) = context.startActivity(
            Intent(context, TransactionsActivity::class.java).apply {
                putExtra(ACCOUNT_UID, accountUid)
                putExtra(CATEGORY_UID, categoryUid)
                putExtra(MIN_TRANSACTION_TIME_STAMP, minTransactionTimestamp)
                putExtra(MAX_TRANSACTION_TIME_STAMP, maxTransactionTimestamp)
            }
        )

    }
}