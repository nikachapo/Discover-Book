package com.example.fincar.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fincar.R
import com.example.fincar.fragments.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel

    override fun getResourceId() = R.layout.home_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

}
