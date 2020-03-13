package cxp.demo.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import cxp.demo.utils.model.Key;

import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;

public class TokenUtils {

    private String TokenHeader = "";
    private String TokenPayload = "";
    private String TokenSignature = "";

    public String getHeader() {
        return TokenHeader;
    }

    public String getPayload() {
        return TokenPayload;
    }

    public String getSignature() {
        return TokenSignature;
    }


    private TokenUtils() {
    }

    public static TokenUtils parse(String token){
        TokenUtils tokenUtils = new TokenUtils();
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new RuntimeException("Wrong token");
        } else {
            tokenUtils.TokenHeader = parts[0];
            tokenUtils.TokenPayload = parts[1];
            tokenUtils.TokenSignature = parts[2];
            return tokenUtils;
        }
    }

    public JsonObject getJsonHeader() {
        String header = new String(Base64.getUrlDecoder().decode(TokenHeader));
        return new Gson().fromJson(header, JsonObject.class);
    }

    public JsonObject getJsonPayload() {
        String payload = new String(Base64.getUrlDecoder().decode(TokenPayload));
        return new Gson().fromJson(payload, JsonObject.class);
    }

    public boolean validateToken()throws Exception{
        String alg = getJsonHeader().get("alg").getAsString();
        if(!alg.equals("RS256")) throw new RuntimeException("Currently this tool can only validate token signed with RS256");
        String kid = getJsonHeader().get("kid").getAsString();
        String tid = getJsonPayload().get("tid").getAsString();
        List<Key> keys = AadUtils.getKeys(tid).getKeys();
        for (Key key : keys ) {
            if(key.getKid().equals(kid)){
                RSAPublicKey publicKey = RsaUtils.getPublicKeyFromX5C((String) key.getX5c().toArray()[0]);
                Signature rs256 = Signature.getInstance("SHA256withRSA");
                rs256.initVerify(publicKey);
                rs256.update(TokenHeader.getBytes());
                rs256.update((byte)'.');
                rs256.update(TokenPayload.getBytes());
                return rs256.verify(Base64.getUrlDecoder().decode(TokenSignature));
            }
        }
        return false;
    }
}
