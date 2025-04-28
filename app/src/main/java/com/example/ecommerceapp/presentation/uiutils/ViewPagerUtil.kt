package com.example.ecommerceapp.presentation.uiutils

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ViewPagerUtil {
    fun startAutoScroll(viewPager: ViewPager2, itemCount: Int, lifecycleScope: LifecycleCoroutineScope): Job {
        return lifecycleScope.launch {
            while (true) {
                delay(3000)
                val currentItem = viewPager.currentItem
                if (currentItem == itemCount - 1) {
                    viewPager.setCurrentItem(0, false)
                } else {
                    viewPager.setCurrentItem(currentItem + 1, true)
                }
            }
        }
    }
}