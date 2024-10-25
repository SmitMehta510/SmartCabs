package org.example.smartcabs.service;

import org.example.smartcabs.model.*;
import org.example.smartcabs.repository.CabRepository;
import org.example.smartcabs.repository.LocationRepository;
import org.example.smartcabs.utilities.AppLogger;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class LocationService {

    static Location[][] nodeList = new Location[5][6];
    static List<Cab> cabList = new ArrayList<>();
    static Graph<Location, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    static Random rand = new Random();


    @Autowired
    CabRepository cabRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CarService carService;

    Logger logger = AppLogger.getLogger();

    @Scheduled(fixedRate = 7000)
    @Async
    public void refreshPricingParameters() {

        System.out.println("Refreshing cab location at time :" + LocalDateTime.now());
        moveCab();
    }


    public void populateGraph(){

        Location node1 = new Location(1, 1, 1);
        nodeList[1][1] = node1;

        Location node2 = new Location(2, 1, 2);
        nodeList[1][2] = node2;

        Location node3 = new Location(3, 1, 3);
        nodeList[1][3] = node3;

        Location node4 = new Location(4, 1, 4);
        nodeList[1][4] = node4;

        Location node5 = new Location(5, 1, 5);
        nodeList[1][5] = node5;

        Location node6 = new Location(6, 2, 1);
        nodeList[2][1] = node6;

        Location node7 = new Location(7, 2, 2);
        nodeList[2][2] = node7;

        Location node8 = new Location(8, 2, 3);
        nodeList[2][3] = node8;

        Location node9 = new Location(9, 2, 4);
        nodeList[2][4] = node9;

        Location node10 = new Location(10, 2, 5);
        nodeList[2][5] = node10;

        Location node11 = new Location(11, 3, 1);
        nodeList[3][1] = node11;

        Location node12 = new Location(12, 3, 2);
        nodeList[3][2] = node12;

        Location node13 = new Location(13, 3, 3);
        nodeList[3][3] = node13;

        Location node14 = new Location(14, 3, 4);
        nodeList[3][4] = node14;

        Location node15 = new Location(15, 3, 5);
        nodeList[3][5] = node15;

        Location node16 = new Location(16, 4, 1);
        nodeList[4][1] = node16;

        Location node17 = new Location(17, 4, 2);
        nodeList[4][2] = node17;

        Location node18 = new Location(18, 4, 3);
        nodeList[4][3] = node18;

        Location node19 = new Location(19, 4, 4);
        nodeList[4][4] = node19;

        Location node20 = new Location(20, 4, 5);
        nodeList[4][5] = node20;


        graph.addVertex(node1);
        graph.addVertex(node2);
        graph.addVertex(node3);
        graph.addVertex(node4);
        graph.addVertex(node5);
        graph.addVertex(node6);
        graph.addVertex(node7);
        graph.addVertex(node8);
        graph.addVertex(node9);
        graph.addVertex(node10);
        graph.addVertex(node11);
        graph.addVertex(node12);
        graph.addVertex(node13);
        graph.addVertex(node14);
        graph.addVertex(node15);
        graph.addVertex(node16);
        graph.addVertex(node17);
        graph.addVertex(node18);
        graph.addVertex(node19);
        graph.addVertex(node20);

        graph.addEdge(node1,node2);
        graph.addEdge(node1,node6);
        graph.addEdge(node2,node3);
        graph.addEdge(node2,node7);
        graph.addEdge(node3,node4);
        graph.addEdge(node3,node8);
        graph.addEdge(node4,node5);
        graph.addEdge(node4,node9);
        graph.addEdge(node5,node10);
        graph.addEdge(node6,node7);
        graph.addEdge(node6,node11);
        graph.addEdge(node7,node8);
        graph.addEdge(node7,node12);
        graph.addEdge(node8,node9);
        graph.addEdge(node8,node13);
        graph.addEdge(node9,node10);
        graph.addEdge(node9,node14);
        graph.addEdge(node10,node15);
        graph.addEdge(node11,node12);
        graph.addEdge(node11,node16);
        graph.addEdge(node12,node13);
        graph.addEdge(node12,node17);
        graph.addEdge(node13,node14);
        graph.addEdge(node13,node18);
        graph.addEdge(node14,node15);
        graph.addEdge(node14,node19);
        graph.addEdge(node15,node20);
        graph.addEdge(node16,node17);
        graph.addEdge(node17,node18);
        graph.addEdge(node18,node19);
        graph.addEdge(node19,node20);

    }

    public void populateCab(){
//        carService.populateCar();
        List<Car> carList = carService.getCarList();

        Location car1Loc = findLocation(1,2);

        Cab cab1 = new Cab();
        cab1.setCabId(1L);
        cab1.setCar(carList.get(0));
        cab1.setCurrentLocation(car1Loc);
        cab1.setStatus(Status.IDLE);
        cab1.setActive(true);

        cabList.add(cab1);

        Location car2Loc = findLocation(2,5);

        Cab cab2 = new Cab();
        cab2.setCabId(2L);
        cab2.setCar(carList.get(1));
        cab2.setCurrentLocation(car2Loc);
        cab2.setStatus(Status.IDLE);
        cab2.setActive(true);

        cabList.add(cab2);

        Location car3Loc = findLocation(4,2);

        Cab cab3 = new Cab();
        cab3.setCabId(3L);
        cab3.setCar(carList.get(2));
        cab3.setCurrentLocation(car3Loc);
        cab3.setStatus(Status.IDLE);
        cab3.setActive(true);

        cabList.add(cab3);

        cabRepository.save(cab1);
        cabRepository.save(cab2);
        cabRepository.save(cab3);

    }


    public Location findLocation(int latitude, int longitude){
        return locationRepository.findLocationByLatitudeAndLongitude(latitude,longitude);


    }

    public Location findNode(int latitude, int longitude){
        if(latitude>= nodeList.length || longitude>= nodeList[0].length){
            System.err.println("Invalid indexes");
            return null;
        }
        return nodeList[latitude][longitude];
    }

    public List<Location> findShortestPath(Location source, Location destination){
        DijkstraShortestPath<Location,DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        return dijkstraShortestPath.getPath(source,destination).getVertexList();
    }

    public Cab findNearestCab(int latitude, int longitude){

        Location userLocation  = findNode(latitude,longitude);
        logger.info("Cab request received for pickup from Node{}", userLocation.getLocationId());
        Cab nearestCab = new Cab();
        int pathLength = Integer.MAX_VALUE;
        for (Cab cab : cabList) {
            if(cab.getStatus()!=Status.IN_TRANSIT) {
                Location cabLocation = findNode(
                        cab.getCurrentLocation().getLatitude(), cab.getCurrentLocation().getLongitude());
                System.out.println("cab found at location " + cabLocation.getLocationId());
                List<Location> shortestPath = findShortestPath(cabLocation, userLocation);
                int length = shortestPath.size();
                if (length < pathLength) {
                    nearestCab = cab;
                    pathLength = length;
                }
            }
        }

        System.out.println("Nearest cab is at : "+ nearestCab.getCurrentLocation().getLocationId());
        int id = Math.toIntExact(nearestCab.getCabId());
//        cabList.get(id-1).setStatus(Status.IN_TRANSIT);
        Cab bookedCab = cabList.get(id-1);
        bookedCab.setStatus(Status.IN_TRANSIT);
        cabRepository.save(bookedCab);
        logger.info("Cab Id: {} assigned", nearestCab.getCabId());
        return nearestCab;
    }

    public void moveCab(){
        for(Cab cab : cabList) {
            if (cab.getStatus() != Status.IN_TRANSIT) {
                Location cabLocation = findNode(
                        cab.getCurrentLocation().getLatitude(), cab.getCurrentLocation().getLongitude());

                Set<DefaultEdge> out = graph.edgesOf(cabLocation);
                List<DefaultEdge> nextLocation = new ArrayList<>(out);
                int rand = generateRandom(nextLocation.size());
                DefaultEdge next = nextLocation.get(rand);
                if (graph.getEdgeSource(next).getLocationId() == cab.getCurrentLocation().getLocationId()) {
                    Location nextPath = graph.getEdgeTarget(next);
                    cab.setCurrentLocation(findNode(nextPath.getLatitude(), nextPath.getLongitude()));
                } else {
                    Location nextPath = graph.getEdgeSource(next);
                    cab.setCurrentLocation(findNode(nextPath.getLatitude(), nextPath.getLongitude()));
                }
                System.out.println("cab at location " + cab.getCurrentLocation().getLocationId());
            }
        }
    }

    private int generateRandom(int max){
        return rand.nextInt(max) ;
    }

    public List<Cab> getCabLocations(){
        List<Cab> availableCab = new ArrayList<>();
        cabList.forEach(cab -> {
            if(cab.getStatus()==Status.IDLE){
                availableCab.add(cab);
            }
        });
        return availableCab;
    }


}
