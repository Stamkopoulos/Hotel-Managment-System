package com.hotel;

import java.time.LocalDate;

public class HotelApp {

    public static void main(String[] args) {

        HotelManager manager = new HotelManager();
        LocalDate today = LocalDate.now();

        // Add Rooms
        System.out.println("=== Adding Rooms ===");
        manager.addRoom(new Room("101",   80.00,  RoomType.SINGLE));
        manager.addRoom(new Room("102",   80.00,  RoomType.SINGLE));
        manager.addRoom(new Room("201",  130.00,  RoomType.DOUBLE));
        manager.addRoom(new Room("301",   250.00,  RoomType.SUITE));
        manager.addRoom(new Room("401",  200.00,  RoomType.DOUBLE));

        // Register Customers
        System.out.println("\n=== Registering Customers ===");
        Customer alice = new Customer("Alice Smith",  "alice@mail.com", "6941000001");
        Customer bob   = new Customer("Bob Johnson",  "bob@mail.com",   "6941000002");
        Customer carol = new Customer("Carol Williams","carol@mail.com", "6941000003");
        manager.createCustomer(alice);
        manager.createCustomer(bob);
        manager.createCustomer(carol);

        // Create Bookings
        System.out.println("\n=== Creating Bookings ===");
        Booking b1 = manager.createBooking(alice, "201", today,             today.plusDays(4));
        Booking b2 = manager.createBooking(bob,   "301", today.plusDays(1), today.plusDays(6));
        Booking b3 = manager.createBooking(carol, "101", today,             today.plusDays(3));

        // All Available Rooms
        System.out.println("\n=== All Available Rooms ===");
        for (Room r : manager.getAllAvailableRooms()) {
            System.out.println("  " + r);
        }

        // Check Availability
        System.out.println("\n=== Availability Check ===");
        System.out.println("Room 201 (today -> +4): " + manager.checkAvailability("201", today, today.plusDays(4)));
        System.out.println("Room 102 (today -> +4): " + manager.checkAvailability("102", today, today.plusDays(4)));

        System.out.println("\nAvailable rooms (today -> +4):");
        for (Room r : manager.getAvailableRooms(today, today.plusDays(4))) {
            System.out.println("  " + r);
        }

        // Cancel a Booking
        System.out.println("\n=== Cancel Bob's Booking ===");
        manager.cancelBooking(b2.getBookingId());

        // Set Booking Status
        System.out.println("\n=== Complete Carol's Booking ===");
        manager.setBookingStatus(b3.getBookingId(), BookingStatus.COMPLETED);

        // Find by Customer
        System.out.println("\n=== Alice's Bookings ===");
        for (Booking b : manager.findBookingsByCustomer(alice.getCustomerId())) {
            System.out.println("  " + b);
        }

        // Find by Period
        System.out.println("\n=== Bookings (today -> +6) ===");
        for (Booking b : manager.findBookingsByPeriod(today, today.plusDays(6))) {
            System.out.println("  " + b);
        }

        // Find by ID
        System.out.println("\n=== Find Booking by ID ===");
        Booking found = manager.findBookingById(b1.getBookingId());
        System.out.println("  " + found);

        // Summary
        manager.printSummary();

        // Double Booking Guard
        System.out.println("=== Double Booking Guard ===");
        try {
            manager.createBooking(bob, "201", today.plusDays(1), today.plusDays(3));
        } catch (IllegalStateException e) {
            System.out.println("Blocked: " + e.getMessage());
        }
    }
}