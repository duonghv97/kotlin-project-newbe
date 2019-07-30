package com.example.duong.androidtrivia



import android.content.Intent
import android.os.Bundle
import android.view.*

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.duong.androidtrivia.databinding.FragmentGameWonBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameWonBinding>(inflater,
            R.layout.fragment_game_won,container,false);
        binding.nextMatchButton.setOnClickListener{
            view:View->
                view.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameFragment());
        }
        // bên thì tạo biến args và sử dụng FragmentArgs.fromBundle để lấy ra
        // nhân dối số thông qua Bundle gán vào args, arguments nếu null thì sẽ báo lỗi
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"Number correct : ${args.numCorrect}, NumQuestions : ${args.numQuestions}",Toast.LENGTH_SHORT).show();
        // và các argument này là các argument đặt ở navigation
        setHasOptionsMenu(true)
        return binding.root;
    }
    private fun getShareIntent() : Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        // tùy vào action trong intent để biết là ta sẽ intent vào phần nào trong device
        // intent vào message
        val shareIntent:Intent = Intent(Intent.ACTION_SEND)
        // truyền dữ liệu
        // set kiểu dữ liệu
        // Intent.EXTRA_TEXT là dữ liệu gửi đi chỉ là string
        shareIntent.setType("text/plain")// args là dữ liệu truyền vào %$d
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        // chưa startActivity mà ta trả ra đối tượng
        return shareIntent
    }
    // intent vào device, tách riêng ra để khi nào thỏa mãn điều kiện thì đó
    // hoặc có sự kiện thì sẽ tiến hành start intent
    private fun shareSuccess() {
        // start intent ở trên
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu,menu);
        // intent vào device thì cần check xem đã
        // khi khởi tạo option menu thì check xem có null hay không
        // nếu null thì invisible option share
        // check bằng PackageManager để xem intent vào device có được giải quyết(xử lý) hay không
        if (getShareIntent().resolveActivity(activity!!.packageManager) == null) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // khi chọn vào item thì check nếu có id thì thực hiện intent
        // itemId tương tự như getId trong java android
        // sử dụng when thay cho switch case
        when(item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
