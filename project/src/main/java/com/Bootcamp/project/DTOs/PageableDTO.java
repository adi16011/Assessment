package com.Bootcamp.project.DTOs;

public class PageableDTO {

    private Long offset;

    private Long size;

    public PageableDTO() {
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
