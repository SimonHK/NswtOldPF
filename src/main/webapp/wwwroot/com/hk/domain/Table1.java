package com.hk.domain;

public class Table1 {
    private Integer idnewtable;

    private String newtablecol;

    private String table1col;

    private String table1col1;

    public Integer getIdnewtable() {
        return idnewtable;
    }

    public void setIdnewtable(Integer idnewtable) {
        this.idnewtable = idnewtable;
    }

    public String getNewtablecol() {
        return newtablecol;
    }

    public void setNewtablecol(String newtablecol) {
        this.newtablecol = newtablecol == null ? null : newtablecol.trim();
    }

    public String getTable1col() {
        return table1col;
    }

    public void setTable1col(String table1col) {
        this.table1col = table1col == null ? null : table1col.trim();
    }

    public String getTable1col1() {
        return table1col1;
    }

    public void setTable1col1(String table1col1) {
        this.table1col1 = table1col1 == null ? null : table1col1.trim();
    }
}