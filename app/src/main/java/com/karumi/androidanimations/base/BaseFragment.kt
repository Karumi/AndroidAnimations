package com.karumi.androidanimations.base

import androidx.fragment.app.Fragment
import com.karumi.androidanimations.extensions.StringResourceResolver

open class BaseFragment : Fragment(), StringResourceResolver {
    fun Int.resolve(): String = resolve(requireContext())
}