package com.example.duong.getdatainternet.detail

import android.app.Application
import android.arch.lifecycle.*
import com.example.duong.getdatainternet.network.MarsProperty
import com.example.duong.getdatainternet.R
class DetailViewModel(@Suppress("UNUSED_PARAMETER")marsProperty: MarsProperty,
                      app: Application) : AndroidViewModel(app) {
    // property được chọn
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }
    // có thể cho sang binding adapter thực hiện thì sử dụng app:xxx--> là annotation ở name
    // sử dụng tại viewmodel thì sử dụng android text
    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString( // sử dụng getString để format dữ liệu vào. Nối chuỗi
            // app.applicationContext.getString để bao ngoài.
            when (it.isRental) {
                true ->   R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price)
    }
    // chuyern dữ liệu dang số và format để hiển thị
    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(R.string.display_type,
            app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }))
    }
}