package com.example.duong.trackmysleepquality.screentracker


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.duong.trackmysleepquality.R
import com.example.duong.trackmysleepquality.database.SleepDatabase
import com.example.duong.trackmysleepquality.databinding.FragmentSleepTrackerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SleepTrackerFragment : Fragment() {
    private lateinit var binding: FragmentSleepTrackerBinding
//    var nightAdapter = SleepNightAdapter();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate<FragmentSleepTrackerBinding>(inflater,
            R.layout.fragment_sleep_tracker,container,false);
        // nếu value là null thì sẽ throw ra exception
        // context application
        val application = requireNotNull(this.activity).application;
/*         tham chiếu đến DAO của database. Trong database chứa thuộc tính là DAO để
         thao tác với CSDL*/
        val dataSource = SleepDatabase.getIntance(application).sleepDatabaseDAO();
        val trackerViewModelFactory = SleepTrackerViewModelFactory(dataSource,application);
        // lấy ra tham chiếu đến viewmodel ở viewmodelfactory được liên kết với fragment
        val trackerViewModel = ViewModelProviders.of(this,trackerViewModelFactory)
            .get(SleepTrackerViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.trackerViewModel = trackerViewModel;

        // call hàm handle click
        val nightAdapter = SleepNightAdapter(SleepNightListener {
            nightId->
                trackerViewModel.onSleepNightClicked(nightId)
        })
        // call đến adapter xong gán vào recyclerview
        // gán adapter vào recycleview và cũng để binding theo dõi
        binding.sleepList.adapter = nightAdapter

        // đưa dữ liệu vào data, khi có sự thay đổi về data thì cập nhật vào Adapter
        // xong hiển thị lại lên view,
        trackerViewModel.nights.observe(this, Observer {
            it?.let {
                // nó chỉ updated những item nào thay đổi ở list thôi
                // it không phải là 1 night mà nó là cả List
                nightAdapter.submitList(it);
            }
        })


        //  từ fragmenttracker lấy dữ liệu night từ viewmodel xong gửi sang fragmentquality.
        trackerViewModel.navigateToSleepNight.observe(this, Observer { night->
            night?.let {
                // value night chính là navigateToSleepNight
                /*Sử dụng this thì không cần đối số, còn sử dụng NavFragment thì truyền đố số fragment vào navcontroller*/
                this.findNavController().navigate(SleepTrackerFragmentDirections
                    .actionSleepTrackerFragmentToSleepQuanlityFragment(night.nightId))
                trackerViewModel.doneNavigating();
                // sau khi truyền xong dữ liệu set lại night trở về ban đầu.
            }
        })

        trackerViewModel.showSnackbarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                trackerViewModel.doneShowSnackBarEvent();
            }
        })


        // quan sát biến điều hướng khi nó thay đổi thì điều hướng và đồng thời
        // truyền id sang bên nhận, và bên nhận sẽ truy vấn từ db
        trackerViewModel.navigateSleepDetail.observe(this, Observer {nightId->
            nightId?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.
                    actionSleepTrackerFragmentToSleepDetailFragment(nightId))
                trackerViewModel.onSleepDetailNavigated();
            }
        })
        //,GridLayoutManager.HORIZONTAL,false
        // tạo gridview có 3 count
        val layoutManager = GridLayoutManager(activity,3);
        // nói với RecycleView sử dụng layoutManager
        binding.sleepList.layoutManager = layoutManager as RecyclerView.LayoutManager?;
        return binding.root;
    }
}
