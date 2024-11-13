package ru.cbr.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTRange {
    @JsonProperty("FromY")
    public int fromY;
    @JsonProperty("ToY")
    public int toY;
}
