package com.cards.bbeitman.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card implements Serializable {
    private Integer elementId;
    private Integer raceId;
    private List<Integer> racePowers;
    private Integer classId;
    private List<Integer> classPowers;
    private Map<Integer, ArrayList<Integer>> raceCardMap;
    private Map<Integer, ArrayList<Integer>> classCardMap;
    private Map<Integer, Integer> powMap;

    // Static race and class position maps
    static HashMap<Integer, ArrayList<Integer>> racePosMap = buildRacePosMap();
    static HashMap<Integer, ArrayList<Integer>> classPosMap = buildClassPosMap();


    public Card(Integer elementId, Integer raceId, List<Integer> racePowers) {
        this.elementId = elementId;
        this.raceId = raceId;
        this.racePowers = racePowers;
        this.powMap = new HashMap<Integer, Integer>();
        // Build racePosMap, map of all possible race positions, with positions rotated at each position
        raceCardMap = new HashMap<Integer, ArrayList<Integer>>();
        for (Integer pos : racePosMap.get(raceId)) {
            // Rotate power to next in list
            racePowers.add(racePowers.remove(0));
            ArrayList<Integer> powersCopy = new ArrayList<>(racePowers);
            raceCardMap.put(pos, powersCopy);
        }
    }

    public Card(Integer elementId, Integer raceId, Integer classId, List<Integer> racePowers, List<Integer> classPowers) {
        this.elementId = elementId;
        this.raceId = raceId;
        this.racePowers = racePowers;
        this.classId = classId;
        this.classPowers = classPowers;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public List<Integer> getRacePowers() {
        return racePowers;
    }

    public void setRacePowers(List<Integer> racePowers) {
        this.racePowers = racePowers;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public List<Integer> getClassPowers() {
        return classPowers;
    }

    public void setClassPowers(List<Integer> classPowers) {
        this.classPowers = classPowers;
    }

    public Map<Integer, ArrayList<Integer>> getRaceCardMap() {
        return raceCardMap;
    }

    public void setRaceCardMap(Map<Integer, ArrayList<Integer>> raceCardMap) {
        this.raceCardMap = raceCardMap;
    }

    public Map<Integer, ArrayList<Integer>> getClassCardMap() {
        // Build classPosMap, map of all possible class positions, with positions rotated at each position
        classCardMap = new HashMap<Integer, ArrayList<Integer>>();
        if (classPowers.size() > 0) {
            for (Integer pos : classPosMap.get(classId)) {
                // Filter out any pos already in the powMap
                if (!powMap.keySet().contains(pos)) {
                    // Rotate power to next in list
                    classPowers.add(classPowers.remove(0));
                    ArrayList<Integer> powersCopy = new ArrayList<>(classPowers);
                    classCardMap.put(pos, powersCopy);
                }
            }
        }
        return classCardMap;
    }

    public void setClassCardMap(Map<Integer, ArrayList<Integer>> classCardMap) {
        this.classCardMap = classCardMap;
    }

    public Map<Integer, Integer> getPowMap() {
        return powMap;
    }

    public void setPowMap(Map<Integer, Integer> powMap) {
        this.powMap = powMap;
    }

    public List<Integer> getRacePositions() {
        return racePosMap.get(raceId);
    }

    public List<Integer> getClassPositions() {
        if (classId != null) {
            return classPosMap.get(classId);
        }
        return null;
    }

    static private HashMap<Integer, ArrayList<Integer>> buildRacePosMap() {
        HashMap<Integer, ArrayList<Integer>> racePosMap = new HashMap<>();
        racePosMap.put(1, new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7)));
        racePosMap.put(2, new ArrayList<Integer>(Arrays.asList(2, 3, 4, 7)));
        racePosMap.put(3, new ArrayList<Integer>(Arrays.asList(1, 4, 5, 8)));
        racePosMap.put(4, new ArrayList<Integer>(Arrays.asList(2, 4, 6, 8)));
        racePosMap.put(5, new ArrayList<Integer>(Arrays.asList(2, 3, 6, 7)));
        racePosMap.put(6, new ArrayList<Integer>(Arrays.asList(2, 5, 6, 7)));
        racePosMap.put(7, new ArrayList<Integer>(Arrays.asList(3, 6, 7, 8)));
        racePosMap.put(8, new ArrayList<Integer>(Arrays.asList(1, 4, 5, 6)));
        racePosMap.put(9, new ArrayList<Integer>(Arrays.asList(1, 2, 6, 7)));
        racePosMap.put(10, new ArrayList<Integer>(Arrays.asList(1, 2, 5, 8)));
        racePosMap.put(11, new ArrayList<Integer>(Arrays.asList(2, 3, 7, 8)));
        racePosMap.put(12, new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5)));
        racePosMap.put(13, new ArrayList<Integer>(Arrays.asList(1, 5, 6, 8)));
        racePosMap.put(14, new ArrayList<Integer>(Arrays.asList(2, 3, 5, 6)));
        racePosMap.put(15, new ArrayList<Integer>(Arrays.asList(1, 2, 5, 6)));
        racePosMap.put(16, new ArrayList<Integer>(Arrays.asList(3, 4, 5, 8)));
        racePosMap.put(17, new ArrayList<Integer>(Arrays.asList(1, 4, 7, 8)));
        racePosMap.put(18, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 6)));
        racePosMap.put(19, new ArrayList<Integer>(Arrays.asList(2, 3, 6, 8)));
        racePosMap.put(20, new ArrayList<Integer>(Arrays.asList(3, 4, 6, 7)));
        return racePosMap;
    }

    static private HashMap<Integer, ArrayList<Integer>> buildClassPosMap() {
        HashMap<Integer, ArrayList<Integer>> classPosMap = new HashMap<>();
        classPosMap.put(1, new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
        classPosMap.put(2, new ArrayList<Integer>(Arrays.asList(1, 3, 6)));
        classPosMap.put(3, new ArrayList<Integer>(Arrays.asList(2, 5, 7)));
        classPosMap.put(4, new ArrayList<Integer>(Arrays.asList(1, 4, 7)));
        classPosMap.put(5, new ArrayList<Integer>(Arrays.asList(3, 5, 8)));
        classPosMap.put(6, new ArrayList<Integer>(Arrays.asList(1, 3, 7)));
        classPosMap.put(7, new ArrayList<Integer>(Arrays.asList(3, 5, 7)));
        classPosMap.put(8, new ArrayList<Integer>(Arrays.asList(1, 3, 5)));
        classPosMap.put(9, new ArrayList<Integer>(Arrays.asList(1, 5, 7)));
        classPosMap.put(10, new ArrayList<Integer>(Arrays.asList(5, 6, 7)));
        classPosMap.put(11, new ArrayList<Integer>(Arrays.asList(1, 7, 8)));
        classPosMap.put(12, new ArrayList<Integer>(Arrays.asList(3, 4, 5)));
        return classPosMap;
    }
}
