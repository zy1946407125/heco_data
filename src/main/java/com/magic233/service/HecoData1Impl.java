package com.magic233.service;

import com.magic233.entity.Fee;
import com.magic233.entity.Price;
import com.magic233.mapper.FeeMapper;
import com.magic233.utils.HexadecimalConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: hecoDataImpl
 * Description:
 * date: 2021/7/26 13:56
 * author: zouyuan
 */
@Service
public class HecoData1Impl implements HecoData1 {
    private String Link = "https://http-mainnet-node.huobichain.com";
    private String factoryFeeTo = "0x0000000000000000000000001B406A94A831709ab7aBc175703032237f949aAa";
    private String Factory = "0x9b40874284dE3Ed50Be4EaA3A4501C30b9bDC29B";
    private String TVL = "0xc85474Cfae8eA933ab4785f480cC11e2cB1A4535";

    @Autowired
    private FeeMapper feeMapper;
    Web3j web3j = Web3j.build(new HttpService(Link));
    HexadecimalConversion hexadecimalConversion = new HexadecimalConversion();


    @Override
    public int start(BigInteger blockHeight, List<String> address, boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Fee: web3j:  " + web3j);
        Event TRANSFER_EVENT = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
                                                },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(false) {
                        }));
        String TRANSFER_EVENT_HASH = EventEncoder.encode(TRANSFER_EVENT);

        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(blockHeight),
                DefaultBlockParameter.valueOf(blockHeight),
//                DefaultBlockParameterName.LATEST,
                address)
                .addSingleTopic(TRANSFER_EVENT_HASH)
                .addOptionalTopics("0x0000000000000000000000000000000000000000000000000000000000000000")
                .addOptionalTopics(factoryFeeTo);

        Request<?, EthLog> ethLogRequest = web3j.ethGetLogs(filter);
        try {
            List<EthLog.LogResult> logs = ethLogRequest.send().getLogs();
            for (EthLog.LogResult log : logs) {
                System.out.println("Fee: " + log);
                EthLog.LogObject logObject = (EthLog.LogObject) log.get();
                EthBlock.Block blockInfo = getBlockInfo(logObject.getBlockHash());
                while (blockInfo == null) {
                    try {
                        System.out.println("Fee: 获取区块信息出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    blockInfo = getBlockInfo(logObject.getBlockHash());
                }
                Fee fee = new Fee();
                fee.setTransactionhash(logObject.getTransactionHash());
                fee.setBlocknumber(Long.valueOf(String.valueOf(logObject.getBlockNumber())));
                fee.setAddress(logObject.getAddress());
                fee.setData(logObject.getData());
                //数量
                double data10 = hexadecimalConversion.covert(logObject.getData().substring(2));
                fee.setData10(data10);
                //单价
                Price pairPrice = this.getPairPrice(fee.getAddress(), false);
                while (pairPrice == null) {
                    try {
                        System.out.println("Fee: 获取pair价格出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pairPrice = this.getPairPrice(fee.getAddress(), true);
                }
                fee.setPrice(pairPrice.getPrice());
                //单价精度
                fee.setDecimals(pairPrice.getDecimal());
                //价值
                double value = (1.0 * (data10 / Math.pow(10, 18)) * (pairPrice.getPrice() / Math.pow(10, pairPrice.getDecimal())));
                fee.setValue(value);
                //交易时间
                Long timestamp = Long.valueOf(String.valueOf(blockInfo.getTimestamp()));
                fee.setTime(timestamp);
                int re = feeMapper.insert(fee);
            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public BigInteger getBlockHeight(boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Fee: web3j:  " + web3j);
        BigInteger blockMax = BigInteger.valueOf(0);
        Request<?, EthBlockNumber> ethBlockNumberRequest = web3j.ethBlockNumber();
        try {
            blockMax = ethBlockNumberRequest.send().getBlockNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Fee: blockMax:  " + blockMax);
        return blockMax;
    }

    public EthBlock.Block getBlockInfo(String blockHash) {
        EthBlock.Block block = null;
        Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByHash(blockHash, true);
        try {
            block = ethBlockRequest.send().getBlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;
    }

    @Override
    public long getPairLength(boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Fee: web3j:  " + web3j);
        String address = "0x56c01c0fC822179Ee6c3FAB8C8a9Ce6787786df9";
        String contractAddress = Factory;
        Function function = new Function("allPairsLength",
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        String encode = FunctionEncoder.encode(function);
        Transaction ethCallTransaction = Transaction.createEthCallTransaction(address, contractAddress, encode);
        long length = -1;
        try {
            String result = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).send().getResult();
            length = (long) hexadecimalConversion.covert(result.substring(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length;
    }

    @Override
    public String getPair(long i, boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Fee: web3j:  " + web3j);
        String address = "0x56c01c0fC822179Ee6c3FAB8C8a9Ce6787786df9";
        String contractAddress = Factory;
        Function function = new Function("allPairs",
                Arrays.asList(new Uint256(i)),
                Arrays.asList(new TypeReference<Address>() {
                }));
        String encode = FunctionEncoder.encode(function);
        Transaction ethCallTransaction = Transaction.createEthCallTransaction(address, contractAddress, encode);
        String pair = "";
        try {
            String result = web3j.ethCall(ethCallTransaction, DefaultBlockParameterName.LATEST).send().getResult();
            pair = result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pair;
    }

    @Override
    public Price getPairPrice(String pair, boolean updateWeb3j) {
        if (updateWeb3j) {
            this.web3j = Web3j.build(new HttpService(Link));
        }
        System.out.println("Fee: web3j:  " + web3j);
        Price p = null;
        String address = "0x56c01c0fC822179Ee6c3FAB8C8a9Ce6787786df9";
        String contractAddress = TVL;
        Function function = new Function("getLPPrice",
                Arrays.asList(new Address(pair)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        String encode = FunctionEncoder.encode(function);
        Transaction ethCallTransaction = Transaction.createEthCallTransaction(address, contractAddress, encode);
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
