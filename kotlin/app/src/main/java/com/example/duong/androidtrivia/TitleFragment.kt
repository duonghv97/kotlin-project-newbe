package com.example.duong.androidtrivia


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.duong.androidtrivia.databinding.FragmentTitleBinding

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
        val binding = DataBindingUtil
            .inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false);
        // nếu có function thì tuyền it còn không có mà là trong hàm listener thì sử dung view:View->
        binding.playButton.setOnClickListener{
            // view ở đây thể hiện là button
            view:View ->
                view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
            // tìm đến cái Nav điều khiển và trỏ đến action và chuyển đổi
        }
        // khi bổ sung thêm view vào layout thì checkxem ở binding đã cập nhất ID của view mới
        // cho vào chưa, nếu chưa thì ta cần bổ sung thì mới có thể gọi được
        binding.rulesButton.setOnClickListener{
            view:View->
                view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToRuleFragment())
        }
        binding.aboutButton.setOnClickListener {
            view:View ->
                view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAboutFragment())
        }
        // mỗi class sẽ có một navdirection, ta sử dụng nó để truyền param thông qua các
        // method hỗ trợ là action....



        // khi oncreateView là sẽ set có option menu, kiểu đăng kí
        setHasOptionsMenu(true)
        return binding.root
    }
    // create option menu, tức là chỉ có ở titleFragment mới có option menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // tức là 2 đối tượng menu và inflater đều có thể null
        super.onCreateOptionsMenu(menu, inflater)
        // chuyển view file .xml sang code để tạo option menu thôi
        inflater?.inflate(R.menu.options_menu,menu); // nếu inflater null thì sẽ bỏ qua lệnh này
    }
    // xử lý khi chọn item
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // item có thể null, nếu null thì thông báo lỗi, view ở đây là khi click vào item
        return NavigationUI.onNavDestinationSelected(item!!,view!!.findNavController())
            || super.onOptionsItemSelected(item);
    }


}
