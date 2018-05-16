package tech.coinbub.daemon;

import tech.coinbub.daemon.niko.Block;
import tech.coinbub.daemon.niko.Transaction;
import tech.coinbub.daemon.niko.Unspent;
import java.math.BigDecimal;
import java.util.List;

/*
 * `nikod help` returns the following:
 *
 * checkwallet
 * createrawtransaction [{"txid":txid,"vout":n},...] {address:amount,...}
 * decoderawtransaction <hex string>
 * decodescript <hex string>
 * dumpprivkey <NIKOaddress>
 * dumpwallet <filename>
 * encryptwallet <passphrase>
 * getaccount <NIKOaddress>
 * getaccountaddress <account>
 * getaddednodeinfo <dns> [node]
 * getaddressesbyaccount <account>
 * getbalance [account] [minconf=1]
 * getblockbynumber <number> [txinfo]
 * getblockcount
 * getblockhash <index>
 * getblocktemplate [params]
 * getcheckpoint
 * getconnectioncount
 * getdifficulty
 * getinfo
 * getmininginfo
 * getnettotals
 * getnewpubkey [account]
 * getpeerinfo
 * getrawmempool
 * getrawtransaction <txid> [verbose=0]
 * getreceivedbyaccount <account> [minconf=1]
 * getreceivedbyaddress <NIKOaddress> [minconf=1]
 * getstakesubsidy <hex string>
 * getstakinginfo
 * getsubsidy [nTarget]
 * getwork [data]
 * getworkex [data, coinbase]
 * help [command]
 * importaddress <address> [label] [rescan=true]
 * importprivkey <NIKOprivkey> [label] [rescan=true]
 * importwallet <filename>
 * keypoolrefill [new-size]
 * listaccounts [minconf=1]
 * listaddressgroupings
 * listreceivedbyaccount [minconf=1] [includeempty=false]
 * listreceivedbyaddress [minconf=1] [includeempty=false]
 * listsinceblock [blockhash] [target-confirmations]
 * listtransactions [account] [count=10] [from=0]
 * makekeypair [prefix]
 * move <fromaccount> <toaccount> <amount> [minconf=1] [comment]
 * ping
 * repairwallet
 * resendtx
 * reservebalance [<reserve> [amount]]
 * sendalert <message> <privatekey> <minver> <maxver> <priority> <id> [cancelupto]
 * sendfrom <fromaccount> <toNIKOaddress> <amount> [minconf=1] [comment] [comment-to]
 * sendmany <fromaccount> {address:amount,...} [minconf=1] [comment]
 * sendrawtransaction <hex string>
 * setaccount <NIKOaddress> <account>
 * settxfee <amount>
 * signmessage <NIKOaddress> <message>
 * signrawtransaction <hex string> [{"txid":txid,"vout":n,"scriptPubKey":hex,"redeemScript":hex},...] [<privatekey1>,...] [sighashtype="ALL"]
 * stop
 * submitblock <hex data> [optional-params-obj]
 * validateaddress <NIKOaddress>
 * validatepubkey <NIKOpubkey>
 * verifymessage <NIKOaddress> <signature> <message>
 */
public interface Niko {
    /**
     * `getnewaddress [account]`
     * 
     * Returns a new NIKO address for receiving payments.  If [account] is specified, it is added to the address book so
     * payments received with the address will be credited to [account].
     */
    String getnewaddress();
    String getnewaddress(String label);
    String getnewaddress(String label, String address_type);

    /**
     * `getbestblockhash`
     * 
     * Returns the hash of the best block in the longest block chain.
     */
    String getbestblockhash();

    /**
     * getblockhash <index>
     * Returns hash of block in best-block-chain at <index>.
     */
    String getblockhash(Long index);

    /**
     * `getblock <hash> [txinfo]`
     * 
     * Returns details of a block with given block-hash.
     * 
     * txinfo optional to print more detailed tx info
     */
    Block getblock(String hash);
    Block getblock(String hash, boolean txinfo);
    
    /**
     * `gettransaction <txid>`
     * 
     * Get detailed information about <txid>
     */
    Transaction gettransaction(String txid);
    /**
     * `sendtoaddress <NIKOaddress> <amount> [comment] [comment-to]`
     * 
     * <amount> is a real and is rounded to the nearest 0.000001
     */
    String sendtoaddress(String address, BigDecimal amount);
    String sendtoaddress(String address, BigDecimal amount, String comment);
    String sendtoaddress(String address, BigDecimal amount, String comment, String commentTo);

    public enum NodeAction {
        add,
        remove,
        onetry
    }
}
