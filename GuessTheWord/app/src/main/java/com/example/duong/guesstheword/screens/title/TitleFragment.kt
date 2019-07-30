package com.example.duong.guesstheword.screens.title


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.duong.guesstheword.R
import com.example.duong.guesstheword.databinding.FragmentTitleBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // thằng binding nó sẽ được gán để hiện thị layout_fragment, nên nó sẽ gọi inflate
        // như vậy nó có thể tham chiếu đến các view bên trong lay_out
        val binding = DataBindingUtil
            .inflate<FragmentTitleBinding>(inflater,R.layout.fragment_title,container,false)
        // khi click vao game button
        binding.playGameButton.setOnClickListener{
            //  bên trong là 1 function sử dụng lambda expression, không cần ngoặc nhọn
            view:View ->
                view.findNavController()
                    .navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }
        return binding.root;
    }


}
