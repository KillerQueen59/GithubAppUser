package com.example.consumerapp.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.consumerapp.R
import com.example.consumerapp.fragment.FragmentFollowers
import com.example.consumerapp.fragment.FragmentFollowing

class PagerAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    private val pages = listOf(
        FragmentFollowers(),
        FragmentFollowing(),
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 ->  "Followers"
            else -> "Following"
        }
    }
}