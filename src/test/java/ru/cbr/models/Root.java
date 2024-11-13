package ru.cbr.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    @JsonProperty("RawData")
    public ArrayList<RawDatum> rawData;
    public ArrayList<HeaderDatum> headerData;
    public ArrayList<Unit> units;
    @JsonProperty("DTRange")
    public ArrayList<DTRange> dTRange;
    @JsonProperty("SType")
    public ArrayList<SType> sType;
}
