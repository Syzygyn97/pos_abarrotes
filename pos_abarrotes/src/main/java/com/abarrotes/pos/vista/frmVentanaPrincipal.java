package com.abarrotes.pos.vista;

/**
 * Formulario de Menú Principal.
 * @author Sebastian Caloca Guzman
 */
public class frmVentanaPrincipal extends javax.swing.JFrame {
    
    private String nombreUsuario;
    
    public frmVentanaPrincipal() {
        initComponents();
        configurarEstilo();
    }

    public frmVentanaPrincipal(String usuario) {
        initComponents();
        this.nombreUsuario = usuario;
        this.lblSaludo.setText("Bienvenido " + usuario);
        configurarEstilo();
    }
    
    private void configurarEstilo() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setSize(265, 260);
        pnlFondo.setBackground(new java.awt.Color(102, 204, 255, 60));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnPuntoVenta = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        lblSaludo = new javax.swing.JLabel();
        pnlFondo = new javax.swing.JPanel();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inicio", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPuntoVenta.setText("Punto de venta");
        btnPuntoVenta.addActionListener(this::btnPuntoVentaActionPerformed);
        jPanel1.add(btnPuntoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 150, 50));

        btnInventario.setText("Inventario");
        btnInventario.addActionListener(this::btnInventarioActionPerformed);
        jPanel1.add(btnInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 150, 50));

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);
        jPanel1.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, -1, -1));

        lblSaludo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSaludo.setText("Bienvenido");
        jPanel1.add(lblSaludo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        pnlFondo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );

        jPanel1.add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 170, 150));

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondo_ventanaprincipal1.jpg"))); // NOI18N
        jPanel1.add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 230));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPuntoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntoVentaActionPerformed
        frmPuntoVenta venta = new frmPuntoVenta();
        venta.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPuntoVentaActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        frmInventario inventario = new frmInventario();
        inventario.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
            this, "¿Estás seguro de querer cerrar sesión?", "Cerrar Sesión", 
            javax.swing.JOptionPane.YES_NO_OPTION
        );
        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            frmLogin login = new frmLogin();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new frmVentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnPuntoVenta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblSaludo;
    private javax.swing.JPanel pnlFondo;
    // End of variables declaration//GEN-END:variables
}