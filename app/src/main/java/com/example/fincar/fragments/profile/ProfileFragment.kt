package com.example.fincar.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.layout_manager.LayoutManagerFactory
import com.example.fincar.R
import com.example.fincar.activities.registration.EXTRA_USER
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.app.Tools
import com.example.fincar.app.Tools.showToast
import com.example.fincar.bean.Account
import com.example.fincar.bean.book.GoogleBook
import com.example.fincar.databinding.FragmentProfileBinding
import com.example.fincar.fragments.booksList.BooksListFragment
import com.example.fincar.layout_manager.ILayoutManagerFactory
import com.example.fincar.network.firebase.account.AccountDataObserver
import com.example.fincar.network.firebase.account.FetchAccountDataCallbacks

class ProfileFragment : Fragment() {

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView

    private lateinit var binding: FragmentProfileBinding
    private lateinit var accountDataObserver: AccountDataObserver

    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val layoutInflater = LayoutInflater.from(container?.context)
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingRootLayout = requireActivity().findViewById(R.id.loadingRootLayout)
        loadingAnimationView = requireActivity().findViewById(R.id.loadingAnimationView)
        Tools.startLoadingAnimation(loadingRootLayout, loadingAnimationView)
        accountDataObserver = AccountDataObserver(fetchUserDataEventListener)
        lifecycle.addObserver(accountDataObserver)

        initViewModel()

    }

    private fun initViewModel() {
        profileFragmentViewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        profileFragmentViewModel.getStarredBooks()?.observe(viewLifecycleOwner, Observer {
            childFragmentManager.beginTransaction()
                .replace(
                    R.id.starredBooksListContainer,
                    BooksListFragment(
                        googleBooksList = it as ArrayList<GoogleBook>,
                        layoutManagerFactory = object : ILayoutManagerFactory {
                            override fun create(): RecyclerView.LayoutManager {
                                return LayoutManagerFactory
                                    .create(context, LinearLayoutManager.HORIZONTAL,false)
                            }
                        }
                        )
                    )
                .commit()
        })
    }

    private val fetchUserDataEventListener = object :
        FetchAccountDataCallbacks {
        override fun onReceive(account: Account?) {
            Tools.cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)
            binding.userModel = account
            binding.editButton.setOnClickListener {
                context?.startActivity(
                    Intent(context, RegistrationActivity::class.java)
                        .putExtra(EXTRA_USER, account)
                )
            }

        }

        override fun onError(message: String) {
            Tools.cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)
            showToast(context!!, message)
        }
    }

}
