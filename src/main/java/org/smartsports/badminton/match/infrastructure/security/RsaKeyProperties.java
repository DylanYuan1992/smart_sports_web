package org.smartsports.badminton.match.infrastructure.security;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 从application.yml加载RSA公钥
 */
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class RsaKeyProperties implements InitializingBean {

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA")
                .generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }
}
