/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import view.SIGFrame;

/**
 *
 * @author DELL
 */
public class InvoiceHeaderTableModel extends AbstractTableModel {

    private String[] columns = {"Num", "Date", "Name", "Total"};
    private ArrayList<InvoiceHeader> invoices;
    
    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return invoice.getNum();
            case 1:
                return SIGFrame.sdf.format(invoice.getDate());
            case 2:
                return invoice.getName();
            case 3:
                return invoice.getTotal();
        }
        
        return "";
    }
    
}
