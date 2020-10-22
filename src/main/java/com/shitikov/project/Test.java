package com.shitikov.project;

import com.shitikov.project.model.builder.AddressBuilder;
import com.shitikov.project.model.builder.AddressTimeDataBuilder;
import com.shitikov.project.model.builder.CargoApplicationBuilder;
import com.shitikov.project.model.entity.Address;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.ApplicationType;
import com.shitikov.project.model.entity.application.CargoApplication;
import com.shitikov.project.model.exception.ServiceException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Test {
    public static void main(String[] args) throws ServiceException, GeneralSecurityException, IOException {
        String phone = "Егор hih";

        System.out.println(phone.matches("[\\p{L}\\s-]{1,50}"));

        CargoApplicationBuilder builder = new CargoApplicationBuilder();
        Address address = new AddressBuilder().buildCity("sd")
                .buildStreetHome("sdfsd")
                .buildAddress();
        AddressTimeData addressTimeData = new AddressTimeDataBuilder()
                .buildDepartureDate(255656L)
                .buildDepartureAddress(address)
                .buildDaysToComplete(4)
                .buildArrivalAddress(address)
                .buildAddressTimeData();

        CargoApplication application = builder.buildCargoVolume(23)
                .buildCargoVolume(434)
                .buildTitle("New app")
                .buildApplicationType(ApplicationType.CARGO)
                .buildDate(42555L)
                .buildAddressTimeData(addressTimeData)
                .buildDescription("sfsdfed")
                .buildApplication();

        StringBuilder builder1 = new StringBuilder("1234567");
        System.out.println(builder1.delete(builder1.lastIndexOf("6"), builder1.length()));

        System.out.println("11111df6".matches("\\d{4}\\p{Alpha}{2}[1-7]"));


    }
}