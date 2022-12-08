package com.app.santasgifts.view.g

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.santasgifts.BaseViewModel
import com.app.santasgifts.R
import com.app.santasgifts.data.CardItem
import com.app.santasgifts.data.FlipState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GViewModel(private val app: Application) : BaseViewModel(app) {

    val listGameItems = MutableLiveData<MutableList<CardItem>>(mutableListOf())
    val mainItem = MutableLiveData<CardItem>()
    val winText = MutableLiveData("")


    fun startGame() {
        viewModelScope.launch {
            val cItemsList = mutableListOf(
                CardItem(R.drawable.one, FlipState.FRONT),
                CardItem(R.drawable.twooo, FlipState.FRONT),
                CardItem(R.drawable.freee, FlipState.FRONT),
                CardItem(R.drawable.foooour, FlipState.FRONT),
                CardItem(R.drawable.fiiiiv, FlipState.FRONT),
                CardItem(R.drawable.e6, FlipState.FRONT),
                CardItem(R.drawable.one, FlipState.FRONT),
                CardItem(R.drawable.twooo, FlipState.FRONT),
                CardItem(R.drawable.freee, FlipState.FRONT),
                CardItem(R.drawable.foooour, FlipState.FRONT),
                CardItem(R.drawable.fiiiiv, FlipState.FRONT),
                CardItem(R.drawable.e6, FlipState.FRONT)
            )

            cItemsList.shuffle()
            listGameItems.postValue(cItemsList)
            mainItem.postValue(cItemsList.random())

            delay(2500)
            listGameItems.postValue(cItemsList)
        }
    }

    fun iF(activeItem: CardItem) {
        viewModelScope.launch {
            val main = mainItem.value!!.resId

            delay(300)

            if (activeItem.resId == main) {

                val firstIndex = listGameItems.value!!.indexOf(activeItem)
                listGameItems.value!![firstIndex] =
                    activeItem.copy(flipState = FlipState.INVISIBLE)


                // check win
                if (listGameItems.value!!.filter { it.flipState != FlipState.INVISIBLE }
                        .none { it.resId == main }) {
                    delay(250)
                    winText.postValue("You are win")
                    startGame()
                }
            }
            listGameItems.postValue(listGameItems.value)
        }
    }
}