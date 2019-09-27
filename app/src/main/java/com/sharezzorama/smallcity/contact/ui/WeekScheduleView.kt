package com.sharezzorama.smallcity.contact.ui

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.sharezzorama.smallcity.ProjectConstants
import com.sharezzorama.smallcity.R
import kotlinx.android.synthetic.main.layout_schedule.view.*

class WeekScheduleView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private val daysOfWeek = context.resources.getStringArray(R.array.daysOfWeekShort)

    companion object {
        const val MONDAY_INDEX = 0
        const val TUESDAY_INDEX = 1
        const val FRIDAY_INDEX = 4
        const val SUNDAY_INDEX = 6
    }

    init {
        orientation = VERTICAL
        fillSchedule()
    }

    private fun fillSchedule() {
        for (i in MONDAY_INDEX until daysOfWeek.size) {
            val view = View.inflate(context, R.layout.layout_schedule, null)

            view.vWorkSchedule.inputType = InputType.TYPE_CLASS_NUMBER
            view.vWorkSchedule.keyListener = DigitsKeyListener.getInstance("1234567890:- ")
            MaskedTextChangedListener.Companion.installOn(view.vWorkSchedule, ProjectConstants.SCHEDULE_PATTERN)

            view.vLunchBreakSchedule.inputType = InputType.TYPE_CLASS_NUMBER
            view.vLunchBreakSchedule.keyListener = DigitsKeyListener.getInstance("1234567890:- ")
            MaskedTextChangedListener.Companion.installOn(view.vLunchBreakSchedule, ProjectConstants.SCHEDULE_PATTERN)

            view.vDayOfWeek.text = daysOfWeek[i]

            addView(view, i)

            val isMonday = i == MONDAY_INDEX
            if (isMonday) {

                view.vWorkSchedule.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus)
                        copyWorkSchedule((v as EditText).text)
                }

                view.vLunchBreakSchedule.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus)
                        copyLaunchBreakSchedule((v as EditText).text)
                }
            }
        }
    }

    fun getWeekSchedule(): List<String> {
        val schedule = mutableListOf<String>()
        for (i in MONDAY_INDEX..SUNDAY_INDEX) {
            val child = getChildAt(i)
            val scheduleLine = "${child?.vWorkSchedule?.text},${child?.vLunchBreakSchedule?.text}"
            schedule.add(scheduleLine)
        }
        return schedule
    }

    /**
     * Paste the work schedule to all views except holidays
     */
    private fun copyWorkSchedule(workSchedule: Editable?) {
        for (i in TUESDAY_INDEX..FRIDAY_INDEX) {
            val child = getChildAt(i)
            if (child?.vWorkSchedule?.text?.isEmpty() == true) {
                child.vWorkSchedule.text = workSchedule
            }
        }
    }

    /**
     * Paste the lunch break schedule to all views except holidays
     */
    private fun copyLaunchBreakSchedule(lunchBreakSchedule: Editable?) {
        for (i in TUESDAY_INDEX..FRIDAY_INDEX) {
            val child = getChildAt(i)
            if (child?.vLunchBreakSchedule?.text?.isEmpty() == true) {
                child.vLunchBreakSchedule.text = lunchBreakSchedule
            }
        }
    }

    interface OnScheduleChangeListener {
        fun onScheduleChanged()
    }
}