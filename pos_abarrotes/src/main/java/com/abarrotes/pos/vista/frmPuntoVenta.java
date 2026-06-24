/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.abarrotes.pos.vista;

import com.abarrotes.pos.modelo.ProductoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de Punto de Venta con base de datos ligada, generación de ticket 
 * y descuento automático de inventario al cobrar.
 * @author Daniel, Alan, Bryan, Caloca
 */
public class frmPuntoVenta extends javax.swing.JFrame {
    
    private String nombreUsuario; 
    private final ProductoDAO dao = new ProductoDAO(); // Instancia del DAO para interactuar con la Base de Datos
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmPuntoVenta.class.getName());

    /**
     * Crea un nuevo form frmPuntoVenta
     */
    public frmPuntoVenta() {
        initComponents();
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            buscarYAgregarProductoPorNombre();
        }
    }
});
        
        // --- CONEXIÓN MANUAL DE BOTONES ---
        btnCancelarProducto.addActionListener(this::btnCancelarProductoActionPerformed);
        btnCancelarVenta.addActionListener(this::btnCancelarVentaActionPerformed);
        btnCobrar.addActionListener(this::btnCobrarActionPerformed);
        // ----------------------------------

        this.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
            javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            lblFechaHora.setText(ahora.format(formato));
        });
    
        timer.start();
        
        java.awt.EventQueue.invokeLater(() -> {
            jScrollPane1.setPreferredSize(new java.awt.Dimension(pnlPrincipal.getWidth(), 300));
            pnlPrincipal.revalidate();
            pnlPrincipal.repaint();
        });
        
        // Inicializar la tabla limpia y protegida
        limpiarYEstablecerTabla();
    }
    
    

    // Configura la estructura limpia de la JTable
    private void limpiarYEstablecerTabla() {
        String[] columnas = {"Código", "Producto", "Cantidad", "Precio Unitario", "Importe"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea la edición manual directa sobre las celdas
            }
        };
        tblProductos.setModel(modelo);
    }

    // Procesa la búsqueda interactiva por nombre en la Base de Datos
    private void buscarYAgregarProductoPorNombre() {
        String nombreBusqueda = txtBuscar.getText().trim();
        
        if (nombreBusqueda.isEmpty()) {
            return;
        }

        List<Object[]> coincidencias = dao.buscarPorNombre(nombreBusqueda);

        if (coincidencias == null || coincidencias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre ⚠", "Sin coincidencias", JOptionPane.ERROR_MESSAGE);
            txtBuscar.setText("");
            return;
        }

        Object[] productoSeleccionado = null;

        if (coincidencias.size() == 1) {
            productoSeleccionado = coincidencias.get(0);
        } else {
            String[] opciones = new String[coincidencias.size()];
            for (int i = 0; i < coincidencias.size(); i++) {
                Object[] p = coincidencias.get(i);
                opciones[i] = p[1].toString() + " - $" + p[2].toString();
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Se encontraron múltiples coincidencias. Selecciona el correcto:",
                    "Seleccionar Producto",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion != null) {
                for (int i = 0; i < opciones.length; i++) {
                    if (opciones[i].equals(seleccion)) {
                        productoSeleccionado = coincidencias.get(i);
                        break;
                    }
                }
            }
        }

        if (productoSeleccionado != null) {
            int codigo = Integer.parseInt(productoSeleccionado[0].toString());
            String nombre = productoSeleccionado[1].toString();
            double precioVenta = Double.parseDouble(productoSeleccionado[2].toString());
            int stockDisponible = dao.obtenerStockActual(codigo); // Consultamos stock fresco de la BD con la columna 'codigo'
            
            int cantidadAVender = 1;

            // Verificar si algún producto ya agregado en la tabla consume el stock disponible
            DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
            int cantidadYaEnTabla = 0;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (Integer.parseInt(modelo.getValueAt(i, 0).toString()) == codigo) {
                    cantidadYaEnTabla += Integer.parseInt(modelo.getValueAt(i, 2).toString());
                }
            }

            if (stockDisponible < (cantidadYaEnTabla + cantidadAVender)) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente en almacén. Disponible: " + (stockDisponible - cantidadYaEnTabla), "Sin existencias", JOptionPane.WARNING_MESSAGE);
                txtBuscar.setText("");
                return;
            }

            double importe = precioVenta * cantidadAVender;
            
            modelo.addRow(new Object[]{codigo, nombre, cantidadAVender, precioVenta, importe});
            calcularTotalVenta();
            
            txtBuscar.setText("");
            txtBuscar.requestFocus();
        }
    }

    // Calcula la suma total de las filas de la tabla actual
    private void calcularTotalVenta() {
        double total = 0.0;
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        
        lblMonto.setText(String.format("$%.2f", total));
    }

    @SuppressWarnings("unchecked")

    public void recibirUsuario(String nombre) {
            this.nombreUsuario = nombre;
            lblUsuario.setText("Usuario: " + nombre);
        }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlNorte = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblFechaHora = new javax.swing.JLabel();
        btnMenuprincipal = new javax.swing.JButton();
        pnlSurOeste = new javax.swing.JPanel();
        btnCancelarVenta = new javax.swing.JButton();
        btnCancelarProducto = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        pnlSurEste = new javax.swing.JPanel();
        lblTxtTotal = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        pnlCentroNorte = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);

        pnlPrincipal.setMaximumSize(new java.awt.Dimension(1068, 598));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Punto de Venta");
        lblTitulo.addComponentListener(new java.awt.event.ComponentAdapter() {
            
        });

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsuario.setText("Usuario:");

        lblFechaHora.setText("Fecha");

        btnMenuprincipal.setText("Menu principal");
        btnMenuprincipal.addActionListener(this::btnMenuprincipalActionPerformed);

        javax.swing.GroupLayout pnlNorteLayout = new javax.swing.GroupLayout(pnlNorte);
        pnlNorte.setLayout(pnlNorteLayout);
        pnlNorteLayout.setHorizontalGroup(
            pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(btnMenuprincipal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFechaHora)
                .addGap(12, 12, 12))
        );
        pnlNorteLayout.setVerticalGroup(
            pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNorteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaHora)
                    .addGroup(pnlNorteLayout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btnMenuprincipal))
                    .addComponent(lblTitulo))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pnlSurOeste.setMaximumSize(new java.awt.Dimension(670, 134));

        btnCancelarVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelarVenta.setText("Cancelar Venta");

        btnCancelarProducto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelarProducto.setText("Cancelar producto");

        btnCobrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCobrar.setText("Cobrar");

        javax.swing.GroupLayout pnlSurOesteLayout = new javax.swing.GroupLayout(pnlSurOeste);
        pnlSurOeste.setLayout(pnlSurOesteLayout);
        pnlSurOesteLayout.setHorizontalGroup(
            pnlSurOesteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurOesteLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(btnCancelarVenta)
                .addGap(71, 71, 71)
                .addComponent(btnCancelarProducto)
                .addGap(65, 65, 65)
                .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        pnlSurOesteLayout.setVerticalGroup(
            pnlSurOesteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurOesteLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlSurOesteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTxtTotal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTxtTotal.setText("Total a Pagar");

        lblMonto.setFont(new java.awt.Font("Monospaced", 0, 36)); // NOI18N
        lblMonto.setText("$00.00");

        javax.swing.GroupLayout pnlSurEsteLayout = new javax.swing.GroupLayout(pnlSurEste);
        pnlSurEste.setLayout(pnlSurEsteLayout);
        pnlSurEsteLayout.setHorizontalGroup(
            pnlSurEsteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSurEsteLayout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addGroup(pnlSurEsteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTxtTotal)
                    .addGroup(pnlSurEsteLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblMonto)))
                .addGap(104, 104, 104))
        );
        pnlSurEsteLayout.setVerticalGroup(
            pnlSurEsteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSurEsteLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(lblTxtTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMonto)
                .addGap(20, 20, 20))
        );

        tblProductos.setBackground(new java.awt.Color(204, 204, 204));
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Producto", "Cantidad", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblProductos.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setResizable(false);
            tblProductos.getColumnModel().getColumn(1).setResizable(false);
            tblProductos.getColumnModel().getColumn(2).setResizable(false);
            tblProductos.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Buscar:");

        javax.swing.GroupLayout pnlCentroNorteLayout = new javax.swing.GroupLayout(pnlCentroNorte);
        pnlCentroNorte.setLayout(pnlCentroNorteLayout);
        pnlCentroNorteLayout.setHorizontalGroup(
            pnlCentroNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroNorteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCentroNorteLayout.setVerticalGroup(
            pnlCentroNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCentroNorteLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlCentroNorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(pnlNorte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCentroNorte, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPrincipalLayout.createSequentialGroup()
                .addGap(421, 421, 421)
                .addComponent(pnlSurOeste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
                .addComponent(pnlSurEste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(pnlNorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCentroNorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSurOeste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSurEste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void btnMenuprincipalActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        frmVentanaPrincipal menu = new frmVentanaPrincipal(this.nombreUsuario);
    
    menu.setVisible(true);
    this.dispose(); // Cierra la ventana actual
    }                                                

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        buscarYAgregarProductoPorNombre();
    }                                         

    // Cancela el producto seleccionado
    private void btnCancelarProductoActionPerformed(java.awt.event.ActionEvent evt) {                                                                    
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        int filaSeleccionada = tblProductos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de quitar este producto de la venta?", 
                "Quitar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                modelo.removeRow(filaSeleccionada);
                calcularTotalVenta();
                txtBuscar.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona el producto que deseas quitar de la tabla.", 
                "Seleccionar producto", JOptionPane.WARNING_MESSAGE);
        }
    }                                                                   

    // Cancelar toda la compra
    private void btnCancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {                                                                 
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
        
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la lista actual.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de cancelar toda la venta actual? Se borrarán todos los productos de la lista.", 
            "Confirmar Cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            limpiarYEstablecerTabla();
            lblMonto.setText("$00.00");
            txtBuscar.setText("");
            txtBuscar.requestFocus();
        }
    }                                                                

    // Logica cobrar, generar ticet y descontar de la base de datos
    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No puedes cobrar una venta vacía. Agrega productos primero.", 
                "Sin productos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String totalTexto = lblMonto.getText().replace("$", "").trim();
        double totalApagar = Double.parseDouble(totalTexto);

        String pagoTexto = JOptionPane.showInputDialog(this, 
            "Total a Pagar: $" + String.format("%.2f", totalApagar) + "\n\n¿Con cuánto efectivo paga el cliente?:", 
            "Procesar Pago", JOptionPane.QUESTION_MESSAGE);

        if (pagoTexto != null && !pagoTexto.trim().isEmpty()) {
            try {
                double efectivoRecibido = Double.parseDouble(pagoTexto.trim());

                if (efectivoRecibido < totalApagar) {
                    JOptionPane.showMessageDialog(this, "El dinero recibido es insuficiente para cubrir el total.", 
                        "Efectivo Insuficiente", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double cambio = efectivoRecibido - totalApagar;
                
                // PASO CRUCIAL: Bucle para descontar las cantidades del inventario en MySQL usando 'codigo'
                boolean exitoDescuento = true;
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    int codigoProducto = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                    int cantidadVendida = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    
                    // Ejecutamos la actualización directamente a la BD por medio del DAO (Objeto de Acceso a Datos)
                    boolean actualizado = dao.descontarInventario(codigoProducto, cantidadVendida);
                    if (!actualizado) {
                        exitoDescuento = false;
                    }
                }
                
                if (!exitoDescuento) {
                    JOptionPane.showMessageDialog(this, "Hubo un inconveniente al actualizar las existencias en el inventario. Verifica la base de datos.", 
                        "Error de Inventario", JOptionPane.WARNING_MESSAGE);
                }
                
                // --- Generación del Formato del Ticket Virtual ---
                java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
                java.time.format.DateTimeFormatter formatoFecha = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                
                StringBuilder ticket = new StringBuilder();
                ticket.append("==========================================\n");
                ticket.append("              ABARROTES EL PATRIARCA              \n");
                ticket.append("==========================================\n");
                ticket.append("Fecha/Hora: ").append(ahora.format(formatoFecha)).append("\n");
                ticket.append("Cajero: ").append(this.nombreUsuario != null ? this.nombreUsuario : "General").append("\n");
                ticket.append("------------------------------------------\n");
                ticket.append(String.format("%-22s %-5s %-12s\n", "Producto", "Cant", "Importe"));
                ticket.append("------------------------------------------\n");
                
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String producto = modelo.getValueAt(i, 1).toString();
                    String cantidad = modelo.getValueAt(i, 2).toString();
                    double importe = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                    
                    if (producto.length() > 20) {
                        producto = producto.substring(0, 17) + "...";
                    }
                    
                    ticket.append(String.format("%-22s %-5s $%10.2f\n", producto, cantidad, importe));
                }
                
                ticket.append("------------------------------------------\n");
                ticket.append(String.format("%-28s $%10.2f\n", "TOTAL A PAGAR:", totalApagar));
                ticket.append(String.format("%-28s $%10.2f\n", "EFECTIVO:", efectivoRecibido));
                ticket.append(String.format("%-28s $%10.2f\n", "SU CAMBIO:", cambio));
                ticket.append("==========================================\n");
                ticket.append("       ¡GRACIAS POR SU COMPRA!       \n");
                ticket.append("==========================================\n");

                JTextArea areaTextoTicket = new JTextArea(ticket.toString());
                areaTextoTicket.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
                areaTextoTicket.setEditable(false);
                
                JOptionPane.showMessageDialog(this, areaTextoTicket, "Ticket de Transacción", JOptionPane.INFORMATION_MESSAGE);

                // Limpieza total para la siguiente venta
                limpiarYEstablecerTabla();
                lblMonto.setText("$00.00");
                txtBuscar.requestFocus();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Monto de efectivo inválido. Introduce solo números.", 
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                         

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frmPuntoVenta().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancelarProducto;
    private javax.swing.JButton btnCancelarVenta;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnMenuprincipal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechaHora;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTxtTotal;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlCentroNorte;
    private javax.swing.JPanel pnlNorte;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlSurEste;
    private javax.swing.JPanel pnlSurOeste;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration                   
}