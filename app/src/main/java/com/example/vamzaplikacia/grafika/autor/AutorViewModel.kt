package com.example.vamzaplikacia.grafika.autor

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamzaplikacia.logika.internet.RetrofitAPI
import com.example.vamzaplikacia.logika.knihy.AutorData
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

/**
 * View model pre detail autora - ak treba vyhľadá cez internet info o autorovi.
 *
 */
class AutorViewModel : ViewModel(){

    val autorInfo = mutableStateOf<AutorData?>(null)

    fun stiahniAutorInfo(autorMeno: String) {
        viewModelScope.launch {
            try {
                val searchResponse = RetrofitAPI.api.hladajAutorov(autorMeno).awaitResponse()
                if (searchResponse.isSuccessful && searchResponse.body() != null) {
                    val autorId = searchResponse.body()!!.docs.firstOrNull()?.key?.split("/")?.last()
                    if (autorId != null) {
                        val detailsResponse = RetrofitAPI.api.getAutorInfo(autorId).awaitResponse()
                        if (detailsResponse.isSuccessful && detailsResponse.body() != null) {
                            autorInfo.value = detailsResponse.body()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.ERROR
            }
        }
    }
}