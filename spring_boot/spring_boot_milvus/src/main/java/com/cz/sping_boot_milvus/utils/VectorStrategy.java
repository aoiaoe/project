package com.cz.sping_boot_milvus.utils;

import org.tensorflow.SavedModelBundle;

public interface VectorStrategy {
    Float[] embedText(String text);
}
