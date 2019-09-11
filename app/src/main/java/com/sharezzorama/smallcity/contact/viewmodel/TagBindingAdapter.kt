package com.sharezzorama.smallcity.contact.viewmodel

import android.view.View
import androidx.databinding.*
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.chip.ChipGroup
import com.sharezzorama.getInflater
import com.sharezzorama.smallcity.BR
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.data.entity.Tag

open class TagBindingAdapter {

    companion object {

        @BindingAdapter(value = ["tag_list"])
        @JvmStatic
        fun setTagList(group: ChipGroup,
                       tagList: ObservableArrayList<Tag>
        ) {

            val newTagLayoutId = R.layout.tag_item
            var listener = ListenerUtil.getListener<TagChangeListener>(group, R.id.tagListener)

            if (listener == null) {
                listener = TagChangeListener(group, newTagLayoutId)
                ListenerUtil.trackListener(group, listener, R.id.tagListener)
                (tagList as ObservableList<Tag>).addOnListChangedCallback(listener as ObservableList.OnListChangedCallback<out ObservableList<Tag>>)
            }

            resetTags(group, newTagLayoutId, tagList)
        }

        @JvmStatic
        private fun bindTag(group: ChipGroup, layoutId: Int, tag: Any, tags: ObservableList<Tag>) =
                DataBindingUtil.inflate<ViewDataBinding>(group.getInflater(), layoutId, group, false).apply {
                    setVariable(BR.tag, tag)
                    setVariable(BR.removeListener, View.OnClickListener {   tags.remove(tag)})
                }

        @JvmStatic
        private fun resetTags(group: ChipGroup, layoutId: Int, tags: ObservableList<Tag>) {
            group.removeAllViews()

            if (layoutId == 0) {
                return
            }

            tags.forEach { tag -> group.addView(bindTag(group, layoutId, tag, tags).root) }
        }


    }


    private class TagChangeListener(private val target: ChipGroup, private val layoutId: Int) : ObservableList.OnListChangedCallback<ObservableList<Tag>>() {
        override fun onChanged(sender: ObservableList<Tag>) {
            resetTags(target, layoutId, sender)
        }

        override fun onItemRangeRemoved(sender: ObservableList<Tag>, positionStart: Int, itemCount: Int) {
            if (layoutId == 0) {
                return
            }

            for (i in 0 until itemCount) {
                target.removeViewAt(positionStart)
            }
        }

        override fun onItemRangeMoved(sender: ObservableList<Tag>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (layoutId == 0) {
                return
            }

            for (i in 0..itemCount) {
                val child = target.getChildAt(fromPosition)
                target.removeViewAt(fromPosition)
                val destination = if (fromPosition > toPosition) toPosition + i else toPosition
                target.addView(child, destination)
            }
        }

        override fun onItemRangeInserted(sender: ObservableList<Tag>, positionStart: Int, itemCount: Int) {
            if (layoutId == 0) {
                return
            }

            val end = positionStart + itemCount
            for (i in end - 1 downTo positionStart) {
                val binding = bindTag(target, layoutId, sender[i], sender)
                target.addView(binding.root, i)

            }
        }

        override fun onItemRangeChanged(sender: ObservableList<Tag>, positionStart: Int, itemCount: Int) {
            if (layoutId == 0) {
                return
            }

            for (i in positionStart..positionStart + itemCount) {
                val binding = bindTag(target, layoutId, sender[i], sender)
                target.removeViewAt(i)
                target.addView(binding.root, i)

            }
        }

    }
}