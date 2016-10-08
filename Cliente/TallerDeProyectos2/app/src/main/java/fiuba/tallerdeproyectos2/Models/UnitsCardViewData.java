package fiuba.tallerdeproyectos2.Models;

public class UnitsCardViewData {

    private String unitTitle;
    private String unitDescription;
    private String unitId;

    public UnitsCardViewData(String unitTitle, String unitDescription, String unitId){
        this.unitTitle = unitTitle;
        this.unitDescription = unitDescription;
        this.unitId = unitId;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}
