package com.abarrotes.pos.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de productos del Inventario.
 * @author Sebastian Caloca Guzman
 */
public class ProductoDAO {

    private final Conexion cn = new Conexion();

    // ==========================================
    // 1. INSERTAR PRODUCTO (Guardar)
    // ==========================================
    public boolean agregarProducto(String nombre, String categoria, double precioCompra, double precioVenta, int stock) {
        String sql = "INSERT INTO inventario (nombre, categoria, precio_compra, precio_venta, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, categoria);
            ps.setDouble(3, precioCompra);
            ps.setDouble(4, precioVenta);
            ps.setInt(5, stock);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> agregarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==========================================
    // 2. LISTAR PRODUCTOS (Para rellenar jTable1)
    // ==========================================
    public List<Object[]> listarProductos() {
        List<Object[]> lista = new ArrayList<>();
        // Forzamos la consulta con el nombre exacto de la columna 'codigo'
        String sql = "SELECT codigo, nombre, categoria, precio_compra, precio_venta, stock FROM inventario";

        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("codigo"); 
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("categoria");
                fila[3] = rs.getDouble("precio_compra");
                fila[4] = rs.getDouble("precio_venta");
                fila[5] = rs.getInt("stock");

                lista.add(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> listarProductos: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================================
    // 3. MODIFICAR PRODUCTO (Editar)
    // ==========================================
    public boolean modificarProducto(int id, String nombre, String categoria, double precioCompra, double precioVenta, int stock) {
        // 🔥 CORREGIDO: Se cambió 'WHERE id=?' por 'WHERE codigo=?' para coincidir con tu BD
        String sql = "UPDATE inventario SET nombre=?, categoria=?, precio_compra=?, precio_venta=?, stock=? WHERE codigo=?";

        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, categoria);
            ps.setDouble(3, precioCompra);
            ps.setDouble(4, precioVenta);
            ps.setInt(5, stock);
            // 🔥 CORREGIDO: Se usa la variable 'id' que entra como parámetro en el método
            ps.setInt(6, id); 

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> modificarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ==========================================
    // 4. ELIMINAR PRODUCTO
    // ==========================================
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM inventario WHERE codigo=?";

        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> eliminarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // ==========================================
    // 6. BUSCAR PRODUCTOS POR NOMBRE (Para Punto de Venta)
    // ==========================================
    public List<Object[]> buscarPorNombre(String nombreBusqueda) {
        List<Object[]> lista = new ArrayList<>();
        // Usamos LIKE para que encuentre coincidencias en cualquier parte del nombre
        String sql = "SELECT codigo, nombre, precio_venta, stock FROM inventario WHERE nombre LIKE ?";
        
        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + nombreBusqueda + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] producto = new Object[4];
                    producto[0] = rs.getInt("codigo");
                    producto[1] = rs.getString("nombre");
                    producto[2] = rs.getDouble("precio_venta");
                    producto[3] = rs.getInt("stock");
                    lista.add(producto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> buscarPorNombre: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // ==========================================
    // 7. NUEVO: OBTENER STOCK ACTUAL (Para validación en Punto de Venta)
    // ==========================================
    public int obtenerStockActual(int codigoProducto) {
        String sql = "SELECT stock FROM inventario WHERE codigo = ?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, codigoProducto);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> obtenerStockActual: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // ==========================================
    // 8. NUEVO: DESCONTAR INVENTARIO (Resta stock tras la venta)
    // ==========================================
    public boolean descontarInventario(int codigoProducto, int cantidad) {
        String sql = "UPDATE inventario SET stock = stock - ? WHERE codigo = ?";
        
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, cantidad);
            ps.setInt(2, codigoProducto);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO -> descontarInventario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}