package com.proje.blokzincirbagisplatformu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Value("${blockchain.rpc-url:https://sepolia.infura.io/v3/YOUR_INFURA_KEY}")
    private String rpcUrl;

    @Bean
    public Web3j web3j() {
        // Ethereum Sepolia test ağ bağlantısını HTTP üzerinden başlatıyoruz
        return Web3j.build(new HttpService(rpcUrl));
    }
}