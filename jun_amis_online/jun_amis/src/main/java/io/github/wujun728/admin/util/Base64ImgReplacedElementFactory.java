package io.github.wujun728.admin.util;

import cn.hutool.core.io.IoUtil;
import io.github.wujun728.admin.common.service.SysFileService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.codec.Base64;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;
import java.io.InputStream;


public class Base64ImgReplacedElementFactory implements ReplacedElementFactory {

    /**
     * 实现createReplacedElement 替换html中的Img标签
     *
     * @param c 上下文
     * @param box 盒子
     * @param uac 回调
     * @param cssWidth css宽
     * @param cssHeight css高
     * @return ReplacedElement
     */
    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac,
                                                 int cssWidth, int cssHeight) {
        Element e = box.getElement();
        if (e == null) {
            return null;
        }
        String nodeName = e.getNodeName();
        // 找到img标签
        if (nodeName.equals("img")) {
            String attribute = e.getAttribute("src");
            FSImage fsImage;
            try {
                // 生成itext图像
                fsImage = buildImage(attribute, uac);
            } catch (BadElementException e1) {
                fsImage = null;
            } catch (IOException e1) {
                fsImage = null;
            }
            if (fsImage != null) {
                // 对图像进行缩放
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                }
                return new ITextImageElement(fsImage);
            }
        }

        return null;
    }

    /**
     * 将base64编码解码并生成itext图像
     *
     * @param srcAttr 属性
     * @param uac 回调
     * @return FSImage
     * @throws IOException io异常
     * @throws BadElementException BadElementException
     */
    protected FSImage buildImage(String srcAttr, UserAgentCallback uac) throws IOException,
            BadElementException {
        FSImage fsImage = null;
        String down = "/admin/download";
        if (srcAttr.startsWith("data:image/")) {
            String b64encoded = srcAttr.substring(srcAttr.indexOf("base64,") + "base64,".length(),
                    srcAttr.length());
            // 解码
            byte[] decodedBytes = Base64.decode(b64encoded);

            fsImage = new ITextFSImage(Image.getInstance(decodedBytes));
        }else if(srcAttr.contains(down)){
            SysFileService sysFileService = SpringContextUtil.getBean(SysFileService.class);
            InputStream in = sysFileService.download(srcAttr);
            byte[] bytes = IoUtil.readBytes(in, true);
            fsImage = new ITextFSImage(Image.getInstance(bytes));
        } else {
            fsImage = uac.getImageResource(srcAttr).getImage();
        }
        return fsImage;
    }


    /**
     * 实现reset
     */
    @Override
    public void reset() {
    }


    @Override
    public void remove(Element arg0) {}

    @Override
    public void setFormSubmissionListener(FormSubmissionListener arg0) {}
}
