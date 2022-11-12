package com.api.busTime.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FormatEntityToDTO {

    public static <T, E> T formatEntityToDto(E elementObj, Supplier<T> supplier) {
        T dto = supplier.get();
        BeanUtils.copyProperties(elementObj, dto);
        return dto;
    }

    public static <T, E> List<T> formatListEntityToListDTO(List<E> list, Supplier<T> supplier) {
        return list.stream().map(elementObj ->
                formatEntityToDto(elementObj, supplier)
        ).collect(Collectors.toList());
    }

    public static <T, E> Page<T> formatPageEntityToPageDto(Page<E> page, Supplier<T> supplier) {
        return page.map(elementObj ->
                formatEntityToDto(elementObj, supplier)
        );
    }
}
