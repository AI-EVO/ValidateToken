package cxp.demo.utils;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class RsaUtils {
    public static RSAPublicKey getPublicKeyFromX5c(String x5c) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(x5c);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(keyBytes));
        return (RSAPublicKey) cer.getPublicKey();
    }

    public static PublicKey getPublicKeyFromEncodedModulusAndExponent(String encodedModulus, String encodedExponent) throws Exception {
        byte[] modulus = Base64.getUrlDecoder().decode(encodedModulus);
        byte[] exponent = Base64.getUrlDecoder().decode(encodedExponent);

        StringBuilder sb = new StringBuilder();
        for (byte b : modulus) {
            sb.append(String.format("%02x", b));
        }
        String sm = sb.toString();

        sb = new StringBuilder();
        for (byte b : exponent) {
            sb.append(String.format("%02x", b));
        }
        String se = sb.toString();

        BigInteger bm = new BigInteger(sm,16);
        BigInteger be = new BigInteger(se,16);

        KeyFactory rsa = KeyFactory.getInstance("RSA");
        PublicKey publicKey = rsa.generatePublic(new RSAPublicKeySpec(bm, be));

        return publicKey;
    }
}
