package com.sharezzorama.smallcity.base

import androidx.annotation.LayoutRes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Layout(@LayoutRes val id: Int)