package com.example.fincar.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.bean.Account
import com.example.fincar.network.firebase.account.AccountDataObserver
import com.example.fincar.network.firebase.account.FetchAccountDataCallbacks

class MainViewModel : ViewModel() {
    private val _accountLiveData = MutableLiveData<Account>()
    private val _accountFetchErrorMessage = MutableLiveData<String>()

    fun getAccountLiveData():LiveData<Account> = _accountLiveData
    fun getAccountFetchErrorMessage():LiveData<String> = _accountFetchErrorMessage

    val accountDataObserver by lazy {
        AccountDataObserver(object :
            FetchAccountDataCallbacks {
            override fun onReceive(account: Account) {
                _accountLiveData.value = account
            }

            override fun onError(message: String) {
                _accountFetchErrorMessage.value = message
            }
        })
    }

}

