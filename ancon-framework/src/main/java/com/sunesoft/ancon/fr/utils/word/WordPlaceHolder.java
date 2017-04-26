package com.sunesoft.ancon.fr.utils.word;


import com.sunesoft.ancon.fr.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Word 占位符，用于配合 wsWord 使用。
 */
public class WordPlaceHolder {

    private String tempFilePath;
    private Map<String, String> textHolderMap;
    private Map<String, List<String>> imageHolderMap;

    public WordPlaceHolder(String tempFilePath) {
        this.tempFilePath = tempFilePath;
        textHolderMap = new HashMap<>();
        imageHolderMap = new HashMap<>();
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    /**
     * 添加图片占位符，若多次添加，则可以替换多张图片。
     *
     * @param holderName 占位符名称。
     * @param imageUrl   图片地址。
     * @return 占位符。
     */
    public WordPlaceHolder addImageHolder(String holderName, String imageUrl) {
        if (StringUtils.isNullOrWhiteSpace(imageUrl))
            return this;
        List<String> imageUrls = imageHolderMap.get(holderName);
        if (imageUrls == null)
            imageUrls = new ArrayList<>();
        imageUrls.add(imageUrl);
        this.imageHolderMap.put(holderName, imageUrls);
        return this;
    }

    /**
     * 添加文本占位符，若多次添加，则以最新版本为准。
     *
     * @param holderName 占位符名称。
     * @param text       文本名称。
     * @return 占位符。
     */
    public WordPlaceHolder addTextHolder(String holderName, String text) {
        if (StringUtils.isNullOrWhiteSpace(text))
            return this;
        this.textHolderMap.put(holderName, text);
        return this;
    }

    public Map<String, String> getTextHolderMap() {
        return textHolderMap;
    }

    public Map<String, List<String>> getImageHolderMap() {
        return imageHolderMap;
    }
}
