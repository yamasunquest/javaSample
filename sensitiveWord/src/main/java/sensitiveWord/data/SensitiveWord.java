package sensitiveWord.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SensitiveWord {

    /**
     * 敏感词模型
     */
    private static HashMap sensitiveWordModel;

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换字符
     * @return String
     */
    public String replaceSensitiveWord(String txt, String replaceChar) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> sets = getSensitiveWordSets(txt);
        for (String str : sets) {
            StringBuilder resultReplace = new StringBuilder(replaceChar);
            for (int i = 1; i < str.length(); i++) {
                resultReplace.append(replaceChar);
            }

            resultTxt = resultTxt.replaceAll(str, resultReplace.toString());
        }

        return resultTxt;
    }

    /**
     *
     * @param txt 需要判断的字符
     * @return 包含的敏感词列表
     */
    private Set<String> getSensitiveWordSets(String txt) {
        Set<String> sensitiveWordSets = new HashSet<>();
        for (int n = 0; n < txt.length(); n++) {
            // 判断是否包含敏感字符
            // 匹配标识长度
            int matchLen = 0;
            // 字符结束标识
            boolean isEnd = false;

            char checkWord;
            Map pointMap = sensitiveWordModel;

            /**
             *
             */
            if(pointMap == null){
                return sensitiveWordSets;
            }

            for (int i = n; i < txt.length(); i++) {
                checkWord = txt.charAt(i);
                // 获取指定key
                pointMap = (Map) pointMap.get(checkWord);
                // 存在，则判断是否为最后一个
                if (pointMap == null) {
                    matchLen = 0;
                    break;
                } else {
                    // 找到相应key，匹配标识+1
                    matchLen++;
                    if ("1".equals(pointMap.get("isEnd"))) {
                        // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                        isEnd = true;
                        break;
                    }

                }
            }

            // 长度必须大于等于1，为词
            if (matchLen < 2 || isEnd == false) {
                matchLen = 0;
            }

            if (matchLen > 0) {
                // 存在,加入list中
                sensitiveWordSets.add(txt.substring(n, n + matchLen));
                // 减1的原因，是因为for会自增
                n = n + matchLen - 1;
            }
        }

        return sensitiveWordSets;
    }

    /**
     * 构建敏感词模型
     * 基于 DFA 算法
     * @param sensitiveWord
     */
    public void rebuildModel(Set<String> sensitiveWord){
        // 重新构建敏感词空间
        if(sensitiveWordModel == null){
        }else{
            sensitiveWordModel.clear();
        }
        sensitiveWordModel = new HashMap(sensitiveWord.size());

        String key;
        Map pointMap;
        Map<String, String> newMap;

        System.out.println("model build at" + System.currentTimeMillis());
        // 循环读取sensitiveWord
        for (String aKeyWordSet : sensitiveWord) {
            // 需要处理的敏感词
            key = aKeyWordSet;
            // 定位，一直指向需要处理的节点
            pointMap = sensitiveWordModel;
            // 字符级循环，处理敏感词
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 空字符丢弃
                if ((int) keyChar == 65279) {
                    continue;
                }

                // 获取
                Object wordMap = pointMap.get(keyChar);

                if (wordMap == null) {
                    // 构建一个isEnd为0的未结束节点map
                    newMap = new HashMap<>();
                    newMap.put("isEnd", "0");
                    // 未找到，那么将其添加进去
                    pointMap.put(keyChar, newMap);
                    pointMap = newMap;
                } else {
                    // 存在key，将其交换至定位节点
                    pointMap = (Map) wordMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个字符
                    pointMap.put("isEnd", "1");
                }
            }
        }

        System.out.println("model build finish at" + System.currentTimeMillis());
    }
}
