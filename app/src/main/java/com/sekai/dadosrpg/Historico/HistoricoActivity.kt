package com.sekai.dadosrpg.Historico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekai.dadosrpg.DataBase.helper.aplication.ApplicationDb
import com.sekai.dadosrpg.MainActivity
import com.sekai.dadosrpg.R
import com.sekai.dadosrpg.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoricoBinding
    private var listaHistorico = arrayListOf<Historico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historicoList()
        initToolbar()
    }

    private fun initToolbar(){
        val toolbar : Toolbar = binding.includeToolbarAppH.root
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_nav_back_24)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun historicoList() {
        historico()
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

    private fun historico() {
        try {
            listaHistorico = ApplicationDb.instancia.helperDB(this).exibirHisorico() as ArrayList<Historico>
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
//        var lista = ""
//        for (i in listaHistorico) {
//            lista += "${i.historico} \n"
//        }
//        txt_listaHistorico.text = lista
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuDraw = menuInflater
        menuDraw.inflate(R.menu.options_menu_historico, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opm_limpar_historico -> {
                ApplicationDb.instancia.helperDB(this).deleteAll()
                historicoList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}