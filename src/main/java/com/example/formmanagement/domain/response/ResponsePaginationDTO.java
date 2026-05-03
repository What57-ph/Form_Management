package com.example.formmanagement.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponsePaginationDTO {
    Meta meta;
    Object result;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Meta{
        int page;
        int pageSize;
        int pages;
        long total;
    }
}
