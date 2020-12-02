package com.zxuru.gui;

import com.zxuru.Conexion;
import com.zxuru.dao.DaoArticulo;
import com.zxuru.model.Articulo;
import com.zxuru.model.Trabajador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Principal extends JFrame {
    private Trabajador trab;
    private JPanel panel1;
    private JList list1;
    private JButton refrescarButton;
    private JTable table1;
    private JButton agregarArticuloALaButton;
    private JButton retrocederButton;
    private JTabbedPane tabbedPane1;

    private DefaultListModel listModel;
    private DefaultTableModel tableModel;

    private Conexion myCon;
    private DaoArticulo daoArt;

    public Principal(){
        super("");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        add(panel1);
        setSize(new Dimension(500,600));
        pack();

        String ip = "localhost";
        String db = "libreria";
        String user = "root";
        String password = "";

        try {
            myCon = new Conexion(ip, user, password, db);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión:" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        daoArt = new DaoArticulo(myCon);

        tableModel = new DefaultTableModel();
        table1.setModel(tableModel);

        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Precio");

        listModel = new DefaultListModel();
        list1.setModel(listModel);

        refreshTable();

        agregarArticuloALaButton.addActionListener(e -> {

        });

        refrescarButton.addActionListener(e -> {
            refreshTable();
        });

        retrocederButton.addActionListener(e -> {
            this.dispose();
            new Menu().setVisible(true);
        });
    }

    public void refreshTable() {

        List<Articulo> tarjList = daoArt.getAll();

        for (int i = tableModel.getRowCount(); i > 0; i--) {
            tableModel.removeRow(i - 1);
        }

        for (Articulo t : tarjList) {
            if (t.getActive()==1){
                String[] val = new String[]{String.valueOf(t.getId()),t.getNombre(), String.valueOf(t.getCantidad()), String.valueOf(t.getPrecio())};
                tableModel.addRow(val);
            }
        }
    }
}
