package com.example.consumerapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.SessionManagement
import com.example.consumerapp.adapter.FollowAdapter
import com.example.consumerapp.connection.ApiService
import com.example.consumerapp.databinding.FragmentFollowingBinding
import com.example.consumerapp.model.User
import com.example.consumerapp.viewmodel.UserViewModel

class FragmentFollowing: Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val model: UserViewModel by viewModels()
    lateinit var session: SessionManagement
    private var list: ArrayList<User> = arrayListOf()
    private lateinit var adapter: FollowAdapter
    private var found = false
    private val apiService by lazy {
        ApiService.create(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManagement(requireContext())
        followingUser()
        showData()
    }

    private fun showData(){
        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        adapter = FollowAdapter(list)
        binding.rvFollowing.adapter = adapter
        if (found) binding.rvFollowing.visibility = View.VISIBLE else binding.rvFollowing.visibility = View.GONE
    }

    private fun followingUser(){
        showProgress(true)
        model.followingUser(apiService, session.user)
        model.resultFollow.observe({lifecycle},{
            if (it.size > 0){
                list.addAll(it)
                adapter.notifyDataSetChanged()
                found = true
                showData()
                showProgress(false)
            }
        })
    }
    private fun showProgress(show: Boolean) {
        binding.loadLayout.bringToFront()
        binding.loadLayout.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvFollowing.visibility = if(show || !found ) View.GONE else View.VISIBLE

    }
}