package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.driver.repository.AirportRepository;

import java.util.Date;

@Service
public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName(){
        return airportRepository.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities( City fromCity, City toCity){
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }

    public int getNumberOfPeopleOn( Date date, String airportName){
        return airportRepository.getNumberOfPeopleOn(date, airportName);
    }

    public int calculateFlightFare(Integer flightId){
        return airportRepository.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId,Integer passengerId){
        return airportRepository.bookATicket(flightId, passengerId);
    }

    public String cancelATicket(Integer flightId,Integer passengerId){
        return airportRepository.cancelATicket(flightId, passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public void addFlight(@RequestBody Flight flight){
        airportRepository.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public void addPassenger(@RequestBody Passenger passenger){
        airportRepository.addPassenger(passenger);
    }
}
