package com.cz.sping_boot_milvus.utils;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.UnsupportedEncodingException;

/**
 * 使用tensorflow加载模型将文本进行向量化
 */
public class VectorTensorStrategy implements VectorStrategy {

    // 假设有一个已加载的BERT模型
    private static final SavedModelBundle DEFAULT_MODEL = SavedModelBundle.load("G:\\AI_models\\bert\\bert-tensorflow2-zh-l-12-h-768-a-12-v4", "serve");

    @Override
    public Float[] embedText(String text) {
        return embedText(text, DEFAULT_MODEL);
    }

    public Float[] embedText(String text, SavedModelBundle model){
        Session session = model.session();
        Tensor<String> inputTensor = null;
        try {
            inputTensor = Tensor.create(text.getBytes("UTF-8"), String.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Tensor<?> outputTensor = session.runner().feed("serving_default_input_type_ids:0", inputTensor)
                .fetch("StatefulPartitionedCall:0").run().get(0);
        Float[][] embeddings = new Float[1][];
        outputTensor.copyTo(embeddings);
        return embeddings[0];
    }

    public static void main(String[] args) {
        System.out.println(new VectorTensorStrategy().embedText("今天是2024年7月8日，晚上吃的泡面"));
    }

}
