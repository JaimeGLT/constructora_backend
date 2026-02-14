-- 1. CREACIÓN DE TIPOS ENUM (sin rol_usuario)
CREATE TYPE tipo_movimiento AS ENUM ('ENTRADA', 'SALIDA');
CREATE TYPE estado_asistencia AS ENUM ('A TIEMPO', 'RETRASO');

-- 2. TABLA DE USUARIOS (ROL es VARCHAR ahora)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) DEFAULT 'OBRERO',  -- ✅ VARCHAR en lugar de ENUM
    cargo VARCHAR(50),
    salario_diario DECIMAL(10, 2) DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE,
    ultimo_gps_texto VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABLA DE MATERIALES
CREATE TABLE materiales (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    stock INTEGER DEFAULT 0,
    stock_maximo INTEGER DEFAULT 500,
    porcentaje_cantidad INT
);

-- 4. TABLA DE MOVIMIENTOS
CREATE TABLE movimientos_inventario (
    id SERIAL PRIMARY KEY,
    material_id INT NOT NULL,
    tipo tipo_movimiento NOT NULL,
    cantidad INTEGER NOT NULL,
    nota VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES materiales(id) ON DELETE CASCADE
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
    id INT PRIMARY KEY,
    latitud_centro DECIMAL(10, 8) NOT NULL,
    longitud_centro DECIMAL(11, 8) NOT NULL,
    radio_permitido_metros INT DEFAULT 100
);