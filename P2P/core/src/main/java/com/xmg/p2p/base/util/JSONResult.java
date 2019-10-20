package com.xmg.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class JSONResult {
    private String msg;
    private boolean success;

    public JSONResult() {
        super();
    }

    public JSONResult(String msg) {
        super();
        this.msg = msg;
    }

    public JSONResult(boolean success,String msg){
        this.success = success;
        this.msg = msg;
    }
}
