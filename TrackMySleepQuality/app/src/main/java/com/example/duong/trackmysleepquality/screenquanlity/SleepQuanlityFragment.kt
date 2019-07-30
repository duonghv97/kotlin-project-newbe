package com.example.duong.trackmysleepquality.screenquanlity


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.duong.trackmysleepquality.R
import com.example.duong.trackmysleepquality.database.SleepDatabase
import com.example.duong.trackmysleepquality.databinding.FragmentSleepQuanlityBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SleepQuanlityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSleepQuanlityBinding>(inflater,
                R.layout.fragment_sleep_quanlity,container,false);
        val application = requireNotNull(this.activity).application;
        // nhận dữ liệu ở Fragment
        val arguments = SleepQuanlityFragmentArgs.fromBundle(arguments!!)
        // cần phải có context để truyền vào getInstance để lấy đối tượng database
        val dataSource = SleepDatabase.getIntance(application).sleepDatabaseDAO();
        val viewModelFactory = SleepQuanlityViewModelFractory(arguments.sleepNightKey,dataSource);
        val sleepQuanlityViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(SleepQuanlityViewModel::class.java);
        binding.sleepQualityViewModel = sleepQuanlityViewModel;
        sleepQuanlityViewModel.navigationToSleepTracker.observe(this, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepQuanlityFragmentDirections.actionSleepQuanlityFragmentToSleepTrackerFragment())
                sleepQuanlityViewModel.donNavigating();
            }
        })
        return binding.root;
        }
    }

