package fiuba.tallerdeproyectos2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mati on 10/11/16 on 00:45.
 */

public class Certificate {

    @SerializedName("certifications")
    private String certifications;

    public Certificate(String certifications) {
        this.certifications = certifications;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
}
