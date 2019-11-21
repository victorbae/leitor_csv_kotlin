package service

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class ClassifierService {

    private var fileReader: BufferedReader? = null
    private var cancelamentos = ArrayList<String>()
    private var line: String? = null
    private var lista = this.read()

    private fun read(): ArrayList<String> {
        fileReader = BufferedReader(FileReader("cancelamentos.csv"))
        fileReader!!.readLine()
        line = fileReader!!.readLine()
        while (line != null) {
            cancelamentos.add(line!!)
            line = fileReader!!.readLine()
        }
//        File("cancelamentos.csv").forEachLine { cancelamentos.add(it) }
        return cancelamentos
    }


    //    1 - Número de cancelamentos por classificação do cliente, ordenado pelo maior número DESC
    fun cancelamentosPorClassificacao() {

        var agrupado = lista.groupingBy {
            it.split(",")[3]
        }.eachCount()

        print("Número de cancelamentos por classificação do cliente: \n")

        agrupado.entries.sortedByDescending { it.value }.forEach { (key, value) ->
            print("$key - $value  \n")
        }
    }

    //     2 - Média de tempo de vida(dias entre início e cancelamento)
    fun mediaTempoDeVida() {
        var days = 0
        var qntDays = 0
        lista.forEach {

            val colunas = it.split(",")
            var dataInicio = LocalDate.parse(colunas[5].replace("\"", ""))
            var dataFim = LocalDate.parse(colunas[6].replace("\"", ""))

            days += ChronoUnit.DAYS.between(dataInicio, dataFim).toInt()
            qntDays++
        }
        var media = days / qntDays

        print("A média de tempo de vida (dias entre inicio e cancelamento) é de $media dias")
    }

    //    3 - Número de cancelamentos por motivo de cancelamento, ordenado pelo maior número DESC
    fun cancelamentosPorMotivo() {

        var agrupado = lista.groupingBy {
            it.split(",")[8]
        }.eachCount()

        print("Número de cancelamentos por motivo de cancelamento: \n")

        agrupado.entries.sortedByDescending { it.value }.forEach { (key, value) ->
            print("Motivo: $key - Cancelamentos: $value  \n")
        }
    }

    //    4 - Quantidade de usuários cancelados por mês/ano, ordenado pelo mes/ano ASC
    fun usuariosCanceladorPorMes() {

        var agrupado = lista.groupingBy {
            toMonthYear(it.split(",")[6].replace("\"", ""))
        }.eachCount()

        print("Quantidade de usuários cancelados por mês/ano: \n")

        agrupado.entries.sortedByDescending { it.value }.forEach { (key, value) ->
            print("Mês/Ano: $key - Usuários Cancelados: $value  \n")
        }
    }

    private fun toMonthYear(data: String): String {
        val dataCancelamento = LocalDate.parse(data)
        return StringBuilder().append(dataCancelamento.monthValue).append("/").append(dataCancelamento.year).toString()
    }

    //    5 - Quantidade de clientes cancelados por usuário responsável, ordenado por número DESC
    fun clientesCanceladorPorUsuarioResponsavel() {

        var agrupado = lista.groupingBy {
            it.split(",")[1]
        }.eachCount()

        print("Número de cancelamentos por usuário responsável: \n")

        agrupado.entries.sortedByDescending { it.value }.forEach { (key, value) ->
            print("Usuário: $key - Cancelamentos: $value  \n")
        }
    }

}