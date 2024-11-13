package ru.cbr.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    public int sType;
    public String dsName;
    @JsonProperty("PublName")
    public String publName;
    @JsonProperty("RawData")
    public ArrayList<RawDatum> rawData;
}
