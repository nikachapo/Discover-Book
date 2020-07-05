package com.example.fincar.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.app.Tools.showToast
import com.example.fincar.activities.registration.EXTRA_USER
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.bean.UserModel
import com.example.fincar.databinding.FragmentProfileBinding
import com.example.fincar.network.firebase.FetchUserDataCallbacks
import com.example.fincar.network.firebase.FirebaseDbHelper

class ProfileFragment : Fragment() {

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView

    private lateinit var binding: FragmentProfileBinding
    private val firebaseDbHelper = FirebaseDbHelper.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutInflater = LayoutInflater.from(container?.context)

        lifecycle.addObserver(firebaseDbHelper)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingRootLayout = activity!!.findViewById(R.id.loadingRootLayout)
        loadingAnimationView = activity!!.findViewById(R.id.loadingAnimationView)
        fetchUserData()
    }

    private fun fetchUserData() {
        loadingRootLayout.visibility = View.VISIBLE
        loadingAnimationView.playAnimation()

        firebaseDbHelper.getCurrentUser(
            object : FetchUserDataCallbacks {
                override fun onReceive(userModel: UserModel?) {

                    loadingRootLayout.visibility = View.GONE
                    loadingAnimationView.cancelAnimation()

                    binding.userModel = userModel

                    binding.editButton.setOnClickListener {
                        context?.startActivity(
                            Intent(context, RegistrationActivity::class.java)
                                .putExtra(EXTRA_USER, userModel)
                        )
                    }

                }

                override fun onError(message: String) {

                    loadingRootLayout.visibility = View.GONE
                    loadingAnimationView.cancelAnimation()

                    showToast(context!!, message)
                }
            })
    }

}
