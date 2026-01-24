package com.artuur.hrms.dto;

import com.artuur.hrms.entities.TimeRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record TimeRecordResponseDTO(
        UUID timeRecordId,
        UUID employeeId,
        LocalDate date,
        LocalTime entryHour,
        LocalTime breakStart,
        LocalTime breakEnd,
        LocalTime exitHour,
        String type,
        boolean validated,
        String nextAction // Campo extra para ajudar o Frontend
) {
    public TimeRecordResponseDTO(TimeRecord record) {
        this(
                record.getTimeRecordId(),
                record.getEmployee().getEmployeeId(),
                record.getDate(),
                record.getEntryHour(),
                record.getBreakStart(),
                record.getBreakEnd(),
                record.getExitHour(),
                record.getType().name(),
                record.isValidated(),
                determineNextAction(record)
        );
    }

    private static String determineNextAction(TimeRecord record) {
        if (record.getEntryHour() == null) return "ENTRADA";
        if (record.getBreakStart() == null) return "INICIO_INTERVALO";
        if (record.getBreakEnd() == null) return "FIM_INTERVALO";
        if (record.getExitHour() == null) return "SAIDA";
        return "CONCLUIDO";
    }
}