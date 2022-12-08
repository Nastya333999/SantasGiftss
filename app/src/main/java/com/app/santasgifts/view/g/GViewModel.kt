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
            val cardItemsList = mutableListOf(
                CardItem(R.drawable.e1, FlipState.FRONT),
                CardItem(R.drawable.e2, FlipState.FRONT),
                CardItem(R.drawable.e3, FlipState.FRONT),
                CardItem(R.drawable.e4, FlipState.FRONT),
                CardItem(R.drawable.e5, FlipState.FRONT),
                CardItem(R.drawable.e6, FlipState.FRONT),
                CardItem(R.drawable.e1, FlipState.FRONT),
                CardItem(R.drawable.e2, FlipState.FRONT),
                CardItem(R.drawable.e3, FlipState.FRONT),
                CardItem(R.drawable.e4, FlipState.FRONT),
                CardItem(R.drawable.e5, FlipState.FRONT),
                CardItem(R.drawable.e6, FlipState.FRONT)
            )

            cardItemsList.shuffle()
            listGameItems.postValue(cardItemsList)
            mainItem.postValue(cardItemsList.random())

            delay(2500)
            listGameItems.postValue(cardItemsList)
        }
    }

    fun itemFlipped(activeItem: CardItem) {
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