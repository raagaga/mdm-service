package com.jsw.mes.mdm.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import org.springframework.data.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Response<T> {

  private static final Response<?> EMPTY = new Response<>();

  private final T content;

  private Integer pageNumber;
  private Integer pageSize;
  private Boolean lastPage;
  private Boolean firstPage;
  private Integer totalPages;
  private Long totalElements;

  private Response() {
    this.content = null;
    this.pageNumber = null;
    this.pageSize = null;
    this.lastPage = null;
    this.firstPage = null;
    this.totalPages = null;
    this.totalElements = null;
  }

  private Response(T content) {
    this.content = Objects.requireNonNull(content);
  }

  private Response(T content, Page<?> page) {
    this.content = Objects.requireNonNull(content);
    this.pageNumber = page.getNumber();
    this.pageSize = page.getSize();
    this.lastPage = page.isLast();
    this.firstPage = page.isFirst();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
  }

  public static <T> Response<T> empty() {
    @SuppressWarnings("unchecked")
    Response<T> c = (Response<T>) EMPTY;
    return c;
  }

  public static <T> Response<T> of(T content) {
    return new Response<>(content);
  }

  public static <T> Response<T> of(T content, Page<?> page) {
    return new Response<>(content, page);
  }

  public T getContent() {
    return content;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Boolean getLastPage() {
    return lastPage;
  }

  public Boolean getFirstPage() {
    return firstPage;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public Long getTotalElements() {
    return totalElements;
  }
}
