package com.sunesoft.ancon.fr.utils.word;

import com.aspose.words.*;

import java.util.List;

class ReplaceImage implements IReplacingCallback {

    private List<String> imgUrls; //图片路径

    public ReplaceImage(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public int replacing(ReplacingArgs e) throws Exception {
        Node node = e.getMatchNode();
        Document doc = (Document) node.getDocument();
        DocumentBuilder docBuilder = new DocumentBuilder(doc);
        docBuilder.moveTo(node);
        for (String imgUrl : imgUrls) {
            docBuilder.insertImage(imgUrl);
        }
        return ReplaceAction.REPLACE;
    }
}
