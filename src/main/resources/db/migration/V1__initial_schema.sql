-- 1. CREACIÓN DE TIPOS ENUM (Se definen una sola vez)
CREATE TYPE rol_usuario AS ENUM ('ADMIN', 'OBRERO');
CREATE TYPE estado_usuario AS ENUM ('ACTIVO', 'INACTIVO');
CREATE TYPE tipo_movimiento AS ENUM ('ENTRADA', 'SALIDA');
CREATE TYPE estado_asistencia AS ENUM ('A TIEMPO', 'RETRASO');

-- 2. TABLA DE USUARIOS
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol rol_usuario DEFAULT 'OBRERO',
    cargo VARCHAR(50),
    salario_diario DECIMAL(10, 2) DEFAULT 0.00,
    estado estado_usuario DEFAULT 'ACTIVO',
    ultimo_gps_texto VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABLA DE MATERIALES
CREATE TABLE materiales (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    unidad VARCHAR(20),
    stock_actual DECIMAL(10, 2) DEFAULT 0,
    stock_minimo DECIMAL(10, 2) DEFAULT 10,
    stock_maximo DECIMAL(10, 2) DEFAULT 500,
    imagen_url VARCHAR(255)
);

-- 4. TABLA DE MOVIMIENTOS
CREATE TABLE movimientos_inventario (
    id SERIAL PRIMARY KEY,
    material_id INT NOT NULL,
    usuario_id INT NOT NULL,
    tipo tipo_movimiento NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    nota VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES materiales(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- 5. TABLA DE ASISTENCIAS
CREATE TABLE asistencias (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    hora_entrada TIME NOT NULL DEFAULT CURRENT_TIME,
    latitud DECIMAL(10, 8),
    longitud DECIMAL(11, 8),
    estado estado_asistencia DEFAULT 'A TIEMPO',
    CONSTRAINT fk_usuario_asistencia FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- 6. CONFIGURACIÓN DE OBRA
CREATE TABLE config_obra (
    id INT PRIMARY KEY, -- Generalmente solo habrá un ID 1
    latitud_centro DECIMAL(10, 8) NOT NULL,
    longitud_centro DECIMAL(11, 8) NOT NULL,
    radio_permitido_metros INT DEFAULT 100
);