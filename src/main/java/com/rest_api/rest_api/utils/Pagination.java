package com.rest_api.rest_api.utils;

public class Pagination {
    private int limit;
    private int offset;
    private int total = -1;

    public Pagination(int offset, int limit) {
        this(offset, limit, 0);
    }

    public Pagination(int offset, int limit, int total) {
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Pagination { limit: " + limit + ", offset: " + offset + (total == -1 ? "" : (", total available records: " + total)) + "}";
    }
}