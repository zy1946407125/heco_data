package com.magic233.service;

import com.magic233.entity.Price;
import com.magic233.entity.Transaction;
import com.magic233.mapper.TransactionMapper;
import com.magic233.utils.HexadecimalConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: HecoDataImpl
 * Description:
 * date: 2021/7/29 17:52
 * author: zouyuan
 */
@Service
public class HecoData2Impl implements HecoData2 {
    private String Link = "https://http-mainnet-node.huobichain.com";
    private String Router = "0x8C8c6fBda1DD67281E36FB1869Fc5e9605E2B8ff";
    private String TVL = "0xc85474Cfae8eA933ab4785f480cC11e2cB1A4535";
    private String ETH = "5545153ccfca01fbd7dd11c0b23ba694d9509a6f";//火币-HT，币安-BNB

    @Autowired
    private TransactionMapper transactionMapper;

    Web3j web3j = Web3j.build(new HttpService(Link));
    HexadecimalConversion hexadecimalConversion = new HexadecimalConversion();

    @Override
    public BigInteger getBlockHeight(boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Transaction: web3j:  " + web3j);
        BigInteger blockMax = BigInteger.valueOf(0);
        Request<?, EthBlockNumber> ethBlockNumberRequest = web3j.ethBlockNumber();
        try {
            blockMax = ethBlockNumberRequest.send().getBlockNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Transaction: blockMax:  " + blockMax);
        return blockMax;
    }

    @Override
    public int getTransaction(BigInteger blockHeight, boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Transaction: web3j:  " + web3j);
        Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockHeight), true);
        EthBlock.Block block = null;
        try {
            block = ethBlockRequest.send().getBlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (block == null) {
            return 0;
        } else {
            List<EthBlock.TransactionResult> transactions = block.getTransactions();
            BigInteger timestamp = block.getTimestamp();
            for (int i = 0; i < transactions.size(); i++) {
                org.web3j.protocol.core.methods.response.Transaction t = null;
                try {
                    t = (org.web3j.protocol.core.methods.response.Transaction) transactions.get(i).get();
                    if (Router.toUpperCase().equals(t.getTo().toUpperCase())) {
                        this.analysisTransaction(t, timestamp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Transaction: " + t.getHash());
                    System.out.println("Transaction: 找t.getTo()出错");
                } finally {
                    continue;
                }
            }
            return 1;
        }
    }

    public void analysisTransaction(org.web3j.protocol.core.methods.response.Transaction t, BigInteger timestamp) {
        String input = t.getInput();
        Transaction transaction = new Transaction();
        transaction.setHash(t.getHash());
        transaction.setBlocknumber(Long.valueOf(String.valueOf(t.getBlockNumber())));
        transaction.setFromAddress(t.getFrom());
        transaction.setToAddress(t.getTo());
        transaction.setInput(t.getInput());
        transaction.setTime(Long.valueOf(String.valueOf(timestamp)));
        if (input.length() >= 10) {
            System.out.println("Transaction: " + input);
            String MethodID = input.substring(0, 10);
            System.out.println("Transaction: MethodID:" + MethodID);
            //情况1：
            if ("0x38ed1739".equals(MethodID) || "0x18cbafe5".equals(MethodID)) {
                String s = input.substring(10);
                String tokenAmount = s.substring(0, 64);
                System.out.println("Transaction: tokenAmount:" + tokenAmount);
                double tokenAmount10 = hexadecimalConversion.covert(tokenAmount);
                System.out.println("Transaction: tokenAmount10:" + tokenAmount10);
                String tokenAddress = s.substring(64 * 6, 64 * 7);
                System.out.println("Transaction: tokenAddress:" + tokenAddress);

                Price tokenPrice = this.getTokenPrice(tokenAddress, false);
                while (tokenPrice == null) {
                    try {
                        System.out.println("Transaction: 获取token价格出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tokenPrice = this.getTokenPrice(tokenAddress, true);
                }

                transaction.setPrice(tokenPrice.getPrice());
                transaction.setDecimals(tokenPrice.getDecimal());
                double value = (1.0 * (tokenAmount10 / Math.pow(10, 18)) * (tokenPrice.getPrice() / Math.pow(10, tokenPrice.getDecimal())));
                transaction.setValue(value);

                transaction.setTokenamount("0x" + tokenAmount);
                transaction.setTokenamount10(tokenAmount10);
                transaction.setTokenaddress("0x" + tokenAddress.substring(24));
                transaction.setMethodid(MethodID);

                transactionMapper.insert(transaction);
            } else if ("0x7ff36ab5".equals(MethodID)) {
                double tokenAmount10 = Double.parseDouble(String.valueOf(t.getValue()));
                System.out.println("Transaction: tokenAmount10:" + tokenAmount10);
                String tokenAddress = ETH;
                System.out.println("Transaction: tokenAddress:" + tokenAddress);

                Price tokenPrice = this.getTokenPrice(tokenAddress, false);
                while (tokenPrice == null) {
                    try {
                        System.out.println("Transaction: 获取token价格出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tokenPrice = this.getTokenPrice(tokenAddress, true);
                }
                transaction.setPrice(tokenPrice.getPrice());
                transaction.setDecimals(tokenPrice.getDecimal());
                double value = (1.0 * (tokenAmount10 / Math.pow(10, 18)) * (tokenPrice.getPrice() / Math.pow(10, tokenPrice.getDecimal())));
                transaction.setValue(value);

                transaction.setTokenamount10(tokenAmount10);
                transaction.setTokenaddress("0x" + tokenAddress);
                transaction.setMethodid(MethodID);

                transactionMapper.insert(transaction);
            } else if ("0x8803dbee".equals(MethodID)) {
                String s = input.substring(10);
                String tokenAmount = s.substring(0, 64);
                System.out.println("Transaction: tokenAmount:" + tokenAmount);
                double tokenAmount10 = hexadecimalConversion.covert(tokenAmount);
                System.out.println("Transaction: tokenAmount10:" + tokenAmount10);
                StringBuffer stringBuffer = new StringBuffer(s);
                StringBuffer reverse = stringBuffer.reverse();
                String substring = reverse.substring(0, 64);
                String tokenAddress = String.valueOf(new StringBuffer(substring).reverse());
                tokenAddress = tokenAddress.substring(24, 64);
                System.out.println("Transaction: tokenAddress:" + tokenAddress);

                Price tokenPrice = this.getTokenPrice(tokenAddress, false);
                while (tokenPrice == null) {
                    try {
                        System.out.println("Transaction: 获取token价格出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tokenPrice = this.getTokenPrice(tokenAddress, true);
                }
                transaction.setPrice(tokenPrice.getPrice());
                transaction.setDecimals(tokenPrice.getDecimal());
                double value = (1.0 * (tokenAmount10 / Math.pow(10, 18)) * (tokenPrice.getPrice() / Math.pow(10, tokenPrice.getDecimal())));
                transaction.setValue(value);

                transaction.setTokenamount("0x" + tokenAmount);
                transaction.setTokenamount10(tokenAmount10);
                transaction.setTokenaddress("0x" + tokenAddress);
                transaction.setMethodid(MethodID);

                transactionMapper.insert(transaction);
            } else if ("0xfb3bdb41".equals(MethodID)) {
                double tokenAmount10 = Double.parseDouble(String.valueOf(t.getValue()));
                System.out.println("Transaction: tokenAmount10:" + tokenAmount10);
                String tokenAddress = ETH;
                System.out.println("Transaction: tokenAddress:" + tokenAddress);

                Price tokenPrice = this.getTokenPrice(tokenAddress, false);
                while (tokenPrice == null) {
                    try {
                        System.out.println("Transaction: 获取token价格出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tokenPrice = this.getTokenPrice(tokenAddress, true);
                }
                transaction.setPrice(tokenPrice.getPrice());
                transaction.setDecimals(tokenPrice.getDecimal());
                double value = (1.0 * (tokenAmount10 / Math.pow(10, 18)) * (tokenPrice.getPrice() / Math.pow(10, tokenPrice.getDecimal())));
                transaction.setValue(value);

                transaction.setTokenamount10(tokenAmount10);
                transaction.setTokenaddress("0x" + tokenAddress);
                transaction.setMethodid(MethodID);

                transactionMapper.insert(transaction);
            }
        }
    }


    public Price getTokenPrice(String tokenAddress, boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Transaction: web3j:  " + web3j);
        Price p = null;
        String address = "0x56c01c0fC822179Ee6c3FAB8C8a9Ce6787786df9";
        String contractAddress = TVL;
        Function function = new Function("getTokenPrice",
                Arrays.asList(new Address(tokenAddress)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        String encode = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.request.Transaction ethCallTransaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address, contractAddress, encode);
        try {
            String result = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).send().getResult();
            String priceStr = result.substring(2, 66);
            String decimalStr = result.substring(66, 130);
            double price = hexadecimalConversion.covert(priceStr);
            double decimal = hexadecimalConversion.covert(decimalStr);
            p = new Price();
            p.setPrice(price);
            p.setDecimal(decimal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
