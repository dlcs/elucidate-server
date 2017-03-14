package com.digirati.elucidate.infrastructure.generator.impl;

import java.util.UUID;

import com.digirati.elucidate.infrastructure.generator.IDGenerator;

public class UUIDIDGeneratorImpl implements IDGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
