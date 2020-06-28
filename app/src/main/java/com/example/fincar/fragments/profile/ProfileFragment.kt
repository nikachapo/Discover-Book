package com.example.fincar.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fincar.Tools.showToast
import com.example.fincar.activities.EXTRA_USER
import com.example.fincar.activities.RegistrationActivity
import com.example.fincar.databinding.FragmentProfileBinding
import com.example.fincar.network.firebase.FetchUserDataCallbacks
import com.example.fincar.network.firebase.FirebaseDbHelper

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(container?.context)
        FirebaseDbHelper.getUserWithUid(FirebaseDbHelper.getCurrentUser().uid,
            object : FetchUserDataCallbacks {
                override fun onReceive(userModel: UserModel?) {
                    binding.userModel = userModel
                    binding.editButton.setOnClickListener {
                        context?.startActivity(Intent(context, RegistrationActivity::class.java)
                            .putExtra(EXTRA_USER,userModel))
                    }
                }

                override fun onError(message: String) {
                    showToast(context!!, message)
                }
            })
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}
