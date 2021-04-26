package ru.dmisb.hr_challenge.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dmisb.hr_challenge.data.model.Point
import ru.dmisb.hr_challenge.databinding.ItemMainTableBinding
import ru.dmisb.hr_challenge.utils.dpToPx

class MainTableAdapter : RecyclerView.Adapter<MainTableAdapter.MainTableViewHolder>() {

    private val items = mutableListOf<Point>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTableViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMainTableBinding.inflate(layoutInflater, parent, false)
        return MainTableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTableViewHolder, position: Int) {
        holder.bind(items[position], position == 0, position == items.size - 1)
    }

    override fun getItemCount() = items.size

    fun setItems(points: List<Point>) {
        items.clear()
        items.addAll(points)
        notifyDataSetChanged()
    }

    class MainTableViewHolder(
            private val binding: ItemMainTableBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Point, isFirst: Boolean, isLast: Boolean) {
            binding.mainTableX.text = item.x.toString()
            binding.mainTableY.text = item.y.toString()

            val lp = binding.root.layoutParams as? RecyclerView.LayoutParams
            lp?.leftMargin = binding.root.context.dpToPx(if (isFirst) 24 else -1)
            lp?.rightMargin = if (isLast) binding.root.context.dpToPx(24) else 0
            if (lp != null) {
                binding.root.layoutParams = lp
            }
        }
    }
}