package com.example.reorder;

import android.os.Bundle;

import java.io.Serializable;

public interface FragmentReplaceable extends Serializable {
    public void replaceFragment(int fragmentId);
}
