package com.example.duong.repository.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telecom.InCallService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.duong.repository.R
import com.example.duong.repository.databinding.DevByteItemBinding
import com.example.duong.repository.databinding.FragmentDevByteBinding
import com.example.duong.repository.model.DevByteVideo
import com.example.duong.repository.viewmodels.DevByteViewModel


class DevByteFragment : Fragment() {

    // sẽ trả ra luôn view model
    private val devByteViewModel: DevByteViewModel by lazy {
        val application = requireNotNull(this.activity).application
        ViewModelProviders.of(this,DevByteViewModel.Factory(application)).get(DevByteViewModel::class.java)
    }

    // Tạo adapter để hiển thị dữ liệu lên recycleview
    private var viewModelAdapter: DevByteAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        devByteViewModel.playlist.observe(this, Observer <List<DevByteVideo>>{videos->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDevByteBinding>(inflater,
            R.layout.fragment_dev_byte,container,false);
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.devByteViewModel = devByteViewModel;
        // tạo đối tượng Adapter
        viewModelAdapter = DevByteAdapter(VideoClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            val packageManager = context?.packageManager ?: return@VideoClick

            // Try to generate a direct intent to the YouTube app
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            // khi intent sang device,app khác của ứng dụng, có thể biến thành đường link ACTION_VIEW
            //  intent sang link ngoài thì cần check resolveActivity, check xem class nhận intent có tồn tại hay k
            if(intent.resolveActivity(packageManager) == null) {
                // intent sang link youtube
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }

            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        devByteViewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            // khi mạng lỗi
            if (isNetworkError == true) {
                onNetworkError()
            }
        })

        return binding.root
    }
    /**
     * Method for displaying a Toast error message for network errors.
     */
    // display message
    private fun onNetworkError() {
        if(!devByteViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            devByteViewModel.onNetworkErrorShown()
        }
    }

    /**
     * Helper method to generate YouTube app links
     */
    // insert thêm param vào DevByteVideo
    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
//            Log.i("Thoa",httpUri.toString())
           Log.i("Duong", Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v")).toString())
               // lấy paramater của url theo key. Ở đây key là v khi getQuery thì sẽ ra value của nó
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"));
        }
}

/*RecycleView Adapter */

class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>(){
    // create thêm list để nhận data
    var videos : List<DevByteVideo> = emptyList()
        set(value) {
            field = value;
            notifyDataSetChanged(); // có gì thay đổi sẽ thông báo cập nhật
        }

    // create viewholder thì return viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding : DevByteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.dev_byte_item,
            parent,false)
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return videos.size;
    }

    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        // scope function, sử dụng also sẽ return về chính nó, k bị thay đổi
        holder.viewDataBinding.also {
            // model
            it.video = videos[position]
            // class listener
            it.videoCallback = callback;
        }
    }
}

/*create ViewHolder*/

class DevByteViewHolder(val viewDataBinding: DevByteItemBinding) :RecyclerView.ViewHolder(viewDataBinding.root){
    companion object {
        // annotation thể hiện đây là lấy từ resource
        @LayoutRes
        val LAYOUT = R.layout.dev_byte_item
    }
}
// các class đều liên quan đến model
class VideoClick(val block : (DevByteVideo)-> Unit) {
    fun onClick(video:DevByteVideo) = block(video)
}
