package com.dsw01.practica02.dto;

public class PaginationQuery {

    private final Integer page;
    private final Integer size;

    public PaginationQuery(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public int getPageOrDefault() {
        return page == null ? 0 : page;
    }

    public int getSizeOrDefault() {
        return size == null ? 5 : size;
    }

    public void validate() {
        if (getPageOrDefault() < 0) {
            throw new IllegalArgumentException("page debe ser mayor o igual a 0");
        }
        if (getSizeOrDefault() != 5) {
            throw new IllegalArgumentException("size debe ser exactamente 5");
        }
    }
}
