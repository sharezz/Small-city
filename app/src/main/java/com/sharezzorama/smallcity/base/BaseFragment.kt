package com.sharezzorama.smallcity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharezzorama.smallcity.base.mvp.MvpAppCompatFragment

open class BaseFragment: MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        check(javaClass.isAnnotationPresent(Layout::class.java)) { "Class must be annotated @Layout!" }
        val layoutId = (javaClass.getAnnotation(Layout::class.java) as Layout).id
        return inflater.inflate(layoutId, container, false)
    }
}