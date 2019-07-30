package com.example.duong.trackmysleepquality.screentracker

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.duong.trackmysleepquality.database.SleepNight
import com.example.duong.trackmysleepquality.databinding.ListItemSleepNightBinding


// tức là khi khởi tạo đối tượng sẽ khởi tạo luôn đt SleepNightDiffCallBack()
// sử dụng ListAdapter là extensions của RecyclerView
class SleepNightAdapter(val clickListener: SleepNightListener) : android.support.v7.recyclerview.extensions.ListAdapter<SleepNight,SleepNightAdapter.ViewHolder>(SleepNightDiffCallBack()) {
// truyền thêm đối số listener cho adapter
/*
    var data = listOf<SleepNight>()
        set(value) {
            field = value;
            // thông báo với adapter khi có thay đổi dữ liệu và cập nhật luôn
            notifyDataSetChanged()--> bỏ đi do ListAdapter đã tracker cho rồi
        }
*/

    // khởi tạo
    // param parent là RecyclerView, viewType sử dụng khi nhiều thành phần view trong RecycleView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /*    override fun getItemCount(): Int { --> không cần do listAdapter thực hiện method này
     nếu không extend ListAdapter thì sẽ phải sử có hàm getItemCount
            return data.size;
        }*/
    // hiển thị data lên list,
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener) // đẩy dữ liệu từ data vào viewholder
    }


    // đối số truyền vào là một view., tên của class binding sẽ theo tên của file xml
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){
        // trỏ đến view bằng binding
        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            // sử dụng hàm để thực hiện các binding ngay lập tức
            binding.executePendingBindings();
        }
/*      --> Do dùng databinding và adapter đã thwucj hiện hết rồi
        val sleepLength: TextView = binding.sleepLength
        val quality: TextView = binding.qualityString
        val qualityImage: ImageView = binding.qualityImage

        fun bind(item: SleepNight) {
            val res = itemView.context.resources
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
        }*/

        // khi muốn định nghĩa hàm thuộc lớp thì sử dụng companion object
        // như static trong java
        // khi đó method from sẽ gọi thẳng lớp ViewHolder k cần khởi tạo
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false)

                // do hàm from trả về lớp ViewHolder thì muốn cho nó vào ViewHolder thì
                // để nó thành static, truyền binding vào
                return ViewHolder(binding);
            }
        }
    }
}

// tạo ra class diffcallback extend từ inferface diffutil và implement method để
// so sánh chênh lệch giữa 2 list
class SleepNightDiffCallBack : DiffUtil.ItemCallback<SleepNight>() {

    // nếu item có id giống nhau thì trả về true, else trả về false
    // sẽ ra được những item đã đã added, removed.
    override fun areItemsTheSame(oldItem: SleepNight?, newItem: SleepNight?): Boolean {
        return oldItem?.nightId == newItem?.nightId
    }
    // nếu có sự khác nhau giữa 2 list này sẽ tiến hành update
    override fun areContentsTheSame(oldItem: SleepNight?, newItem: SleepNight?): Boolean {
        return oldItem == newItem
    }
}

// định nghĩa class listener và hàm onclick
// hàm clickListener callback là contructor và gán nó vào onClick,
// ->Unit là cho Callback
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}
