package com.magdy.abo100.models.cart;

import com.google.gson.annotations.Expose;

public class AddData {
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
