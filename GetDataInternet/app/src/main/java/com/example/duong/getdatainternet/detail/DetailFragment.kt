package com.example.duong.getdatainternet.detail


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.duong.getdatainternet.R
import com.example.duong.getdatainternet.databinding.FragmentDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater);
        binding.setLifecycleOwner(this)
        // lấy luôn đối tượng trong arguments
        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val detailViewModelFactory = DetailViewModelFactory(marsProperty,application);
        val detalViewModel = ViewModelProviders.of(this,detailViewModelFactory)
            .get(DetailViewModel::class.java);
        binding.detailViewModel = detalViewModel
        return binding.root;
    }
}
