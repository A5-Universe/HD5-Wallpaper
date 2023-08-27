package com.a5universe.hd5wallpaper.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a5universe.hd5wallpaper.databinding.FragmentOfflineBinding


class OfflineFragment : Fragment() {

    private lateinit var binding: FragmentOfflineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentOfflineBinding.inflate(layoutInflater,container,false)
        //when your network offline show offline fragment

        return binding.root
    }


}