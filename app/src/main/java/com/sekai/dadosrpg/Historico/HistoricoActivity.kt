package com.sekai.dadosrpg.Historico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekai.dadosrpg.MainActivity
import com.sekai.dadosrpg.R
import com.sekai.dadosrpg.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoricoBinding
    private val listaHistorico = arrayListOf<Historico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        historicoList()
        initToolbar()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.icludeToolbarAppH.root
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun historicoList() {

        val adapter = HistoricoAdapter(listaHistorico)
        binding.rvListHistory.layoutManager = LinearLayoutManager(this)
        binding.rvListHistory.adapter = adapter
    }

    fun preencherLista(lista: ArrayList<Historico>) {
        listaHistorico.clear()
        listaHistorico.addAll(lista)
    }

    companion object{
        val instancia = this
    }
}