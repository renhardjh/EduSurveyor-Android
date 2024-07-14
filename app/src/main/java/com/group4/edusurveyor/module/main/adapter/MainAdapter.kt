package com.group4.edusurveyor.module.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.group4.edusurveyor.R
import com.group4.edusurveyor.databinding.SurveyItemBinding
import com.group4.edusurveyor.module.detail.view.DetailActivity
import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.module.main.view.MainActivity

class MainAdapter : PagingDataAdapter<SurveyRecordModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private val VIEW_ITEM = 0

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SurveyRecordModel>() {
            override fun areItemsTheSame(oldItem: SurveyRecordModel, newItem: SurveyRecordModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SurveyRecordModel, newItem: SurveyRecordModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemHomeBinding = SurveyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ItemViewHolder(itemHomeBinding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as ItemViewHolder
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_ITEM
    }

    override fun getItemCount(): Int {
        return snapshot().items.size
    }

    class ItemViewHolder(private val binding: SurveyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SurveyRecordModel) {
            with(binding) {
                val context = cardView.context
                tvTitle.text = item.title
                tvCreatedAt.text = context.getString(R.string.created_at, item.createAt)
                tvUpdatedAt.text = context.getString(R.string.updated_at, item.updateAt)
                cardView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("survey_record", item)
                    cardView.context.startActivity(intent)
                }

                ivButtonDelete.setOnClickListener {
                    val activity = (context as MainActivity)
                    val viewModel = activity.viewModel
                    viewModel.deleteData(item.id.toString())
                    activity.fetchData()
                    Toast.makeText(context,
                        context.getString(R.string.data_record_has_been_deleted), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}