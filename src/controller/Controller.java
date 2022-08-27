/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceHeader;
import model.InvoiceHeaderTableModel;
import model.InvoiceLine;
import view.SIGFrame;

/**
 *
 * @author DELL
 */
public class Controller implements ActionListener, ListSelectionListener{

    private SIGFrame frame;
    
    public Controller(SIGFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (e.getActionCommand()) {
            case "Create Invoice":
                createInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create Item":
                createItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "Load Files":
                loadFiles(null, null);
                break;
            case "Save Data":
                saveData();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoicesTable().getSelectedRow();
        if (selectedIndex >= 0) {
            InvoiceHeader invoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText(""+invoice.getNum());
            frame.getInvoiceDateLabel().setText(frame.sdf.format(invoice.getDate()));
            frame.getCustomerNameLabel().setText(invoice.getName());
            frame.getInvoiceTotalLabel().setText(""+invoice.getTotal());
        }
    }
    
    public void loadFiles(String headrPath, String linePath) {
        File headerFile = null;
        File lineFile = null;
        if (headrPath == null && linePath == null) {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                headerFile = fc.getSelectedFile();
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    lineFile = fc.getSelectedFile();
                }
            }
        } else {
            headerFile = new File(headrPath);
            lineFile = new File(linePath);
        }

        if (headerFile != null && lineFile != null) {
            try {
                /*
                1,22-11-2020,Ali
                2,13-10-2021,Saleh
                 */
                // collection streams
                List<String> headerLines = Files.lines(Paths.get(headerFile.getAbsolutePath())).collect(Collectors.toList());
                /*
                1,Mobile,3200,1
                1,Cover,20,2
                1,Headphone,130,1	
                2,Laptop,9000,1
                2,Mouse,135,1
                 */
                List<String> lineLines = Files.lines(Paths.get(lineFile.getAbsolutePath())).collect(Collectors.toList());
                //ArrayList<Invoice> invoices = new ArrayList<>();
                frame.getInvoices().clear();
                for (String headerLine : headerLines) {
                    String[] parts = headerLine.split(",");  // "1,22-11-2020,Ali"  ==>  ["1", "22-11-2020", "Ali"]
                    String numString = parts[0];
                    String dateString = parts[1];
                    String name = parts[2];
                    int num = Integer.parseInt(numString);
                    Date date = frame.sdf.parse(dateString);
                    InvoiceHeader inv = new InvoiceHeader(num, date, name);
                    //invoices.add(inv);
                    frame.getInvoices().add(inv);
                }
                System.out.println("Check point");
                for (String lineLine : lineLines) {
                    // lineLine = "1,Mobile,3200,1"
                    String[] parts = lineLine.split(",");
                    /*
                    parts = ["1", "Mobile", "3200", "1"]
                     */
                    int num = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    InvoiceHeader inv = frame.getInvoiceByNum(num);
                    InvoiceLine line = new InvoiceLine(name, price, count, inv);
                    inv.getLines().add(line);
                }
                System.out.println("Check point");
                frame.setHeaderTableModel(new InvoiceHeaderTableModel(frame.getInvoices()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void createInvoice() {
    }

    private void deleteInvoice() {
    }

    private void createItem() {
    }

    private void deleteItem() {
    }

    private void saveData() {
    }
    
}
