
package com.proje.blokzincirbagisplatformu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BagisSozlesmesiService {

    @Autowired
    private Web3j web3j;

    @Value("${blockchain.contract-address}")
    private String contractAddress;

    // Ethereum ağının ID değerini getirir
    public BigInteger agIdGetir() throws IOException {
        return web3j.ethChainId().send().getChainId();
    }

    // Sözleşmenin dağıtılıp dağıtılmadığını kontrol eder
    public boolean sozlesmeDagitilmisMi() throws IOException {

        EthGetCode response = web3j.ethGetCode(
                contractAddress,
                DefaultBlockParameterName.LATEST
        ).send();

        String kod = response.getCode();

        return kod != null
                && !kod.equals("0x")
                && !kod.equals("0x0");
    }

    // Akıllı sözleşmenin toplam ETH bakiyesini Wei olarak getirir
    public BigInteger toplamBakiyeGetir() throws IOException {

        Function function = new Function(
                "toplamBakiye",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Uint256>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);

        EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(
                        null,
                        contractAddress,
                        encodedFunction
                ),
                DefaultBlockParameterName.LATEST
        ).send();

        List<org.web3j.abi.datatypes.Type> sonuc =
                FunctionReturnDecoder.decode(
                        response.getValue(),
                        function.getOutputParameters()
                );

        if (sonuc.isEmpty()) {
            throw new IllegalStateException("Sözleşmeden bakiye bilgisi alınamadı.");
        }

        return (BigInteger) sonuc.get(0).getValue();
    }

    public String getContractAddress() {
        return contractAddress;
    }

    // Kampanyaya yapılan toplam bağış miktarını Wei olarak getirir
    public BigInteger kampanyaToplamBagisGetir(BigInteger kampanyaId)
            throws IOException {

        Function function = new Function(
                "kampanyaToplamlari",
                Arrays.asList(new Uint256(kampanyaId)),
                Arrays.asList(new TypeReference<Uint256>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);

        EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(
                        null,
                        contractAddress,
                        encodedFunction
                ),
                DefaultBlockParameterName.LATEST
        ).send();

        List<org.web3j.abi.datatypes.Type> sonuc =
                FunctionReturnDecoder.decode(
                        response.getValue(),
                        function.getOutputParameters()
                );

        if (sonuc.isEmpty()) {
            throw new IllegalStateException(
                    "Kampanya bağış bilgisi alınamadı."
            );
        }

        return (BigInteger) sonuc.get(0).getValue();
    }
    public org.web3j.protocol.core.methods.response.TransactionReceipt
    islemMakbuzuGetir(String transactionHash) throws java.io.IOException {

        var cevap = web3j.ethGetTransactionReceipt(transactionHash).send();

        return cevap.getTransactionReceipt().orElse(null);
    }

}
