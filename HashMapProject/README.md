# Proyecto: Implementación de HashMap (Tabla Hash)

## Descripción
Este proyecto consiste en la implementación desde cero de una estructura de datos tipo **Tabla Hash** en Java. El objetivo principal es construir una estructura robusta utilizando **Encadenamiento Separado** (Separate Chaining) para el manejo de colisiones y **rehashing dinámico** para mantener la eficiencia.

Características principales:
* **Genérico:** Soporta tipos de datos `<K, V>`.
* **Manejo de Colisiones:** Uso de listas enlazadas (`LinkedList`) en cada bucket.
* **Redimensionamiento Automático:** La tabla duplica su capacidad cuando el factor de carga supera 0.75.

## Estructura del Proyecto
El código está organizado siguiendo la arquitectura solicitada:

```text
HashMapProject/
├── src/
│   └── estructuras/
│       ├── Diccionario.java  (Interfaz)
│       └── TablaHash.java    (Implementación lógica)
├── test/
│   └── TestTablaHash.java    (Clase de pruebas)
└── README.md
