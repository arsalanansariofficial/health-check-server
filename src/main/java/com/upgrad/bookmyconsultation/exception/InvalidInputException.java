package com.upgrad.bookmyconsultation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidInputException extends Exception{
    private List<String> attributeNames;
}
