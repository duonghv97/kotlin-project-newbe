package com.example.duong.androidtrivia


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.duong.androidtrivia.databinding.FragmentGameBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GameFragment : Fragment() {
    // answer thì chỉ xem thôi nên ta sử dụng List để nhẹ hơn
    data class Question (val text: String, val answers: List<String> )

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android Jetpack?", answers = listOf("All the these","Tools","Document","Library")),
        Question(text = "1 + 3 = ?",answers = listOf("4","2","3","1")),
        Question(text = "4 + 5 = ?", answers = listOf("9","5","7","4")),
        Question(text = "10 + 3 = ?",answers = listOf("13","18","12","11"))
    );
    lateinit var currentQuestion: Question;
    lateinit var answers: MutableList<String>
    private var questionIntex = 0;
    private var numberQuestions = Math.min((questions.size + 1)/2,3 )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // tạo đối tượng binding để thay cho string resource ở layout nên sẽ return binding
        // còn không thì sẽ return inflater để biên dịch fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game,container,false);
        // xáo trộn lại câu hỏi
        randomizeQuestion();
        binding.game = this;// object game ở data sẽ được gán bằng fragment
        // hiển thị câu hỏi và đáp án trong anwers đã được sáo trộn
        // tùy vào object data gán bằng gì thì ta sẽ trỏ đến các mục con cho phù hợp
        binding.submitButton.setOnClickListener{
//            submitAnswer(it); // chinrh la button hoac k tach ham ra thi cos the su dung
             view:View ->
                // lấy ra id mà người dùng chọn
                val checkedId = binding.questionRadioGroup.checkedRadioButtonId;
                // nếu không chọn thì id sẽ bằng -1
                if(checkedId != -1) {
                    // answerIndex là vị trí câu trả lời mà người dùng chọn
                    var answerIndex = 0;
                    when(checkedId) {
                        R.id.secondAnswerRadioButton -> answerIndex = 1;
                        R.id.thirdAnswerRadioButton -> answerIndex = 2;
                        R.id.fourthAnswerRadioButton -> answerIndex = 3
                    }
                    // so sánh với đáp án đúng
                    if(answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIntex++;
                        // chuyen sang cau hoi ke tiep
                        if(questionIntex < numberQuestions) {
                            // câu hỏi hiện tại sẽ được cập nhật
                            currentQuestion = questions.get(questionIntex);
                            setQuestion();
                            binding.invalidateAll();// cập nhật GUI
                            // xong người dùng lại chọn câu khác và nhấn submit thì code sẽ lại được chạy
                        } else {
                            // nếu đúng hết thì sẽ chiến thằng
                            // chuyenr snag gamewonFragment
                            // sử dụng directions để điều hướng sang GameWonFragment
                            view.findNavController()
                                .navigate(GameFragmentDirections
                                    .actionGameFragmentToGameWonFragment(numberQuestions,questionIntex)); // send data
                            // questionIntex chính là số lượng câu trả lời đúng vì neus sai đã thoat luon
                            // truyền đối số ở gameFragment thì chỉ cần cho vào method action thôi

                        }
                    } else {
                        // game over// chuyeern den gameover fragment
                        view.findNavController()
                            .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                    }
                }
        }

        return  binding.root;
    }
    private fun submitAnswer(view:View) {
        // câu trả lời đầu tiên trong bộ câu trả lời ban đầu khi chưa xáo trộn sẽ là câu trả lời đúng
        // cách hay không làm thêm 1 biến câu trả lời đúng nữa, chỉ cần cho câu trả lời ban đầu là đúng
        // xong xáo trộn lên và so sánh với cái ban đầu là được

    }

    // random question and set câu hỏi đầu tiên
    private fun randomizeQuestion() {
        questions.shuffle(); // hàm xáo trộn câu hỏi
        questionIntex = 0; // xao tron xong lay cau dau tien ra
        setQuestion();
    }

    // set question and random answer
    private fun setQuestion() {
        currentQuestion = questions.get(questionIntex);
        // get answer and random answer, chuyern từ List sang MutableList để xáo trộn được
        answers = currentQuestion.answers.toMutableList() // cho ra list answers xáo trộn rồi hiển thị thôi
        answers.shuffle();
       (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIntex + 1, numberQuestions)
    }


}
