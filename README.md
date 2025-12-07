# Tarea Práctica - Parte 2: Tabla de Ruteo de Red

Este proyecto implementa una **Tabla de Ruteo de Red** funcional utilizando una estructura de datos `TablaHash` personalizada (desarrollada en la Parte 1).El sistema simula el comportamiento de un router real (como Cisco o Juniper) para determinar la ruta óptima de paquetes de datos basándose en direcciones IP de destino, máscaras de subred y métricas.

## 📋 Descripción del Proyecto

El objetivo principal es aplicar la estructura de datos `HashMap` en un caso de uso real de networking. El simulador permite:
1.  **Almacenar rutas:** Rutas estáticas, dinámicas (OSPF, BGP) y conectadas directamente.
2.  **Decisión de Forwarding:** Determinar por qué interfaz enviar un paquete.
3.  **Manejo de Métricas:** Si una ruta ya existe, solo se actualiza si la nueva tiene menor métrica (costo).
4.  **Longest Prefix Match:** Implementación simplificada para encontrar la red más específica para una IP.

##  Estructura del Proyecto

El código fuente se encuentra bajo el paquete `estructuras`.

```text
src/
└── estructuras/
    ├── Diccionario.java      # Interfaz base para el mapa.
    ├── TablaHash.java        # Implementación de Tabla Hash con encadenamiento y resize dinámico.
    ├── Ruta.java             # Modelo de datos (Red, Máscara, NextHop, Interfaz, Métrica).
    ├── TablaRuteo.java       # Lógica de negocio (Agregar, Buscar, Eliminar rutas).
    └── SimuladorRouter.java  # Clase principal (Main) que ejecuta la simulación.****
