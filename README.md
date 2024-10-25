# SmartCabs

Assumptions made:
  + Need to represent cab movement in real-time, so there is a notion of map/graph.
  + Considered 20 nodes and edges among them to represent a map. Nodes represents building/intersections and edges represent roads.
  + To mimic cab movement, every 7 seconds(arbitrary number) cab will move inbetween nodes.
  + User can request to book a cab from any of the node and the nearest cab should be allocated to the user.
