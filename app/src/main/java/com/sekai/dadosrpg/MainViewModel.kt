package com.sekai.dadosrpg

import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel : ViewModel() {
    //mvalor quantidade de dados
    var mValor = MutableLiveData<Int>().apply { value = 1 }
    //dados multiplos
    var cont = MutableLiveData<Int>().apply { value = 1 }



}