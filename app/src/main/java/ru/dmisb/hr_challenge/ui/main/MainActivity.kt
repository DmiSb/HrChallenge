package ru.dmisb.hr_challenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.dmisb.hr_challenge.databinding.ActivityMainBinding
import ru.dmisb.hr_challenge.ui.custom.ChartView
import ru.dmisb.hr_challenge.ui.dialog.ProgressDialog
import ru.dmisb.hr_challenge.utils.SimpleTextWatcher
import ru.dmisb.hr_challenge.utils.hideKeyboard

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var countListener: SimpleTextWatcher
    private lateinit var tableAdapter: MainTableAdapter

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countListener = SimpleTextWatcher { viewModel.setCount(it) }

        binding.mainActionButton.setOnClickListener {
            hideKeyboard()
            viewModel.getPoints()
        }

        tableAdapter = MainTableAdapter()

        binding.mainTableView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = tableAdapter
        }

        viewModel.valid.observe(this) { valid ->
            binding.mainActionButton.isEnabled = valid == true
        }

        viewModel.showProgress.observe(this) { showProgress ->
            if (showProgress == true) getProgressDialog()?.show()
            else getProgressDialog()?.dismiss()
        }

        viewModel.points.observe(this) { points ->
            tableAdapter.setItems(points.orEmpty())

            binding.mainChartLayout.removeAllViews()
            ChartView(this).apply {
                bind(points.orEmpty())
                binding.mainChartLayout.addView(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.mainCountView.addTextChangedListener(countListener)
    }

    override fun onPause() {
        super.onPause()

        binding.mainCountView.removeTextChangedListener(countListener)
    }

    private fun getProgressDialog() : ProgressDialog? {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
        return progressDialog
    }
}