package com.example.fincar.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fincar.models.Post
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class HomeViewModel : ViewModel() {

    private val postsRef = FirebaseDatabase.getInstance()
        .getReference(FirebaseDbHelper.POSTS_KEY)

    private val _post = MutableLiveData<Post>()

    fun getPost(): LiveData<Post> = _post

    private val getPostListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            _post.value = p0.getValue(Post::class.java)
        }

        override fun onChildRemoved(p0: DataSnapshot) {
        }

    }

    init {
        postsRef.addChildEventListener(getPostListener)
    }

}
