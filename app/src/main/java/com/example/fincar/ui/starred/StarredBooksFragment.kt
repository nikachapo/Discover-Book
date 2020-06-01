package com.example.fincar.ui.starred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fincar.R

class StarredBooksFragment : Fragment() {

    private lateinit var starredBooksViewModel: StarredBooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        starredBooksViewModel =
            ViewModelProviders.of(this).get(StarredBooksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_starred_books, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        starredBooksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
