package estructuras;

import java.util.LinkedList;

/**
 * Implementación de tabla hash con encadenamiento separado.
 * Soporta tipos genéricos y redimensionamiento dinámico.
 * El factor de carga máximo es 0.75, tras lo cual se duplica la capacidad automáticamente.
 * * @param <K> Tipo de las claves
 * @param <V> Tipo de los valores
 * @author [Tu Nombre]
 * @version 1.0
 */
public class TablaHash<K, V> implements Diccionario<K, V> {

    // -----------------------------------------------------------
    // 1.1 Estructura de Clases y Nodos [cite: 31]
    // -----------------------------------------------------------

    /**
     * Clase interna: Almacena un par clave-valor.
     */
    private class Nodo<K, V> {
        K key;
        V value;

        public Nodo(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Atributos principales [cite: 60]
    private LinkedList<Nodo<K, V>>[] tabla; // Arreglo de listas (buckets)
    private int size;                       // Cantidad de elementos almacenados (N)
    private int capacidad;                  // Tamaño del arreglo (M)
    private static final double FACTOR_CARGA_MAX = 0.75;

    /**
     * Constructor por defecto.
     * Inicializa la tabla con capacidad 11 (número primo).
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.capacidad = 11;
        this.tabla = new LinkedList[capacidad];
        this.size = 0;

        // IMPORTANTE: Inicializar cada posición del arreglo [cite: 84]
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    // -----------------------------------------------------------
    // 1.2 Operaciones Básicas [cite: 95]
    // -----------------------------------------------------------

    /**
     * Calcula el índice en el arreglo para una clave dada.
     * Maneja correctamente valores negativos del hashCode.
     * * @param key La clave a hashear
     * @return Índice válido en el rango [0, capacidad-1]
     */
    private int hash(K key) {
        // Operación bitwise AND con 0x7fffffff elimina el signo negativo
        return (key.hashCode() & 0x7fffffff) % capacidad;
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     * Si la clave ya existe, actualiza su valor.
     * Verifica automáticamente el factor de carga y redimensiona si es necesario.
     * * Complejidad: O(1) promedio, O(n) si se requiere resize.
     * * @param key La clave única del elemento (no puede ser null)
     * @param value El valor asociado a la clave
     * @throws NullPointerException si key es null
     */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new NullPointerException("La clave no puede ser nula");

        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        // 3. Recorrer la lista buscando si key ya existe [cite: 115]
        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                nodo.value = value; // Actualizar valor
                return;
            }
        }

        // Si no existe, agregar nuevo nodo
        lista.add(new Nodo<>(key, value));
        size++;

        // 5. Verificar factor de carga [cite: 119]
        if ((double) size / capacidad >= FACTOR_CARGA_MAX) {
            resize();
        }
    }

    /**
     * Recupera el valor asociado a una clave.
     * * Complejidad: O(1) promedio.
     * * @param key La clave a buscar
     * @return El valor asociado, o null si no existe
     */
    @Override
    public V get(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        for (Nodo<K, V> nodo : lista) {
            if (nodo.key.equals(key)) {
                return nodo.value;
            }
        }
        return null; // No encontrado
    }

    /**
     * Elimina un par clave-valor de la tabla.
     * * @param key La clave del elemento a eliminar
     * @return El valor eliminado, o null si la clave no existe
     */
    @Override
    public V remove(K key) {
        int indice = hash(key);
        LinkedList<Nodo<K, V>> lista = tabla[indice];

        // Usamos un bucle for con índice para poder eliminar fácilmente [cite: 153]
        for (int i = 0; i < lista.size(); i++) {
            Nodo<K, V> nodo = lista.get(i);
            if (nodo.key.equals(key)) {
                V valorEliminado = nodo.value;
                lista.remove(i);
                size--;
                return valorEliminado;
            }
        }
        return null;
    }

    /**
     * Verifica si una clave existe en la tabla.
     * * @param key La clave a verificar
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Retorna la cantidad de elementos almacenados.
     * * @return El tamaño actual de la tabla
     */
    @Override
    public int size() {
        return size;
    }

    // -----------------------------------------------------------
    // 1.3 Rehashing Dinámico (Resize) [cite: 195]
    // -----------------------------------------------------------

    /**
     * Redimensiona la tabla hash cuando el factor de carga supera el umbral.
     * Duplica la capacidad y reubica todos los elementos (rehashing).
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        // 1. Guardar referencia al arreglo antiguo
        LinkedList<Nodo<K, V>>[] tablaVieja = tabla;

        // 2. Calcular nueva capacidad
        capacidad = capacidad * 2;

        // 3. Crear nuevo arreglo e inicializar
        tabla = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }

        // Reiniciar size porque put() lo incrementará nuevamente
        size = 0;

        // 4. Recorrer cada bucket del arreglo antiguo y reinsertar [cite: 255]
        for (LinkedList<Nodo<K, V>> bucket : tablaVieja) {
            for (Nodo<K, V> nodo : bucket) {
                // Insertamos en la nueva tabla (esto recalcula el hash automáticamente con la nueva capacidad)
                put(nodo.key, nodo.value);
            }
        }
    }
}