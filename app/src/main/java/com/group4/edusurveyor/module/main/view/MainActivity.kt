package com.group4.edusurveyor.module.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.group4.edusurveyor.databinding.MainActivityBinding
import com.group4.edusurveyor.module.detail.view.DetailActivity
import com.group4.edusurveyor.module.main.adapter.MainAdapter
import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.module.main.viewmodel.MainViewModel
import com.group4.edusurveyor.repository.local.helper.SurveyDBHelper
import com.group4.edusurveyor.repository.local.helper.SurveyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var mainAdapter: MainAdapter
    var viewModel = MainViewModel(SurveyDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mainAdapter = MainAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvHome.layoutManager = layoutManager
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = mainAdapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        fetchData()
    }

    fun fetchData() {
        viewModel.getData()
        lifecycleScope.launch(Dispatchers.Main) {
            val pagingData = PagingData.from(viewModel.listData)
            mainAdapter.submitData(pagingData)
            mainAdapter.notifyDataSetChanged()
        }

        val noData = viewModel.listData.isEmpty()
        binding.rvHome.visibility = if (noData) View.GONE else View.VISIBLE
        binding.tvTextEmpty.visibility = if (noData) View.VISIBLE else View.GONE
    }
}