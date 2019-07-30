package com.example.duong.androidtrivia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.duong.androidtrivia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        // tìm navigation controller object chính là trỏ đến fragment ở trong main_layout
        val navController = this.findNavController(R.id.myNavHostFragment)
        // liên kết navigation controller object với cả action bar
        // để hiển thị option menu và drawer button lên action bar, drawer button là 1 conponet trong NavigatioUI rồi
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout);
        // liên kết navigation controller với cả navgationView trong DrawerLayout
        NavigationUI.setupWithNavController(binding.navView, navController);


    }
    // điều hướng sang các fragment tương ứng khi click vào item
    // override fun để thực hiện các hành vi điều hướng
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment);
        return NavigationUI.navigateUp(drawerLayout,navController);
    }
}
