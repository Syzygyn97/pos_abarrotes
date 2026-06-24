package com.abarrotes.pos.vista;

import com.abarrotes.pos.modelo.ProductoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de Inventario - Estructura visual fija asegurada
 * @author Sebastian Caloca Guzman
 */
public class frmInventario extends javax.swing.JFrame {
    
    ProductoDAO dao = new ProductoDAO();
    
    public frmInventario() {
        initComponents();
        setLocationRelativeTo(null);
        setSize(1920, 1080);
        
        // Colores de fondo traslúcidos para tus paneles
        pnlNorte.setBackground(new java.awt.Color(102, 204, 255, 60));
        pnlNorteCentro.setBackground(new java.awt.Color(102, 204, 255, 60));
        pnlSur.setBackground(new java.awt.Color(102, 204, 255, 60));
        
        txtCodigo.setEditable(false);
        txtCodigo.setText("Auto");
        
        // Carga los artículos almacenados al abrir la ventana
        listarProductos();
    }
    
    public final void listarProductos() {
        try {
            String[] titulos = {"Código", "Nombre del producto", "Categoría", "Precio Compra", "Precio Venta", "Cantidad Stock"};
            
            DefaultTableModel modeloEstructurado = new DefaultTableModel(null, titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
            };

            List<Object[]> lista = dao.listarProductos();
            if (lista != null) {
                for (Object[] fila : lista) {
                    modeloEstructurado.addRow(fila);
                }
            }
            
            jTable1.setModel(modeloEstructurado);
            jTable1.revalidate();
            jTable1.repaint();
            
        } catch (Exception e) {
            System.err.println("Error al actualizar tabla: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("Auto");
        txtNombreProducto.setText("");
        txtCategoria.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtCantidadSctock.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlNorte = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnMenuPrincipal = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        pnlNorteCentro = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        lblNombreProducto = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        lblPrecioCompra = new javax.swing.JLabel();
        lblPrecioVenta = new javax.swing.JLabel();
        lblCantidadStock = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtCantidadSctock = new javax.swing.JTextField();
        pnlSur = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);

        pnlNorte.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Inventario");

        btnMenuPrincipal.setText("Menu Principal");
        btnMenuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPrincipalActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        javax.swing.GroupLayout pnlNorteLayout = new javax.swing.GroupLayout(pnlNorte);
        pnlNorte.setLayout(pnlNorteLayout);
        pnlNorteLayout.setHorizontalGroup(
            pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuPrincipal)
                    .addComponent(lblUsuario))
                .addGap(650, 650, 650)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNorteLayout.setVerticalGroup(
            pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNorteLayout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMenuPrincipal))
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlNorteCentro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCodigo.setText("Código:");
        lblNombreProducto.setText("Nombre del producto:");
        lblCategoria.setText("Categoría:");
        lblPrecioCompra.setText("Precio Compra:");
        lblPrecioVenta.setText("Precio Venta:");
        lblCantidadStock.setText("Cantidad Stock:");

        javax.swing.GroupLayout pnlNorteCentroLayout = new javax.swing.GroupLayout(pnlNorteCentro);
        pnlNorteCentro.setLayout(pnlNorteCentroLayout);
        pnlNorteCentroLayout.setHorizontalGroup(
            pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteCentroLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlNorteCentroLayout.createSequentialGroup()
                        .addComponent(lblCategoria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNorteCentroLayout.createSequentialGroup()
                        .addComponent(lblNombreProducto)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNorteCentroLayout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrecioCompra)
                    .addComponent(lblPrecioVenta)
                    .addComponent(lblCantidadStock))
                .addGap(18, 18, 18)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrecioCompra)
                    .addComponent(txtPrecioVenta)
                    .addComponent(txtCantidadSctock, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pnlNorteCentroLayout.setVerticalGroup(
            pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteCentroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecioCompra)
                    .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreProducto)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecioVenta)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlNorteCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoria)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidadStock)
                    .addComponent(txtCantidadSctock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pnlSur.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSurLayout = new javax.swing.GroupLayout(pnlSur);
        pnlSur.setLayout(pnlSurLayout);
        pnlSurLayout.setHorizontalGroup(
            pnlSurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnlSurLayout.setVerticalGroup(
            pnlSurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurLayout.createSequentialGroup()
                .addGap(0,0,0)
                .addGroup(pnlSurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        // 🔥 REDISEÑO SEGURO DE CONTENEDORES PARA EVITAR EL SOLAPAMIENTO:
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNorte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1900, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(pnlNorteCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(760, 760, 760)
                .addComponent(pnlSur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlNorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNorteCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlSur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (txtNombreProducto.getText().trim().isEmpty() || txtPrecioCompra.getText().trim().isEmpty() || 
                txtPrecioVenta.getText().trim().isEmpty() || txtCantidadSctock.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos obligatorios ⚠");
                return;
            }

            boolean exito = dao.agregarProducto(
                txtNombreProducto.getText().trim(),
                txtCategoria.getText().trim(),
                Double.parseDouble(txtPrecioCompra.getText().trim()),
                Double.parseDouble(txtPrecioVenta.getText().trim()),
                Integer.parseInt(txtCantidadSctock.getText().trim())
            );

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Producto guardado exitosamente! ✔");
                limpiarCampos();
                listarProductos(); 
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en el Inventario ❌");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Verifica que los precios tengan formato decimal y el stock sea entero.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPrincipalActionPerformed
        try {
            frmVentanaPrincipal inicio = new frmVentanaPrincipal();
            inicio.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al regresar al menú: " + e.getMessage());
        }
    }//GEN-LAST:event_btnMenuPrincipalActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto de la tabla para editar ⚠");
            return;
        }
        
        try {
            int id = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 0).toString());
            boolean editado = dao.modificarProducto(
                id,
                txtNombreProducto.getText().trim(),
                txtCategoria.getText().trim(),
                Double.parseDouble(txtPrecioCompra.getText().trim()),
                Double.parseDouble(txtPrecioVenta.getText().trim()),
                Integer.parseInt(txtCantidadSctock.getText().trim())
            );

            if (editado) {
                JOptionPane.showMessageDialog(this, "¡Producto modificado con éxito! ✔");
                limpiarCampos();
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto de la tabla para eliminar ⚠");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este artículo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 0).toString());
            if (dao.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente ✔");
                limpiarCampos();
                listarProductos();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            txtCodigo.setText(jTable1.getValueAt(fila, 0).toString());
            txtNombreProducto.setText(jTable1.getValueAt(fila, 1).toString());
            txtCategoria.setText(jTable1.getValueAt(fila, 2).toString());
            txtPrecioCompra.setText(jTable1.getValueAt(fila, 3).toString());
            txtPrecioVenta.setText(jTable1.getValueAt(fila, 4).toString());
            txtCantidadSctock.setText(jTable1.getValueAt(fila, 5).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new frmInventario().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMenuPrincipal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCantidadStock;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioCompra;
    private javax.swing.JLabel lblPrecioVenta;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlNorte;
    private javax.swing.JPanel pnlNorteCentro;
    private javax.swing.JPanel pnlSur;
    private javax.swing.JTextField txtCantidadSctock;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    // End of variables declaration//GEN-END:variables
}