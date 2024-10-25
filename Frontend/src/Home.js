import axios from 'axios';
import Graph from "react-graph-vis";
import React, { useState, useEffect } from "react";

const options = {
  layout: {
    hierarchical: false
  },
  edges: {
    color: "#000000",
    arrows: {
      to: { enabled: false } 
    }
  },
  nodes: {
    shape: 'circle',
    fixed: {
      x: true,
      y: true,
    },
  },
  physics: {
    enabled: false, 
  },
};



const fetchCabLocation = async () => {
  try {
    const response = await axios.get('http://localhost:8082/cab/update-location'); 
    return response.data; 
  } catch (error) {
    console.error("Error fetching cab coordinates:", error);
    return []; 
  }
};

const Home = () => {
  const [state, setState] = useState({
    graph: {
      nodes : [
        { id: 1, label: "Node 1", color: "#FFFFFF", x: 100, y: 100 },
        { id: 2, label: "Node 2", color: "#FFFFFF", x: 100, y: 200 },
        { id: 3, label: "Node 3", color: "#FFFFFF", x: 100, y: 300 },
        { id: 4, label: "Node 4", color: "#FFFFFF", x: 100, y: 400 },
        { id: 5, label: "Node 5", color: "#FFFFFF", x: 100, y: 500 },
        { id: 6, label: "Node 6", color: "#FFFFFF", x: 200, y: 100 },
        { id: 7, label: "Node 7", color: "#FFFFFF", x: 200, y: 200 },
        { id: 8, label: "Node 8", color: "#FFFFFF", x: 200, y: 300 },
        { id: 9, label: "Node 9", color: "#FFFFFF", x: 200, y: 400 },
        { id: 10, label: "Node 10", color: "#FFFFFF", x: 200, y: 500 },
        { id: 11, label: "Node 11", color: "#FFFFFF", x: 300, y: 100 },
        { id: 12, label: "Node 12", color: "#FFFFFF", x: 300, y: 200 },
        { id: 13, label: "Node 13", color: "#FFFFFF", x: 300, y: 300 },
        { id: 14, label: "Node 14", color: "#FFFFFF", x: 300, y: 400 },
        { id: 15, label: "Node 15", color: "#FFFFFF", x: 300, y: 500 },
        { id: 16, label: "Node 16", color: "#FFFFFF", x: 400, y: 100 },
        { id: 17, label: "Node 17", color: "#FFFFFF", x: 400, y: 200 },
        { id: 18, label: "Node 18", color: "#FFFFFF", x: 400, y: 300 },
        { id: 19, label: "Node 19", color: "#FFFFFF", x: 400, y: 400 },
        { id: 20, label: "Node 20", color: "#FFFFFF", x: 400, y: 500 },
      ],
      edges : [
        { from: 1, to: 2 },
        { from: 1, to: 6 },
        { from: 2, to: 3 },
        { from: 2, to: 7 },
        { from: 3, to: 4 },
        { from: 3, to: 8 },
        { from: 4, to: 5 },
        { from: 4, to: 9 },
        { from: 5, to: 10 },
        { from: 6, to: 7 },
        { from: 6, to: 11 },
        { from: 7, to: 8 },
        { from: 7, to: 12 },
        { from: 8, to: 9 },
        { from: 8, to: 13 },
        { from: 9, to: 10 },
        { from: 9, to: 14 },
        { from: 10, to: 15 },
        { from: 11, to: 12 },
        { from: 11, to: 16 },
        { from: 12, to: 13 },
        { from: 12, to: 17 },
        { from: 13, to: 14 },
        { from: 13, to: 18 },
        { from: 14, to: 15 },
        { from: 14, to: 19 },
        { from: 15, to: 20 },
        { from: 16, to: 17 },
        { from: 17, to: 18 },
        { from: 18, to: 19 },
        { from: 19, to: 20 },
      ]
    },
    events: {
      select: ({ nodes }) => {
        if (nodes.length > 0) {
          const selectedNodeId = nodes[0]; 
          const selectedNode = state.graph.nodes.find(node => node.id === selectedNodeId);
          const { x, y } = selectedNode; 
          
          
          let xValue = x;
          let yValue = y;
          sendCoordinates(xValue/100, yValue/100);
          
          console.log(`Selected node coordinates: x=${xValue}, y=${yValue}`);
        }
      }
    }
  });

  const [isCabBooked, setIsCabBooked] = useState(false);
  const [locationId, setLocationId] = useState(null);
  const { graph, events } = state;

  const sendCoordinates = async (x, y) => {
    try {
      const srcLocation = {
        latitude: x,
        longitude: y
      }

      const authToken = JSON.parse(localStorage.getItem("authToken"));
      const token = authToken ? authToken.accessToken : '';
      const userId = parseInt(authToken.userId);
      const payload = {
            userId: userId,
            srcLocation: srcLocation
        };

        
        const response = await axios.post(
            'http://localhost:8082/user/book-cab',payload,{
              headers: {
                  Authorization: `Bearer ${token}`,
                  "Content-Type": 'application/json',
              }
          }
        );
  
        // setLocationId(response.data);
        const locationId = response.data.cabCurrentLocation.locationId;
        setLocationId(locationId);
        if (response.data) { 
            setIsCabBooked(true);
        }
      console.log('API Response:', response.data);
    } catch (error) {
      console.error("Error sending coordinates:", error);
    }
  };

  const fetchCabCoordinates = async () => {
    const newCabCoordinates = await fetchCabLocation();

    setState(prevState => {
        const updatedNodes = prevState.graph.nodes.map(node => {
            const cab = newCabCoordinates.find(cab => 
                Math.abs(cab.currentLocation.latitude - node.x/100) === 0 && 
                Math.abs(cab.currentLocation.longitude - node.y/100) === 0 
            );

            if (cab) {
                return { 
                  ...node,
                  color: "#000000",
                  font: { color: "#FFFFFF" } 
                };
            }

            return { ...node, color: "#FFFFFF", font: { color: "#000000" }  }; 
        });

        return {
            ...prevState,
            graph: {
                ...prevState.graph,
                nodes: updatedNodes
            }
        };
    });
  };

  

  useEffect(() => {
    fetchCabCoordinates();
    const interval = setInterval(fetchCabCoordinates, 7000);
    return () => clearInterval(interval); 
  }, []);

  return (
    <div>
      <h1 style={{ textAlign: 'center' }}>Cab Location Graph</h1>
      <p style={{ textAlign: 'center' }}>This graph displays the current locations of cabs in the area. Select a node to book a cab.</p>
      {isCabBooked && <p style={{ textAlign: 'center', color: 'green' }}>Cab booked successfully! Nearest Cab is at Node{locationId}</p>}
      <Graph graph={graph} options={options} events={events} style={{ height: "1000px" }} />
    </div>
  );
};

export default Home;
