package tech.coinbub.daemon.niko;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScriptPublicKey {
    public String asm;
    public String hex;
    public long reqSigs;
    public Type type;
    public List<String> addresses;
    
    public enum Type {
        pubkey,
        pubkeyhash,
        nonstandard
    }
}
