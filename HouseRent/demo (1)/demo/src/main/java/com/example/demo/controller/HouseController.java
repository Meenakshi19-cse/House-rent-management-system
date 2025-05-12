package com.example.demo.controller;

import com.example.demo.model.House;
import com.example.demo.model.Owner;
import com.example.demo.repository.HouseRepository;
import com.example.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/houses")
@CrossOrigin(origins = "*") // Allow frontend to talk to backend
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    // Get all houses
    @GetMapping
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    // Add a new house
    @PostMapping
    public ResponseEntity<?> addHouse(@RequestBody Map<String, Object> houseMap) {
        try {
            House house = new House();
            house.setTitle((String) houseMap.get("title"));
            house.setAddress((String) houseMap.get("address"));
            Object roomsObj = houseMap.get("rooms");
            if (roomsObj instanceof Integer) {
                house.setRooms((Integer) roomsObj);
            } else if (roomsObj instanceof String) {
                house.setRooms(Integer.parseInt((String) roomsObj));
            } else {
                return ResponseEntity.status(400).body("Invalid rooms value");
            }
            Object rentObj = houseMap.get("rent");
            if (rentObj instanceof Integer) {
                house.setRent(((Integer) rentObj).doubleValue());
            } else if (rentObj instanceof Double) {
                house.setRent((Double) rentObj);
            } else if (rentObj instanceof String) {
                house.setRent(Double.parseDouble((String) rentObj));
            } else {
                return ResponseEntity.status(400).body("Invalid rent value");
            }
            house.setLocationDescription((String) houseMap.get("locationDescription"));
            house.setNearbyAttractions((String) houseMap.get("nearbyAttractions"));
            Object nearCityObj = houseMap.get("nearCity");
            if (nearCityObj instanceof Boolean) {
                house.setNearCity((Boolean) nearCityObj);
            } else if (nearCityObj instanceof String) {
                house.setNearCity(Boolean.parseBoolean((String) nearCityObj));
            } else {
                house.setNearCity(false);
            }
            // parkingAvailable is not sent from frontend, default to false
            house.setParkingAvailable(false);

            // Handle imageUrl with double encoding and proxy prefix
            String imageUrl = (String) houseMap.get("imageUrl");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    java.net.URI uri = new java.net.URI(imageUrl);
                    if (!uri.isAbsolute()) {
                        return ResponseEntity.status(400).body("Image URL must be absolute");
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(400).body("Invalid image URL");
                }
                String encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString());
                house.setImageUrl("http://localhost:8080/api/image-proxy?url=" + encodedUrl);
            } else {
                house.setImageUrl(null);
            }

            Integer ownerIdInt = null;
            Object ownerIdObj = houseMap.get("ownerId");
            if (ownerIdObj instanceof Integer) {
                ownerIdInt = (Integer) ownerIdObj;
            } else if (ownerIdObj instanceof String) {
                try {
                    ownerIdInt = Integer.parseInt((String) ownerIdObj);
                } catch (NumberFormatException nfe) {
                    return ResponseEntity.status(400).body("Invalid ownerId value");
                }
            } else {
                return ResponseEntity.status(400).body("Owner ID is required");
            }
            if (ownerIdInt != null) {
                Optional<Owner> ownerOpt = ownerRepository.findById(ownerIdInt.longValue());
                if (ownerOpt.isPresent()) {
                    house.setOwner(ownerOpt.get());
                } else {
                    return ResponseEntity.status(400).body("Owner not found with id: " + ownerIdInt);
                }
            }

            House savedHouse = houseRepository.save(house);
            return ResponseEntity.ok(savedHouse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving house: " + e.getMessage());
        }
    }

    // Delete a house by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long id) {
        Optional<House> houseOptional = houseRepository.findById(id);

        if (houseOptional.isPresent()) {
            houseRepository.deleteById(id);
            return ResponseEntity.ok("House deleted successfully");
        } else {
            return ResponseEntity.status(404).body("House not found");
        }
    }

    // Get house by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable Long id) {
        Optional<House> houseOptional = houseRepository.findById(id);
        if (houseOptional.isPresent()) {
            return ResponseEntity.ok(houseOptional.get());
        } else {
            return ResponseEntity.status(404).body("House not found");
        }
    }

    // Update house by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody Map<String, Object> houseMap) {
        Optional<House> houseOptional = houseRepository.findById(id);
        if (!houseOptional.isPresent()) {
            return ResponseEntity.status(404).body("House not found");
        }
        House house = houseOptional.get();
        try {
            if (houseMap.containsKey("title")) {
                house.setTitle((String) houseMap.get("title"));
            }
            if (houseMap.containsKey("address")) {
                house.setAddress((String) houseMap.get("address"));
            }
            if (houseMap.containsKey("rooms")) {
                house.setRooms((Integer) houseMap.get("rooms"));
            }
            if (houseMap.containsKey("rent")) {
                Object rentObj = houseMap.get("rent");
                if (rentObj instanceof Integer) {
                    house.setRent(((Integer) rentObj).doubleValue());
                } else if (rentObj instanceof Double) {
                    house.setRent((Double) rentObj);
                }
            }
            if (houseMap.containsKey("locationDescription")) {
                house.setLocationDescription((String) houseMap.get("locationDescription"));
            }
            if (houseMap.containsKey("nearbyAttractions")) {
                house.setNearbyAttractions((String) houseMap.get("nearbyAttractions"));
            }
            if (houseMap.containsKey("nearCity")) {
                Object nearCityObj = houseMap.get("nearCity");
                if (nearCityObj instanceof Boolean) {
                    house.setNearCity((Boolean) nearCityObj);
                } else if (nearCityObj instanceof String) {
                    house.setNearCity(Boolean.parseBoolean((String) nearCityObj));
                }
            }
            // parkingAvailable is not sent from frontend, keep existing value

            if (houseMap.containsKey("imageUrl")) {
                String imageUrl = (String) houseMap.get("imageUrl");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    String encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString());
                    String doubleEncodedUrl = URLEncoder.encode(encodedUrl, StandardCharsets.UTF_8.toString());
                    house.setImageUrl("http://localhost:8080/api/image-proxy?url=" + doubleEncodedUrl);
                } else {
                    house.setImageUrl(null);
                }
            }

            if (houseMap.containsKey("ownerId")) {
                Integer ownerIdInt = null;
                Object ownerIdObj = houseMap.get("ownerId");
                if (ownerIdObj instanceof Integer) {
                    ownerIdInt = (Integer) ownerIdObj;
                } else if (ownerIdObj instanceof String) {
                    ownerIdInt = Integer.parseInt((String) ownerIdObj);
                }
                if (ownerIdInt != null) {
                    Optional<Owner> ownerOpt = ownerRepository.findById(ownerIdInt.longValue());
                    if (ownerOpt.isPresent()) {
                        house.setOwner(ownerOpt.get());
                    } else {
                        return ResponseEntity.status(400).body("Owner not found with id: " + ownerIdInt);
                    }
                }
            }

            House updatedHouse = houseRepository.save(house);
            return ResponseEntity.ok(updatedHouse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating house: " + e.getMessage());
        }
    }
}
