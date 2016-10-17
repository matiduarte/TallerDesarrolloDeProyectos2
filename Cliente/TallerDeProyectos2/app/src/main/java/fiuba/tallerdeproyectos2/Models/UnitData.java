package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class UnitData {

    @SerializedName("unity")
    private String unitData;
    @SerializedName("subtitles")
    private String subtitles;

    public UnitData(String unitData, String subtitles) {
        this.unitData = unitData;
        this.subtitles = subtitles;
    }

    public String getUnitData() {
        return unitData;
    }

    public void setUnitData(String unitData) {
        this.unitData = unitData;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }
}


