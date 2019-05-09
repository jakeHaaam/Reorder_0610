package com.example.reorder.Api;

import android.os.Bundle;

import java.io.Serializable;

public interface FragmentReplaceable extends Serializable {
    public void replaceFragment(int fragmentId);
}
