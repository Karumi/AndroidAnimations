package com.karumi.androidanimations.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras

fun sharedElements(vararg views: View): FragmentNavigator.Extras =
    FragmentNavigatorExtras(*views.map { it to it.transitionNameCompat }.toTypedArray())

val View.transitionNameCompat: String
    get() = ViewCompat.getTransitionName(this) ?: ""