package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

public class UnitData {

    @SerializedName("unity")
    private String unitData;

    public UnitData(String unitData) {
        this.unitData = unitData;
    }

    public String getUnitData() {
        return unitData;
    }

    public void setUnitData(String unitData) {
        this.unitData = unitData;
    }
}


