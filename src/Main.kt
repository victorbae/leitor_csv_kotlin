import service.ClassifierService


fun main(args: Array<String>) {


    val service = ClassifierService()

    print("\n --------- \n")
    service.mediaTempoDeVida()
    print("\n\n --------- \n")
    service.cancelamentosPorClassificacao()
    print("\n --------- \n")
    service.cancelamentosPorMotivo()
    print("\n --------- \n")
    service.usuariosCanceladorPorMes()
    print("\n --------- \n")
    service.clientesCanceladorPorUsuarioResponsavel()


}