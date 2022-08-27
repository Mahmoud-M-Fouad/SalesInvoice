
package model;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int num;
    private Date date;
    private String name;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int num, Date date, String name) {
        this.num = num;
        this.date = date;
        this.name = name;
    }

    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public double getTotal() {
        double total = 0.0;
        
        for (InvoiceLine line : getLines()) {
            total += line.getTotal();
        }
        
        return total;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + ", date=" + date + ", name=" + name + '}';
    }
}
