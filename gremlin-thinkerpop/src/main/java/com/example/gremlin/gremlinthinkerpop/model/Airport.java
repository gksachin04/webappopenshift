package com.example.gremlin.gremlinthinkerpop.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Airport {
    private ArrayList<Edges> edge;
    private ArrayList<Vertex> vertices;
}
