package org.smartsports.badminton.match.infrastructure.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * JWT相关配置
 * 功能：
 * 1. 加载RSA密钥
 * 2. 配置JWT编解码器
 */
@Configuration
public class JwtConfig {

    /**
     * 创建JWT解码器
     */
    @Bean
    JwtDecoder jwtDecoder(RsaKeyProperties resaKeyProperties) {
        return NimbusJwtDecoder.withPublicKey(resaKeyProperties.getPublicKey()).build();
    }

    /**
     * 创建JWT编码器
     */
    @Bean
    JwtEncoder jwtEncoder(RsaKeyProperties resaKeyProperties) {
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(
                new JWKSet(new RSAKey.Builder(resaKeyProperties.getPublicKey())
                        .privateKey(resaKeyProperties.getPrivateKey())
                        .build())
        );
        return new NimbusJwtEncoder(jwkSource);
    }
}