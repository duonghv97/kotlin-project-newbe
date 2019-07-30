package com.example.duong.bindingdata

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.duong.bindingdata.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // tạo biến myName gán bằng đối tượng khởi tạo từ class data truyền vào 1 tham số
    private val myName:MyName = MyName("Ha Van Duong")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // set các giá trị mặc định cho data class. Chính là param name ở <variable>
        // param name đó là tên của data class
        binding.myName = myName
        // handle event click
        val doneButton = binding.doneButton;
        doneButton.setOnClickListener {
            addNickName(it)
            // đối tượng item(it) tham chiếu đến button, chính là đối tượng mà ta đang xét
        };
        // khi click vào textview thì hiển thị edittext và button để sửa
        binding.nicknameText.setOnClickListener {
            updateNickName(it)
        }
    }

    private fun updateNickName(view: View) {
        val editText = binding.nicknameEdit;
        val buttonDone = binding.doneButton;
        // view truyền vào cũng phải đổi thành binding
        binding.nicknameText.visibility = View.GONE; // hide textview
        editText.visibility = View.VISIBLE; // display edit text
        buttonDone.visibility = View.VISIBLE // display button

        // tức là khi hiện lên thì nháy chuột ở trong đó rồi
        editText.requestFocus();
        // show keyboard, sử dụng inputMethodManager cho showSoftInput
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.showSoftInput(editText, 0);
    }

    // define function has name adđNickName in kotlin
    private fun addNickName(view: View) {
        // luồng. get text từ edittext, set text vào text view
        // hide edit text và button
        // display textview
        // getText ở edittable ra xong settext vào textview
        binding.apply {
            // gán value cho nickname.
            // tức là binding.myName trỏ đến các thuộc tính con trong sử ?.
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll() // để refresh UI
            nicknameEdit.visibility = View.GONE; // hide edittext
            doneButton.visibility = View.GONE; // view tức là button, hide button
            nicknameText.visibility = View.VISIBLE;
        }


        // hide keyboard, sử dụng inputMethodManager cho nó hideSoftInput
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
    }
}
