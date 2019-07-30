package com.example.duong.trackmysleepquality.screendetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.duong.trackmysleepquality.R
import com.example.duong.trackmysleepquality.database.SleepDatabase
import com.example.duong.trackmysleepquality.database.SleepDatabaseDAO
import com.example.duong.trackmysleepquality.databinding.FragmentSleepDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SleepDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<FragmentSleepDetailBinding>(inflater,
            R.layout.fragment_sleep_detail,container,false)
        val application = requireNotNull(this.activity).application;
        val dataSource = SleepDatabase.getIntance(application).sleepDatabaseDAO();
        val args = SleepDetailFragmentArgs.fromBundle(arguments);

        val sleepDetailViewModelFactory = SleepDetailViewModelFactory(args.sleepNightKey,dataSource);
        val sleepDetailViewModel = ViewModelProviders.of(this,sleepDetailViewModelFactory)
            .get(SleepDetailViewModel::class.java);
        binding.sleepDetailViewModel = sleepDetailViewModel;
        // sử dụng để quan sát các LiveData
        binding.setLifecycleOwner(this)
        sleepDetailViewModel.navigateSleepTracker.observe(this, Observer {
           // khi click vào button close thì cho biến thành true
            if(it == true) {
                this.findNavController().navigate(SleepDetailFragmentDirections.
                    actionSleepDetailFragmentToSleepTrackerFragment());
                sleepDetailViewModel.donNavigating(); // thực hiện xong reset về null.
            }
        })


        return binding.root;
    }


}
