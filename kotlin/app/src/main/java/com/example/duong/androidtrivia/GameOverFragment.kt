package com.example.duong.androidtrivia


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.duong.androidtrivia.databinding.FragmentGameOverBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(inflater,
            R.layout.fragment_game_over,container,false);
        // binding hỗ trợ lấy id
        binding.tryAgainButton.setOnClickListener{
            view:View->
                view.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameFragment());
        }
        return binding.root;
    }
}
