package com.charan.norton.common.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidNetworkChecker @Inject constructor(
    @param:ApplicationContext private val context: Context
) : NetworkChecker {

    /**
     * Returns true if the device has an active internet connection.
     */
    override fun isConnected(): Boolean = isNetworkAvailable(context)
}