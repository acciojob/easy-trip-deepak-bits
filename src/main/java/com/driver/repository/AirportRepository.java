package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDB;
    HashMap<Integer, Flight> flightDB;
    HashMap<Integer, Passenger> passengerDB;
    HashMap<Integer, Set<Integer>> flightPassengerDB; // key: flightID, values: set of passengerIDs
//    HashMap<Integer, Set<Integer>> passengerFlightDB; // key: passengerID, values: set of flightIDs

    public AirportRepository() {
        this.airportDB = new HashMap<>();
        this.flightDB = new HashMap<>();
        this.passengerDB = new HashMap<>();
        this.flightPassengerDB = new HashMap<>();
    }

    public void addAirport(Airport airport){
        String key = airport.getAirportName();
        if(key != null) airportDB.put(airport.getAirportName(), airport);
    }

    public String getLargestAirportName(){
        // Convert the HashMap values to a List and sort it using the compareTo method
        List<Airport> sortedAirports = new ArrayList<>(airportDB.values());
        Collections.sort(sortedAirports);

        // Get the largest airport (i.e., first element of the sorted list)
        Airport largestAirport = sortedAirports.get(0);
        return largestAirport.getAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double shortestTime = Double.MAX_VALUE;
        boolean isFound = false;
        for(Flight flight : flightDB.values()) {
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)) {
                isFound = true;
                shortestTime = Math.min(shortestTime, flight.getDuration());
            }
        }
        return isFound ? shortestTime : -1;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        int totalPeople = 0;
        for(Flight flight : flightDB.values()) {
            String fromCity = flight.getFromCity().toString();
            String toCity = flight.getToCity().toString();
            if(flight.getFlightDate().compareTo(date) == 0 && fromCity.equals(airportName) || toCity.equals(airportName)) {
                totalPeople += flightPassengerDB.get(flight.getFlightId()).size();
            }
        }
        return totalPeople;
    }

    public int calculateFlightFare(Integer flightId){
        Set<Integer> passengers = flightPassengerDB.get(flightId);
        int existingCount = passengers.size();

        return 3000 + existingCount * 50;
    }

    public String bookATicket(Integer flightId,Integer passengerId){
        if(!flightDB.containsKey(flightId)) {
            return "FAILURE";
        } else {
            Set<Integer> passengers = flightPassengerDB.get(flightId);
            int maxCapacity = flightDB.get(flightId).getMaxCapacity();

            if(passengers.size() >= maxCapacity || passengers.contains(passengerId)) {
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightPassengerDB.put(flightId, passengers);
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId,Integer passengerId){
        Set<Integer> passengers = flightPassengerDB.get(flightId);
        if(!flightDB.containsKey(flightId)) {
            return "FAILURE";
        } else {
            if(!passengers.contains(passengerId)) return "FAILURE";
            else {
                passengers.remove(passengerId);
                flightPassengerDB.put(flightId, passengers);
                return "SUCCESS";
            }
        }
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int countBookings = 0;
        for(Set<Integer> passengers : flightPassengerDB.values()) {
            if(passengers.contains(passengerId)) countBookings++;
        }
        return countBookings;
    }

    public void addFlight(Flight flight){
        int flightID = flight.getFlightId();
        flightDB.put(flightID, flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        if(!flightDB.containsKey(flightId)) return null;

        City fromCity = flightDB.get(flightId).getFromCity();
        for(Airport airport : airportDB.values()) {
            if (airport.getCity().equals(fromCity)) {
                return fromCity.toString();
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        Set<Integer> passengers = flightPassengerDB.get(flightId);
        int totalRevenue = 0;
        for(int i = 0; i < passengers.size(); i++) {
            totalRevenue += (3000 + (i*50));
        }
        return totalRevenue;
    }

    public void addPassenger(Passenger passenger){
        int passengerID = passenger.getPassengerId();
        passengerDB.put(passengerID, passenger);
    }
}

//TestCases.testBookAFlight:114 » NullPointer
//        Error:    TestCases.testBookAFlightWhenPassengerAlreadyThere:128 » NullPointer
//        Error:    TestCases.testCalculateFlightFare:100 » NullPointer
//        Error:    TestCases.testCalculateRevenueOfAFlight:202 » NullPointer
//        Error:    TestCases.testCancelATicket:143 » NullPointer
//        Error:    TestCases.testCancelATicketWhenTicketDoesntExist:158 » NullPointer
//        Error:    TestCases.testCountOfBookingsDoneByPassengerAllCombined:222 » NullPointer
//        Error:    TestCases.testGetNumberOfPeopleOn:76 » NullPointer
