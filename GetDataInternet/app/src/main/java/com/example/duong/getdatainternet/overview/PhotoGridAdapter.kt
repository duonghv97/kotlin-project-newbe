package com.example.duong.getdatainternet.overview

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.duong.getdatainternet.network.MarsProperty
import com.example.duong.getdatainternet.databinding.GridViewItemBinding

class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<MarsProperty,PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    // ở ViewHolder sẽ định nghĩa các method. ở ViewHolder sẽ chứa binding của customer_layout
    // để nó còn biết nó hiển thị
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        // có chứa method bind để gán dữ liệu vào binding object để hiển thị lên view
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

/*     định nghĩa đối tượng CallBack implement từ ItemCallBack nên khi khởi tạo Adapter thì
     truyền CallBack vào k cần () để create object nữa. Nếu định nghĩa class DiffCallBack ở bên ngoiaf
     ở bên ngoài thì cần phải truyền vào đó object.*/
    // để so sánh update những item đã bị thay đổi trong list
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(p0: MarsProperty, p1: MarsProperty): Boolean {
            return p0 === p1
        }

        override fun areContentsTheSame(p0: MarsProperty, p1: MarsProperty): Boolean {
            return p0.id == p1.id
        }
    }
}

// create lắng nghe sự kiện sau đó truyền nó vào Adapter
class OnClickListener(val clickListener: (marsProperty:MarsProperty) -> Unit) {
    fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty);
}


