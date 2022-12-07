package com.example.gremlin.gremlinthinkerpop.controller;

import com.example.gremlin.gremlinthinkerpop.model.Edges;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.*;
import org.apache.tinkerpop.gremlin.util.Gremlin;

import java.util.ArrayList;
//import com.example.gremlin.gremlinthinkerpop.model.Vertex;
import com.example.gremlin.gremlinthinkerpop.model.Airport;

@RequestMapping("/airports")
@RestController
public class AirportController {

    @Autowired
    GraphTraversalSource g;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value="/deletevertex/{vertex}")
    public void deleteVertex(@PathVariable Long vertex){
       g.V(vertex).drop().iterate();
       // g.V().valueMap(true).toList().stream().flatMap().filter(e -> e.)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value="/addairportdetails")
    public String AddEdge(@RequestBody  ArrayList<Edges>edgeList){
        for (Edges edge:
                edgeList) {
            addElements(edge.getOrigin(),edge.getLabel(),edge.getDestination());
        }

        return g.E().valueMap(true).toList().toString();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value="/details")
    public String getAirportDetails()
    {
        return   g.V().valueMap(true).toList().toString();

    }

    public boolean addElements(String name1, String label, String name2)
    {
        if ( g==null)
        {
            return false;
        }

        // Create a node for 'name1' unless it exists already
        Vertex v1 =
                g.V().has("code",name1).fold().
                        coalesce(__.unfold(),__.addV("airport").property("code",name1)).next();

        // Create a node for 'name2' unless it exists already
        Vertex v2 =
                g.V().has("code",name2).fold().
                        coalesce(__.unfold(),__.addV("airport").property("code",name2)).next();

        // Create an edge between 'name1' and 'name2' unless it exists already
        g.V().has("code",name1).out(label).has("code",name2).fold().
                coalesce(__.unfold(),
                        __.addE(label).from(__.V(v1)).to(__.V(v2))).iterate();

        return true;
    }

}
