package com.example.duong.guesstheword.screens.game


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.duong.guesstheword.R
import com.example.duong.guesstheword.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GameFragment : Fragment() {

    // động đến list để hiện thị ra từng item thì cần phải có itemcurrent

    // do cần dùng binding ở ngoài onCreateView thì tạo biến toàn cục
    private lateinit var binding: FragmentGameBinding;

    // liên kết từ UI Controller reference đến ViewModel
    private lateinit var gameViewModel: GameViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // hiển thị fragment_game
        binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game,container,false);
        Log.i("GameFragment", "Called ViewModelProviders.of")
        // create object ViewModel sử dụng ViewModelProvider
        // Method ViewModelProvider.of sẽ tạo ra ViewModelProvider xong get với các param sẽ tạo ra object
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java);

        binding.gameViewModel = gameViewModel;
        // binding có thể quan sát LiveData thay đổi
        // do không thấy lifecycleOwner nên ta sử dụng khi start ta set là fragment này sau khi gọi super
        // khi stop ta set lại là null trước khi gọi super
//        binding.lifecycleOwner = this;
        // observer
        // đính kèm observer object vào trong LiveData object để Fragment quan sát liveData
        // update lên UI một cách tự động
        /** Setting up LiveData observation relationship **/
/*        LiveData có call method observer trong đó có param là
         this thể hiện là Fragment này sẽ thực hiện quan sát và khi có thay đổi data
         thì cập nhật bằng cách implements Inteface Observer*/


/*        gameViewModel.score.observe(this, Observer<Int>{
            // override method onChange trong Interface Observer
            newScore ->
                binding.scoreText.text = newScore.toString()
        });*/

/*      Do sử dụng lifecycleOwner nên không cần observer
        gameViewModel.word.observe(this, Observer <String> {
            newWord ->
                binding.wordText.text = newWord;
        });*/


        // het từ tự động nhảy sang Fragment score
        gameViewModel.endGameFinish.observe(this, Observer<Boolean> {
               hasFinish ->
                    if(hasFinish == true) {
                        onFinishGame();
                    }
        })

   /*
        // do dùng data binding nên k cần nữa
        // chuyển sang câu khác và tăng score lên 1
        binding.correctButton.setOnClickListener {
            onCorrect();
        }

        // chuyển sang câu khác và giảm score
        binding.skipButton.setOnClickListener {
            onSkip()
        }

        // khi endgame thì chuyern sang fragment_score
        binding.endGameButton.setOnClickListener {
            onFinishGame()
        }*/

//        updateWordText();
////        updateScoreText();
        return binding.root;
    }

/*  // do dùng data binding nên k cần nữa
    private fun onCorrect() {
        // call method process data in GameViewModel
        gameViewModel.onCorrect();
        // do có LiveData rồi nên k cần nữa
//        updateScoreText();
//        updateWordText();
    }

    private fun onSkip() {
        gameViewModel.onSkip();
//        updateWordText();
//        updateScoreText();
    }*/

    /*methods update data for UI*/
    // cập nhật word mới lên textView
//    private fun updateWordText() {
//        binding.word = gameViewModel.word.value;
//    }
//    // cập nhật điểm số lên textView
//    private fun updateScoreText() {
//        binding.score = gameViewModel.score.value;
//    }


    private fun onFinishGame() {
        // Mỗi lần xoay màn hình sẽ Toast lại --> Bug
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        // sử dụng NavHostFragment như view:View
        // nó sẽ tìm đến fragment dó xong thực hiện điều hướng theo sự kiện
        NavHostFragment.findNavController(this).navigate(GameFragmentDirections
            .actionGameFragmentToScoreFragment(gameViewModel.score.value?:0)) // check null sẽ nhận 0
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
