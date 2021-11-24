package un4.collections

data class Tienda(val nombre: String, val clientes: List<Clientes>) {
    fun obtenerConjuntoDeClientes(): Set<Clientes> = clientes.toSet()
    fun obtenerCiudadesDeClientes(): Set<Ciudad> =clientes.map { it.ciudad }.toSet()
    fun obtenerClientesPor(ciudad: Ciudad): List<Clientes> = clientes.filter { it.ciudad == ciudad }
    fun checkTodosClientesSonDe(ciudad: Ciudad): Boolean = clientes.all { it.ciudad == ciudad }
    fun hayClientesDe(ciudad: Ciudad): Boolean = clientes.any { it.ciudad == ciudad }
    fun cuentaClientesDe(ciudad: Ciudad): Int = clientes.count { it.ciudad == ciudad }
    fun encuentraClienteDe(ciudad: Ciudad): Clientes? = clientes.find { it.ciudad == ciudad }
    fun obtenerClientesOrdenadosPorPedidos(): List<Clientes> = clientes.sortedByDescending { it.pedidos.size }
    fun obtenerClientesConPedidosSinEngregar(): Set<Clientes> =
        clientes.partition { cliente->cliente.pedidos.all { it.estaEntregado > !it.estaEntregado } }.second.toSet()

    fun obtenerProductosPedidos(): Set<Producto> =
        clientes.flatMap { it.pedidos }.flatMap { it.productos }.toSet()

    fun obtenerProductosPedidosPorTodos(): Set<Producto> =
        clientes.fold(obtenerProductosPedidos()){acc, cliente -> acc.intersect(cliente.pedidos.flatMap { it.productos }.toSet()) }

    fun obtenerNumeroVecesProductoPedido(producto: Producto): Int = clientes.flatMap { cliente->cliente.pedidos.flatMap { it.productos} }.count{it==producto}
    fun agrupaClientesPorCiudad(): Map<Ciudad, List<Clientes>> = clientes.groupBy { it.ciudad }
    fun mapeaNombreACliente(): Map<String, Clientes> = clientes.associateBy { it.nombre }
    fun mapeaClienteACiudad(): Map<Clientes, Ciudad> = clientes.associateWith { it.ciudad }
    fun mapeaNombreClienteACiudad(): Map<String, Ciudad> = clientes.associate { Pair(it.nombre,it.ciudad) }
    fun obtenerClientesConMaxPedidos(): Clientes? = clientes.maxByOrNull { it.pedidos.size }

}

data class Clientes(val nombre: String, val ciudad: Ciudad, val pedidos: List<Pedido>) {
    override fun toString() = "$nombre from ${ciudad.nombre}"

    fun obtenerProductosPedidos(): List<Producto> =
        pedidos.flatMap { it.productos }.toList()

    fun encuentraProductoMasCaro(): Producto? = pedidos.filter { it.estaEntregado }.flatMap { it.productos }.maxByOrNull { it.precio }
    fun obtenerProductoMasCaroPedido(): Producto? = pedidos.flatMap { it.productos }.maxByOrNull { it.precio }
    fun dineroGastado(): Double = pedidos.flatMap { it.productos}.sumOf { it.precio }
}

data class Pedido(val productos: List<Producto>, var estaEntregado: Boolean)

data class Producto(val nombre: String, val precio: Double) {
    override fun toString() = "'$nombre' for $precio"
}

data class Ciudad(val nombre: String) {
    override fun toString() = nombre
}

fun main() {
    val cadiz = Ciudad("Cadiz")
    val tenerife = Ciudad("Tenerife")
    val albondigas = Producto("albondigas", 3.50)
    val acelgas = Producto("acelgas", 1.50)
    val pipas = Producto("pipas", 1.0)
    val listaPedido1 = listOf(albondigas)
    val listaPedido2 = listOf(acelgas)
    val listaPedido3 = listOf(pipas)
    val pedido1 = Pedido(listaPedido1, false)
    val pedido2 = Pedido(listaPedido2, false)
    val pedido3 = Pedido(listaPedido3, true)
    val pedidosCliente1 = listOf(pedido1)
    val pedidosCliente2 = listOf(pedido2, pedido3)
    val pedidosCliente3 = listOf(pedido3)
    val cliente1 = Clientes("Paco", cadiz, pedidosCliente1)
    val cliente2 = Clientes("Rodolfo", tenerife, pedidosCliente2)
    val cliente3 = Clientes("Juan", cadiz, pedidosCliente3)
    val listaClientes = listOf(cliente1, cliente2, cliente3)
    val mercadona = Tienda("Mercadona", listaClientes)
    println(mercadona.obtenerCiudadesDeClientes())
    println(mercadona.obtenerConjuntoDeClientes())
    println(mercadona.obtenerClientesPor(cadiz))
    println(mercadona.checkTodosClientesSonDe(tenerife))
    println(mercadona.hayClientesDe(tenerife))
    println(mercadona.cuentaClientesDe(cadiz))
    println(mercadona.encuentraClienteDe(tenerife))
    println(mercadona.obtenerClientesOrdenadosPorPedidos())
    println(mercadona.obtenerClientesConPedidosSinEngregar())
    println(cliente1.obtenerProductosPedidos())
    println(mercadona.obtenerProductosPedidos())
    println(mercadona.obtenerProductosPedidosPorTodos())
    println(cliente2.encuentraProductoMasCaro())
    println(mercadona.obtenerNumeroVecesProductoPedido(pipas))
    println(mercadona.agrupaClientesPorCiudad())
    println(mercadona.mapeaNombreACliente())
    println(mercadona.mapeaClienteACiudad())
    println(mercadona.mapeaNombreClienteACiudad())
    println(mercadona.obtenerClientesConMaxPedidos())
    println(cliente1.obtenerProductoMasCaroPedido())
    println(cliente2.dineroGastado())
}