package com.mitrais.cdcpos.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedDto<T> {

    private List<T> currentPageContent;
    private int currentPage;
    private int totalPages;

    public PaginatedDto(List<T> currentPageContent, int currentPage, int totalPages) {
        this.currentPageContent = currentPageContent;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
