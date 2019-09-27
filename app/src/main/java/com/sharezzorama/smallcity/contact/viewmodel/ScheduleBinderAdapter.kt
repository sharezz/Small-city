package com.sharezzorama.smallcity.contact.viewmodel

import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.sharezzorama.smallcity.contact.ui.WeekScheduleView

open class ScheduleBinderAdapter {

    companion object {
        @BindingAdapter(value = ["week_schedule"])
        @JvmStatic
        fun setSchedule(weekScheduleView: WeekScheduleView, observableField: ObservableField<MutableList<String>>) {

            observableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    observableField.get()?.apply {
                        clear()
                        addAll(weekScheduleView.getWeekSchedule())
                    }
                }
            })
        }
    }
}