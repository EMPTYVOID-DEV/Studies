package app.lib;

import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;

public class globalStore {
    public static globalStore instance;
    private HashMap<Integer, String> errorsMap;
    private IvParameterSpec iv;

    private globalStore() throws Exception {
        errorsMap = error.init();
        iv = symmetric.genIV(128);
    }

    public static void createInstance() throws Exception {
        if (instance == null)
            instance = new globalStore();
    }

    public HashMap<Integer, String> getErrorMap() {
        return instance.errorsMap;
    }

    public IvParameterSpec getIv() {
        return instance.iv;
    }
}
