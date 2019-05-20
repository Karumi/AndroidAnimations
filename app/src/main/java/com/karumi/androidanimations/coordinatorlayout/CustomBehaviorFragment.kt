package com.karumi.androidanimations.coordinatorlayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment

@SuppressLint("RestrictedApi")
class CustomBehaviorFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_custom_behavior, container, false)
}