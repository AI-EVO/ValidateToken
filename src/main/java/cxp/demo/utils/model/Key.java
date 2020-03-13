package cxp.demo.utils.model;
import java.util.List;

public class Key {

    private String kty;
    private String use;
    private String kid;
    private String x5t;
    private String n;
    private String e;
    private List<String> x5c;
    private String issuer;
    public void setKty(String kty) {
        this.kty = kty;
    }
    public String getKty() {
        return kty;
    }

    public void setUse(String use) {
        this.use = use;
    }
    public String getUse() {
        return use;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }
    public String getKid() {
        return kid;
    }

    public void setX5t(String x5t) {
        this.x5t = x5t;
    }
    public String getX5t() {
        return x5t;
    }

    public void setN(String n) {
        this.n = n;
    }
    public String getN() {
        return n;
    }

    public void setE(String e) {
        this.e = e;
    }
    public String getE() {
        return e;
    }

    public void setX5c(List<String> x5c) {
        this.x5c = x5c;
    }
    public List<String> getX5c() {
        return x5c;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public String getIssuer() {
        return issuer;
    }

}