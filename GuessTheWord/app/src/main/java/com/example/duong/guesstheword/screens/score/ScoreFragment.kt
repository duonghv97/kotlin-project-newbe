package com.example.duong.guesstheword.screens.score


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.duong.guesstheword.R
import com.example.duong.guesstheword.databinding.FragmentScoreBinding
import kotlinx.android.synthetic.main.fragment_game.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    // variable để tham chiếu từ Fragment sang ViewModel và ViewModelFactory
    private lateinit var scoreViewModel:ScoreViewModel;
    private lateinit var scoreViewModelFactory : ScoreViewModelFactory;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentScoreBinding>(inflater,R.layout.fragment_score,
            container,false);

        // Việc truyền và nhận dữ liệu thì vẫn thông qua các fragment. Xong rồi đem dữ liệu
        // đó xử lý gì thì xử lý
        // khởi tạo scoreViewModelFactory
        scoreViewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs
            .fromBundle(arguments!!).score)
        // khởi tạo scoreViewModel object, truyền viewmodelfactory vào để nó tạo ra đối tượng
        // có đối số truyền vào.
        scoreViewModel = ViewModelProviders.of(this,scoreViewModelFactory)
            .get(ScoreViewModel::class.java); // tạo đối tượng xong lấy thuộc tính trong đó ra
        binding.scoreViewModel = scoreViewModel;
//        binding.lifecycleOwner = this;

/*        scoreViewModel.score.observe(this, Observer <Int>{
            // để đưa lên UI
            newScore ->
                binding.scoreText.text = newScore.toString();
        })*/


        /*
        // thay thế hết các sự kiện click để nó ở layout và sử dụng data binding
        // khi chơi lại sẽ chuyern về fragment_game
        binding.playAgainButton.setOnClickListener{
            scoreViewModel.onPlayAgain(); // khi click vào nút chơi lại thì set trạng thái là true
            // thì thằng biến playAgain thấy có thay đổi cập nhật lên UI chuyển đổi và set về false
        }*/
        // khi thay đổi trạng thái của biến thì tự cập nhật lên UI

        scoreViewModel.playAgain.observe(this, Observer <Boolean>{
            playAgain ->
                if(playAgain == true) {
                    NavHostFragment.findNavController(this)
                        .navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
                    // reset về false
                    scoreViewModel.onPlayAgainResetStatus()
                }

        })

        return binding.root;

    }
    override fun onStart() {
        super.onStart()
        binding.setLifecycleOwner(this)
    }

    override fun onStop() {
        binding.setLifecycleOwner(null);
        super.onStop()
    }
}
