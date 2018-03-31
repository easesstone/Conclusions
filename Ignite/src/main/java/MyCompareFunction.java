import org.apache.ignite.cache.query.annotations.QuerySqlFunction;

import java.math.BigDecimal;

public class MyCompareFunction {
    @QuerySqlFunction
    public static float featureCompare(float[] currentFeature, float[] historyFeature) {
        double similarityDegree = 0;
        double currentFeatureMultiple = 0;
        double historyFeatureMultiple = 0;
        for (int i = 0; i < currentFeature.length; i++) {
            similarityDegree = similarityDegree + currentFeature[i] * historyFeature[i];
            currentFeatureMultiple = currentFeatureMultiple + Math.pow(currentFeature[i], 2);//pow 返回currentFeature[i] 平方
            historyFeatureMultiple = historyFeatureMultiple + Math.pow(historyFeature[i], 2);
        }

        double tempSim = similarityDegree / Math.sqrt(currentFeatureMultiple) / Math.sqrt(historyFeatureMultiple);//sqrt 平方根 余弦相似度
        double actualValue = new BigDecimal((0.5 + (tempSim / 2)) * 100).//余弦相似度表示为cosineSIM=0.5cosθ+0.5
                setScale(2, BigDecimal.ROUND_HALF_UP).//ROUND_HALF_UP=4 保留两位小数四舍五入
                doubleValue();
        if (actualValue >= 100) {
            return 100;
        }
        return (float) actualValue;
    }
}
