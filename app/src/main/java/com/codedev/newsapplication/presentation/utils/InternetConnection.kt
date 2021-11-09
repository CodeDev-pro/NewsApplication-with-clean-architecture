package com.codedev.newsapplication.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val TAG = "InternetConnection"

class InternetConnection(context: Context, private val scope: CoroutineScope) {

    private val _connection = MutableSharedFlow<Boolean>(0)
    val connection = _connection.asSharedFlow()

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    init {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "onAvailable: $network")
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val isInternet = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                Log.d(TAG, "onAvailable: $network $isInternet")
                if (isInternet == true) {
                    validNetworks.add(network)
                }
                checkValidNetwork()
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "onLost: $network")
                validNetworks.remove(network)
                checkValidNetwork()
            }
        }

    private fun checkValidNetwork() {
        scope.launch {
            _connection.emit(validNetworks.size > 0)
        }
    }
}