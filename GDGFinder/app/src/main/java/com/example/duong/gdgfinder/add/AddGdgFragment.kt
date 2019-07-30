package com.example.duong.gdgfinder.add


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.duong.gdgfinder.R
import com.example.duong.gdgfinder.databinding.AddDgdFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddGdgFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<AddDgdFragmentBinding>(inflater,
            R.layout.add_dgd_fragment,
            container,false)
        // cho phép data binding observer LiveData với vòng đời cả Fragment
        binding.setLifecycleOwner(this)

        var addGdgViewModel = ViewModelProviders.
            of(this).get(AddGdgViewModel::class.java);
        binding.addGdgViewModel = addGdgViewModel;

        // show snackbar
        addGdgViewModel.showSnackbarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.application_submitted),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
            }
            addGdgViewModel.doneShowSnackBarEvent();
        })

        setHasOptionsMenu(true)
        return binding.root;
    }


}
