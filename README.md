# Sistema de Gestión de Autores

Aplicación CRUD desarrollada en Java con interfaz gráfica para gestionar información de autores.

## Estructura del Proyecto
```
AUTORESAPP/
├── GestionAutores/      # Proyecto principal
├── database.sql         # Script de base de datos
└── README.md           # Este archivo
```

## Requisitos Previos

1. Java 11 o superior
2. SQL Server 2019 o superior
3. NetBeans IDE
4. Maven (incluido en NetBeans)

## Configuración Inicial

### Base de Datos
1. Abrir SQL Server Management Studio
2. Conectarse con autenticación de Windows
3. Abrir el archivo `database.sql`
4. Ejecutar el script completo

El script creará:
- Base de datos: DBAutores
- Tabla: Autor
- Usuario: usuario_crud (password: Crud2024#)
- Datos de ejemplo

### Verificación de SQL Server
1. Asegurarse que SQL Server está configurado para:
   - Autenticación mixta (Windows y SQL Server)
   - TCP/IP habilitado (usar SQL Server Configuration Manager)
   - Puerto 1433 accesible

2. Probar conexión en SQL Server Management Studio:
   - Servidor: localhost
   - Autenticación: SQL Server
   - Usuario: usuario_crud
   - Contraseña: Crud2024#

## Ejecución del Proyecto

1. Abrir el proyecto en NetBeans:
   - File -> Open Project
   - Seleccionar la carpeta GestionAutores

2. Ejecutar el proyecto:
   - Click derecho en el proyecto
   - Run
   - O presionar F6

## Funcionalidades

- Visualización de autores en tabla
- Agregar nuevos autores
- Editar autores existentes
- Eliminar autores
- Validación de datos:
  - Nombres y países obligatorios
  - Fechas en formato válido
  - Número de publicaciones positivo
  - Fecha de primera publicación poste