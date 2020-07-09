package me.superischroma.aegis.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AegisServiceHandler
{
    @Getter
    private List<AegisService> services;

    public AegisServiceHandler()
    {
        services = new ArrayList<>();
    }

    public void add(AegisService service)
    {
        services.add(service);
        service.start();
    }

    public int getServiceAmount()
    {
        return services.size();
    }
}