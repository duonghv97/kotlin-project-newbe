package com.example.duong.getdatainternet.overview


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.example.duong.getdatainternet.R
import com.example.duong.getdatainternet.databinding.FragmentOverviewBinding
import com.example.duong.getdatainternet.databinding.GridViewItemBinding
import com.example.duong.getdatainternet.network.MarsApiFilter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OverviewFragment : Fragment() {
    /**
     * Lazily initialize our [OverviewViewModel].
     */
    //create viewmodel k suwr dungj factory
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application;
       val binding = FragmentOverviewBinding.inflate(inflater);

      //  val binding = GridViewItemBinding.inflate(inflater)
/*         sử dụng Binding chấm luôn mà không qua BindingUtil
        thì nó sẽ hiểu luôn layout nào sẽ tương ứng với Binding đó */
        binding.setLifecycleOwner(this);
        binding.overviewViewModel = viewModel;
        // Add adater ở ReycleView bằng Adapter Object. Khi đó adapter ở RecycleView
        // có thể cập nhật những gì thay đổi và hiển thị lên.
        binding.photosGrid.adapter = PhotoGridAdapter(OnClickListener { marsProperty ->
            viewModel.displayPropertyDetails(marsProperty)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (it != null) {
                NavHostFragment.findNavController(this)
                    .navigate(OverviewFragmentDirections
                        .actionOverviewFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete();
            }
        })
        setHasOptionsMenu(true)
        return binding.root;
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.overflow_menu,menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
    // khi chọn vào item
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // khi chọn item sẽ call đến updatefilter và truyền filter data tương ứng với từng item
        viewModel.updateFilter(
            when (item?.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }

}
