-- Crear la base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'DBAutores')
BEGIN
    CREATE DATABASE DBAutores;
END
GO

USE DBAutores;
GO

-- Crear la tabla si no existe
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Autor]') AND type in (N'U'))
BEGIN
    CREATE TABLE Autor (
        IdAutor INT IDENTITY(1,1) PRIMARY KEY,
        Nombre VARCHAR(100) NOT NULL,
        Pais VARCHAR(50) NOT NULL,
        Publicaciones INT NOT NULL,
        FechaNacimiento DATE NOT NULL,
        FechaPrimeraPublicacion DATE NOT NULL,
        CONSTRAINT CHK_FechaPublicacion CHECK (FechaPrimeraPublicacion > FechaNacimiento),
        CONSTRAINT CHK_Publicaciones CHECK (Publicaciones >= 0)
    );
END
GO

-- Crear el usuario de la aplicación si no existe
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'usuario_crud')
BEGIN
    CREATE LOGIN usuario_crud WITH PASSWORD = 'Crud2024#';
END
GO

-- Crear el usuario en la base de datos
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'usuario_crud')
BEGIN
    CREATE USER usuario_crud FOR LOGIN usuario_crud;
END
GO

-- Asignar permisos al usuario
ALTER ROLE db_datareader ADD MEMBER usuario_crud;
ALTER ROLE db_datawriter ADD MEMBER usuario_crud;
GO

-- Insertar datos de ejemplo
IF NOT EXISTS (SELECT TOP 1 1 FROM Autor)
BEGIN
    INSERT INTO Autor (Nombre, Pais, Publicaciones, FechaNacimiento, FechaPrimeraPublicacion)
    VALUES 
        ('Gabriel García Márquez', 'Colombia', 25, '1927-03-06', '1947-12-17'),
        ('Jorge Luis Borges', 'Argentina', 50, '1899-08-24', '1923-01-15'),
        ('Isabel Allende', 'Chile', 20, '1942-08-02', '1982-01-22'),
        ('Julio Cortázar', 'Argentina', 15, '1914-08-26', '1938-01-01'),
        ('Mario Vargas Llosa', 'Perú', 30, '1936-03-28', '1957-01-01');
END
GO

-- Mostrar información de conexión
SELECT 
    'Información de conexión para la aplicación:' as Info,
    'usuario_crud' as Usuario,
    'Crud2024#' as Contraseña,
    'localhost' as Servidor,
    'DBAutores' as BaseDeDatos;
GO