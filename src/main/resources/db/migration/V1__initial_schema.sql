-- 1. TABLA DE USUARIOS (Limpia, sin gps_texto y rol directo en VARCHAR)
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) DEFAULT 'OBRERO', 
    cargo VARCHAR(50),
    estado_asistencia VARCHAR(20) DEFAULT 'SIN MARCAR',
    salario_diario DECIMAL(10, 2) DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. TABLA DE MATERIALES
CREATE TABLE materiales (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    stock INTEGER DEFAULT 0,
    stock_maximo INTEGER DEFAULT 500,
    porcentaje_cantidad INT
);

-- 3. TABLA DE MOVIMIENTOS (Tipo ya es VARCHAR desde el inicio, nada de ENUMs)
CREATE TABLE movimientos_inventario (
    id SERIAL PRIMARY KEY,
    material_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL, 
    cantidad INTEGER NOT NULL,
    nota VARCHAR(255),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES materiales(id) ON DELETE CASCADE
);

-- 4. TABLA DE ASISTENCIAS (Coordenadas en DOUBLE PRECISION y candado UNIQUE incluido)
CREATE TABLE asistencias (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    hora_entrada TIME NOT NULL DEFAULT CURRENT_TIME,
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION,
    estado VARCHAR(20) DEFAULT 'A TIEMPO',
    CONSTRAINT fk_usuario_asistencia FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT unica_asistencia_diaria UNIQUE (usuario_id, fecha)
);

-- 5. CONFIGURACIÓN DE OBRA (Coordenadas en DOUBLE PRECISION)
CREATE TABLE config_obra (
    id INT PRIMARY KEY,
    latitud_centro DOUBLE PRECISION NOT NULL,
    longitud_centro DOUBLE PRECISION NOT NULL,
    radio_permitido_metros INT DEFAULT 100
);

-- 6. DATOS INICIALES OBLIGATORIOS (Para evitar tu Error 500)
INSERT INTO config_obra (id, latitud_centro, longitud_centro, radio_permitido_metros) 
VALUES (1, -17.3895, -66.1568, 50);