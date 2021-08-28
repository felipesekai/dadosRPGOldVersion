package com.sekai.dadosrpg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.sekai.dadosrpg.DataBase.helper.HelperDB
import com.sekai.dadosrpg.Historico.Historico
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_navigation_layout.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var btnRolar: Button
    lateinit var spinnerDados: Spinner
    lateinit var resultado: TextView
    lateinit var mViewModel: MainViewModel
    var helperDB = HelperDB(this)
    var valor: Int = 6
    var valor2: Int = 6
    val numDados = arrayListOf(6, 4, 8, 10, 12, 20, 100)
    val numDadosString = arrayListOf("D6", "D4", "D8", "D10", "D12", "D20", "D100")
    var listaHistorico = arrayListOf<Historico>()
    lateinit var adapter: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_navigation_layout)
        adapter = ArrayAdapter(this, R.layout.dropdown_spinner_meu, numDadosString)
        init()
        initOnClick()
        initDrawer()

    }

    private fun initDrawer(){
        val drawer = drawer_layout
        val toolbar : Toolbar = toolbar_app as Toolbar

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,drawer,
            toolbar,
            R.string.open_navigation,
            R.string.close_navigation)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
    private fun initOnClick() {
        btnRolar.setOnClickListener {


            var historico: Historico? = null

            if (mViewModel.cont.value == 1) {

                var listaRestultado =
                    mViewModel.mValor.value?.let { it1 -> multiplosDados(valor, it1) }
                edtResultado_total.text =
                    resultadoTotal(listaRestultado as ArrayList<Int>).toString()
                resultado.setText("D$valor:${stringResultados(listaRestultado as ArrayList<Int>)}")
                historico = Historico(resultado.text.toString())


            } else if (mViewModel.cont.value == 2) {
                var listaRestultado = mViewModel.mValor.value?.let { it1 -> multiplosDados(valor, it1) }
                var listaRestultado2 = mViewModel.mValor2.value?.let { it1 -> multiplosDados(valor2, it1) }
                var total = resultadoTotal(listaRestultado as ArrayList<Int>)+resultadoTotal(listaRestultado2 as ArrayList<Int>)
                edtResultado_total.text = total.toString()
                resultado.setText("D$valor:${stringResultados(listaRestultado )} \n" +
                        "D$valor2:${stringResultados(listaRestultado2)}")
                historico = Historico(resultado.text.toString())

            }

            try {
                if (historico != null) {
                    helperDB.insertNoDB(historico)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            historico()


        }
        btn_add.setOnClickListener {
            doisDados()
        }
        btn_delete2.setOnClickListener {
            mViewModel.cont.value = mViewModel.cont.value?.minus(1)
            //visiveis
            btn_add.visibility = View.VISIBLE
            //setbotoes invisiveis
            btn_add2.visibility = View.INVISIBLE
            btn_delete2.visibility = View.INVISIBLE
            spinnerNumDados2.visibility = View.INVISIBLE
            txt_dados_inv.visibility = View.INVISIBLE
            txt_quant_inv.visibility = View.INVISIBLE
            addQ_mais_dados_inv.visibility = View.INVISIBLE
            addQ_menos_dados_inv.visibility = View.INVISIBLE
            dados_quantidade_inv.visibility = View.INVISIBLE
        }
        btn_add2.setOnClickListener {

        }


        addQ_mais_dados.setOnClickListener {
            mViewModel.mValor.value = mViewModel.mValor.value?.plus(1)
            dados_quantidade.text = mViewModel.mValor.value.toString()
        }
        addQ_menos_dados.setOnClickListener {
            mViewModel.mValor.value = mViewModel.mValor.value?.minus(1)
            if (mViewModel.mValor.value!! < 1) {
                mViewModel.mValor.value = 1
            }
            dados_quantidade.text = mViewModel.mValor.value.toString()
        }
        addQ_mais_dados_inv.setOnClickListener {
            mViewModel.mValor2.value = mViewModel.mValor2.value?.plus(1)
            dados_quantidade_inv.text = mViewModel.mValor2.value.toString()
        }
        addQ_menos_dados_inv.setOnClickListener {
            mViewModel.mValor2.value = mViewModel.mValor2.value?.minus(1)
            if (mViewModel.mValor2.value!! < 1) {
                mViewModel.mValor2.value = 1
            }
            dados_quantidade_inv.text = mViewModel.mValor2.value.toString()
        }

    }

    fun init() {
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        btnRolar = findViewById(R.id.btnRolar)
        spinnerDados = findViewById(R.id.spinnerNumDados)
        resultado = findViewById(R.id.edtResultado)


        spinnerDados.adapter = adapter

        adapter.setDropDownViewResource(R.layout.dropdown_spinner_meu)

        spinnerDados.onItemSelectedListener = object :

        // mViewModel.mValor.observe(this, Observer {
        //    valor.setText(it)
        // })
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                valor = numDados.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        historico()

    }

    fun doisDados() {
        mViewModel.cont.value = 2
        spinnerNumDados2.visibility = View.VISIBLE
        btn_add.visibility = View.INVISIBLE

        btn_add2.visibility = View.VISIBLE
        btn_delete2.visibility = View.VISIBLE
        txt_dados_inv.visibility = View.VISIBLE
        txt_quant_inv.visibility = View.VISIBLE
        addQ_mais_dados_inv.visibility = View.VISIBLE
        addQ_menos_dados_inv.visibility = View.VISIBLE
        dados_quantidade_inv.visibility = View.VISIBLE

        spinnerNumDados2.adapter = adapter
        spinnerNumDados2.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                valor2 = numDados.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun historico() {
        try {
            listaHistorico = helperDB.exibirHisorico() as ArrayList<Historico>
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        var lista = ""
        for (i in listaHistorico) {
            lista += "${i.historico} \n"
        }
        txt_listaHistorico.text = lista
    }

    override fun onResume() {
        super.onResume()

        if (mViewModel.cont.value == 2) {
            doisDados()
        }
    }


    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun multiplosDados(valorDoDado: Int, mValor:Int): MutableList<Int> {

        var rolarDado = roll(valorDoDado)
        var arrayDados: MutableList<Int> = arrayListOf()
        var num = mValor.minus(1)

        for (i in 0..num) {
            arrayDados.add(rolarDado)
            rolarDado = roll(valorDoDado)

        }
        return arrayDados
    }

    private fun roll(dado: Int): Int {
        var dado = Random.nextInt(1, dado + 1)

//        for (i in 0..2){
//            dado = Random.nextInt(1,(dado+1))
//        }

        return dado
    }

    private fun stringResultados(lista: ArrayList<Int>): String {
        var resultado = ""
        lista.forEach { i ->
            resultado += "$i, "

        }
        return resultado
    }

    private fun resultadoTotal(lista: ArrayList<Int>): Int {
        var total = 0
        lista.forEach { i ->
            total += i

        }
        return total
    }

}