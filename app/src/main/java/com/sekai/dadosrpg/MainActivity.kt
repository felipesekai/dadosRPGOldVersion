package com.sekai.dadosrpg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.sekai.dadosrpg.DataBase.helper.HelperDB
import com.sekai.dadosrpg.Historico.Historico
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var btnRolar: Button
    lateinit var spinnerDados: Spinner
    lateinit var resultado: TextView
    lateinit var mViewModel: MainViewModel
    var helperDB = HelperDB(this)
    var valor: Int = 6
    var valor2: Int = 6
    var valor3: Int = 6
    var valor4: Int = 6
    var numDados = arrayListOf(6, 4, 8, 10, 12, 20, 100)
    var listaHistorico = arrayListOf<Historico>()
    lateinit var adapter: ArrayAdapter<Int>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter(this, R.layout.dropdown_spinner_meu, numDados)
        init()
        initOnClick()

    }


    private fun initOnClick() {
        btnRolar.setOnClickListener {


            var historico: Historico? = null

            if (mViewModel.cont.value == 1) {
                var  listaRestultado = multiplosDados(valor)
                    edtResultado_total.text = resultadoTotal(listaRestultado as ArrayList<Int>).toString()
                    resultado.setText("D$valor:${stringResultados(listaRestultado as ArrayList<Int>)}")
                    historico = Historico(resultado.text.toString())



            } else if (mViewModel.cont.value == 2) {
//                resultado.setText("D$valor:$rolarDado, D$valor2:$rolarDado2")
//                historico = Historico(resultado.text.toString())

            } else if (mViewModel.cont.value == 3) {
//                resultado.setText("D$valor:$rolarDado, D$valor2:$rolarDado2, D$valor3:$rolarDado3")
//                historico = Historico(resultado.text.toString())

            } else if (mViewModel.cont.value == 4) {
//                resultado.setText("D$valor:$rolarDado, D$valor2:$rolarDado2, D$valor3:$rolarDado3, D$valor4:$rolarDado4")
//                historico = Historico(resultado.text.toString())
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
        }
        btn_add2.setOnClickListener {
            tresDados()
        }
        btn_delete3.setOnClickListener {
            mViewModel.cont.value = mViewModel.cont.value?.minus(1)
            //setbotoes visiveis
            btn_add2.visibility = View.VISIBLE
            btn_delete2.visibility = View.VISIBLE
            spinnerNumDados2.visibility = View.VISIBLE
            //setbotoes invisiveis
            btn_add3.visibility = View.INVISIBLE
            btn_delete3.visibility = View.INVISIBLE
            spinnerNumDados3.visibility = View.INVISIBLE
        }
        btn_add3.setOnClickListener {
            quatroDados()
        }
        btn_delete4.setOnClickListener {
            mViewModel.cont.value = mViewModel.cont.value?.minus(1)
            //setbotoes visiveis
            btn_add3.visibility = View.VISIBLE
            btn_delete3.visibility = View.VISIBLE
            spinnerNumDados3.visibility = View.VISIBLE
            //setbotoes invisiveis
            btn_add4.visibility = View.INVISIBLE
            btn_delete4.visibility = View.INVISIBLE
            spinnerNumDados4.visibility = View.INVISIBLE
        }
        btn_add4.setOnClickListener {
            val builder = AlertDialog.Builder(this )
                builder.setTitle("Desculpa!!")
                builder.setMessage("No momento nosso app só suporta o maximo de 4 dados")
                builder.setNegativeButton("Fechar"){dialog, witch ->    }
            val dialog : AlertDialog = builder.create()
            dialog.show()
        }

        addQ_mais_dados.setOnClickListener {
            mViewModel.mValor.value = mViewModel.mValor.value?.plus(1)
            dados_quantidade.text =  mViewModel.mValor.value.toString()
        }
        addQ_menos_dados.setOnClickListener {
            mViewModel.mValor.value = mViewModel.mValor.value?.minus(1)
            if (mViewModel.mValor.value!! < 1){
                mViewModel.mValor.value = 1
            }
            dados_quantidade.text =  mViewModel.mValor.value.toString()
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
        } else if (mViewModel.cont.value == 3) {
            tresDados()
        } else if (mViewModel.cont.value == 4) {
            quatroDados()
        }
    }

    private fun tresDados() {
        doisDados()
        mViewModel.cont.value = 3
        spinnerNumDados3.visibility = View.VISIBLE
        spinnerNumDados3.adapter = adapter
        btn_add2.visibility = View.INVISIBLE
        btn_delete2.visibility = View.INVISIBLE
        btn_add3.visibility = View.VISIBLE
        btn_delete3.visibility = View.VISIBLE

        spinnerNumDados3.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                valor3 = numDados.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun quatroDados() {
        tresDados()
        mViewModel.cont.value = 4

        //nao visivel
        btn_add3.visibility = View.INVISIBLE
        btn_delete3.visibility = View.INVISIBLE
        //visivel
        btn_add4.visibility = View.VISIBLE
        btn_delete4.visibility = View.VISIBLE
        spinnerNumDados4.visibility = View.VISIBLE
        spinnerNumDados4.adapter = adapter

        spinnerNumDados4.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                valor4 = numDados.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun multiplosDados(valorDoDado: Int): MutableList<Int> {

        var rolarDado = Random.nextInt(1, valorDoDado + 1)
        var arrayDados : MutableList<Int> = arrayListOf()
        var num = mViewModel.mValor.value?.minus(1)

        for (i in 0..num!!){
        arrayDados.add(rolarDado)
            rolarDado = Random.nextInt(1, valorDoDado + 1)

        }
        return arrayDados
    }

    private fun stringResultados(lista: ArrayList<Int>): String{
        var resultado = ""
        lista.forEach { i ->
            resultado += "$i, "

        }
        return resultado
    }
    private fun resultadoTotal(lista: ArrayList<Int>): Int{
        var total = 0
        lista.forEach { i ->
            total += i

        }
        return total
    }

}