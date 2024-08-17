package edu.ifba.saj.demo_crud.util;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateUtils {

    public Long getIdadeEmAnos(LocalDate dataAniversario) {
        return ChronoUnit.YEARS.between(dataAniversario, LocalDate.now());
    }

}
