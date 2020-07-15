package com.example.fincar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.models.Category
import com.example.fincar.databinding.ItemCategoryBinding


class CategoriesAdapter(
    private val categories: MutableList<Category>, private val clickListener: OnCategoryClickListener
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var lastChosenPosition = 0

    interface OnCategoryClickListener {
        fun onClick(category: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding
            .inflate(layoutInflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CategoriesViewHolder(
        private val itemCategoryBinding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(itemCategoryBinding.root) {

        fun onBind() {
            val category = categories[adapterPosition]
            itemCategoryBinding.category = category

            itemCategoryBinding.categoryItemCard.setCardBackgroundColor(category.color)

            itemCategoryBinding.root.setOnClickListener {

                itemCategoryBinding.categoryItemCard.setCardBackgroundColor(Color.WHITE)

                if (lastChosenPosition != adapterPosition) {
                    notifyItemChanged(lastChosenPosition)
                }

                lastChosenPosition = adapterPosition

                clickListener.onClick(category.name)
            }

        }
    }
}
