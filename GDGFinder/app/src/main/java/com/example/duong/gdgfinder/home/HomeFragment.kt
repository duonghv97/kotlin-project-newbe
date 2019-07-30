package com.example.duong.gdgfinder.home


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.duong.gdgfinder.R
import com.example.duong.gdgfinder.databinding.HomeFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    // định nghĩa một hàm static
    companion object {
        // định nghĩa một function sẽ return về object HomeFragment
        fun newIntance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<HomeFragmentBinding>(inflater,
            R.layout.home_fragment,container,false)
        var homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java);
        binding.homeViewModel = homeViewModel;
        return binding.root;
    }


}
