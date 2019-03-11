package com.epam.ankov.FtpBridge.services.implementation;

import com.epam.ankov.FtpBridge.services.RecordValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SimpleRecordValidator implements RecordValidator {

    @Override
    public boolean validate(String record) {
        if (record.length() == 0) {
            return false;
        }
        String[] fields = record.split(",");
        if (fields.length < 3) {
            return false;
        }
        if (Arrays.stream(fields).filter(String::isEmpty).findAny().isPresent()) {
            return false;
        }
        return true;
    }
}
