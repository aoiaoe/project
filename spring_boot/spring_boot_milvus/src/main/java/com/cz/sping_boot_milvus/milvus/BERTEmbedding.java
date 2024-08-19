package com.cz.sping_boot_milvus.milvus;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class BERTEmbedding {

    // 加载BERT模型
    public static SavedModelBundle loadBERTModel(String modelPath) {
        return SavedModelBundle.load(modelPath, "serve");
    }

    // 使用BERT模型将文本转换为向量
    public static float[] embedText(String text, SavedModelBundle model) {
        try (Session session = model.session()) {
            // 创建输入张量
            Tensor<String> inputTensor = Tensor.create(text.getBytes(StandardCharsets.UTF_8), String.class);
            
            // 运行会话以获取输出张量
            List<Tensor<?>> outputs = session.runner()
                                             .feed("serving_default_input_word_ids:0", inputTensor)
                                             .fetch("StatefulPartitionedCall:0")
                                             .run();
            
            // 提取输出向量
            float[][] embeddings = new float[1][];
            outputs.get(0).copyTo(embeddings);
            return embeddings[0];
        }
    }

    public static void main(String[] args) {
        // 指定BERT模型路径
        String modelPath = "G:\\AI_models\\bert\\bert-tensorflow2-zh-l-12-h-768-a-12-v4";
        
        // 加载BERT模型
        SavedModelBundle model = loadBERTModel(modelPath);
        
        // 测试文本
        String testSentence = "这是一个测试句子";
        
        // 将文本转换为向量
        float[] vector = embedText(testSentence, model);
        System.out.println("向量：" + Arrays.toString(vector));
    }
}
