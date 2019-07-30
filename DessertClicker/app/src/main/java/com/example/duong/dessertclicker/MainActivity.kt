package com.example.duong.dessertclicker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.duong.dessertclicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var revenua = 0; // tiền kiếm được
    private var dessertSold = 0;  // số lượng món tráng miệng đã bán
    private lateinit var binding: ActivityMainBinding

    // sử dụng thêm id trong lớp để lấy ra chính xác ra hình ảnh
    // giá của món đó
    data class Dessert(val ImageId:Int,val price:Int, val startProductionAmount:Int)
    // create list of dessert
    // startProductionAmount tức là thể hiện khi bán được nhiều món hiện tại thì
    // sẽ bắt đầu chuyển sang sản xuất những món đắt tiền hơn.
    private val listDessert = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )

    private var currentDessert = listDessert[0];

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viết một information message
        Log.i("MainActivity","onCreate Called");
        // sử dụng binding để tham chiếu đến view
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.dessertButton.setOnClickListener{
            onDessertClick();
        }
        // set text view, mặc định ban đầu
        binding.revenua = revenua;
        binding.amountSold = dessertSold;

        // chắc chắn rằng chính xác món đang được hiển thị
        binding.dessertButton.setImageResource(currentDessert.ImageId);
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity","Onstart Called");
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity","OnResume Called");
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity","OnPause Called");
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity","OnStop Called");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity","OnDestroy Called");
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity","OnRestart Called");
    }
    //    cập nhập score khi click vào món tráng miệng. Và có thể thấy món tráng miệng mới
    private fun onDessertClick() {
        // update score
    // mỗi lần click thì tăng số lượng sản phẩm bán được và doanh thu của nó
        revenua += currentDessert.price; // tăng doanh thu
        dessertSold++; // tăng số lượng sản phẩm lên 1
        binding.revenua = revenua; // tiền kiếm được
        binding.amountSold = dessertSold;

        showCurrentDessert();
    }
    /**
     * Determine which dessert to show.
     */
    private fun showCurrentDessert() {
        var newDessert = listDessert[0]
        for (dessert in listDessert) {
            // nếu số lượng món bán được lớn hơn số lượng bắt đầu để sản xuất món mới
            if (dessertSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
            // you'll start producing more expensive desserts as determined by startProductionAmount
            // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
            // than the amount sold.
            else break
        }

        // If the new dessert is actually different than the current dessert, update the image
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.ImageId)
        }
    }


    /**
     * Menu methods
     */
    private fun onShare() {
        val shareIntentActivity = Intent(Intent.ACTION_SEND)
        shareIntentActivity.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text,dessertSold,revenua))
        // bắt try catch để bắt trường hợp có lỗi khi không tìm thấy activity
        try {
            startActivity(shareIntentActivity)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }
}
