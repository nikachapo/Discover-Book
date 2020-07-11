package com.example.fincar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fincar.R
import com.example.fincar.app.SharedPreferenceUtil
import kotlinx.android.synthetic.main.item_suggestion_layout.view.*


class SuggestionsAdapter(private val suggestions: MutableList<String>,
                         private val suggestionClickListener: SuggestionClickListener) :
    RecyclerView.Adapter<SuggestionsAdapter.SuggestionsViewHolder>() {

    private var lastChosenPosition = 0

    interface SuggestionClickListener{
        fun onSuggestionClicked(suggestion: String)
        fun onSuggestionDeleted(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggestion_layout, parent, false)
        return SuggestionsViewHolder(view)
    }

    override fun getItemCount(): Int = suggestions.size

    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.onBind()
    }

    inner class SuggestionsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBind() {
            val suggestion = suggestions[adapterPosition]
            itemView.suggestionTextView.text = suggestion
            itemView.suggestionCard.setCardBackgroundColor(Color.GRAY)
            itemView.setOnClickListener {
                itemView.suggestionCard.setCardBackgroundColor(Color.WHITE)
                if(lastChosenPosition != adapterPosition){
                    notifyItemChanged(lastChosenPosition)
                }
                lastChosenPosition = adapterPosition
                suggestionClickListener.onSuggestionClicked(suggestion)
            }
            itemView.deleteSuggestionButton.setOnClickListener {
                suggestionClickListener.onSuggestionDeleted(adapterPosition)
            }
        }
    }
}