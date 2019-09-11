package com.sharezzorama.smallcity.contact.viewmodel

import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.sharezzorama.smallcity.contact.ui.WeekScheduleView

class ScheduleBinderAdapter {

    companion object {
        @BindingAdapter(value = ["schedule"])
        @JvmStatic
        fun setSchedule(weekScheduleView: WeekScheduleView, observableField: ObservableField<List<String>>) {

            observableField.set(weekScheduleView.schedule)

          /*


            weekScheduleView.scheduleChangeListener = object :WeekScheduleView.OnScheduleChangeListener{
                override fun onScheduleChanged() {
                    observableField?.get()
                }
            }*/
        }
    }
}