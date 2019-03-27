package com.albert;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;

public class Test {
    
    public static WebClient newWebClient() {
        WebClient wc = new WebClient();
        wc.getOptions().setUseInsecureSSL(true); // 允许使用不安全的SSL连接。如果不打开，站点证书过期的https将无法访问
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器
        wc.getOptions().setCssEnabled(false); //禁用css支持
        // 禁用一些异常抛出
        wc.getOptions().setThrowExceptionOnScriptError(false);
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        wc.getOptions().setDoNotTrackEnabled(false); // 随请求发送DoNotTrack
        wc.setJavaScriptTimeout(1000);      // 设置JS超时，这里是1s
        wc.getOptions().setTimeout(5000); //设置连接超时时间 ，这里是5s。如果为0，则无限期等待
        wc.setAjaxController(new NicelyResynchronizingAjaxController()); // 设置ajax控制器
        
        return wc;
    }
    public static String getTaobaoDetail(String url) {
        WebClient wc = newWebClient();
        
        String detail = "";

        try {
            WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));
//            request.setAdditionalHeaders(searchRequestHeader);

            Page page = wc.getPage(request);

            if(page.isHtmlPage()) {
                HtmlPage htmlPage = (HtmlPage) page;

                String html = htmlPage.asXml();
                DomNodeList<HtmlElement> script = htmlPage.getHead().getElementsByTagName("script");
                String detailUrl = "";
                for(HtmlElement elm : script) {
                    String textContent = elm.getTextContent();
                    if(textContent.contains("var g_config = {")) {
                        for(String line : textContent.split("\n")) {
                            if(line.startsWith("        descUrl")) {
                                detailUrl = "http:" + getFirstMatch(line,
                                        "'//dsc.taobaocdn.com/i[0-9]+/[0-9]+/[0-9]+/[0-9]+/.+[0-9]+'\\s+:"
                                ).replaceAll("\\s+:","").replace("'","");
                                break;
                            }

                        }
                        break;
                    }
                }
                if(StringUtils.isNotBlank(detailUrl))
                detail = wc.getPage(detailUrl).getWebResponse().getContentAsString().replace("var desc='","").replace("';","");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wc.close();
        }
        return detail;
    }
    public static String getTmallDetail(String url) {
        WebClient wc = newWebClient();

        String detail = "";

        try {
            WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));

//            request.setAdditionalHeaders(searchRequestHeader);

            wc.getCurrentWindow().getTopWindow().setOuterHeight(Integer.MAX_VALUE);
            wc.getCurrentWindow().getTopWindow().setInnerHeight(Integer.MAX_VALUE);

            Page page = wc.getPage(request);
            page.getEnclosingWindow().setOuterHeight(Integer.MAX_VALUE);
            page.getEnclosingWindow().setInnerHeight(Integer.MAX_VALUE);

            if(page.isHtmlPage()) {
                HtmlPage htmlPage = (HtmlPage) page;
                ScriptResult sr = htmlPage.executeJavaScript(String.format("javascript:window.scrollBy(0,%d);",Integer.MAX_VALUE));
                // 执行页面所有渲染相关的JS
                int left = 0;
                do {
                    left = wc.waitForBackgroundJavaScript(10);
//                    System.out.println(left);
                } while (left > 7); // 有6-7个时间超长的js任务

//                htmlPage = (HtmlPage)sr.getNewPage();
                detail = htmlPage.getElementById("description").asXml()
                        .replaceAll("src=\"//.{0,100}.png\" data-ks-lazyload=", "src=");  // 移除懒加载
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wc.close();
        }
        return detail;
    }
    /**
     * 获得1688的详情页图片区域生成链接
     * @param url
     * @return
     */
    public static String get1688(String url) {
        String detail = "";
        try (WebClient wc = newWebClient()){
            WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));

            Page page = wc.getPage(request);
            if(page.isHtmlPage()) {
                HtmlPage htmlPage = (HtmlPage) page;
                detail = htmlPage.getElementById("desc-lazyload-container").getAttribute("data-tfs-url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return detail;
    }
    public static String getFirstMatch(String str,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String ret = null;
        if(matcher.find()) {
            ret = matcher.group();
        }
        return ret;
    }
    
    public static void downloadPicture(String href,String dir) throws MalformedURLException {
        URL url = null;

        url = new URL(href);
        String imageName =  dir+ "//"+UUID.randomUUID().toString()+".jpg";
        try (DataInputStream dataInputStream = new DataInputStream(url.openStream());
        		FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));){
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String fetch(String url) {
    	try(WebClient wc = newWebClient()){
    		 WebRequest request = new WebRequest(UrlUtils.toUrlUnsafe(url));
             Page page = wc.getPage(request);
             return (page.getWebResponse().getContentAsString());
    	} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    public static void main(String[] args) throws IOException {
//		String s = get1688("https://detail.1688.com/offer/576277585710.html?spm=b26110380.sw1688.mof001.43.22a748f4eIfqmX&tracelog=p4p&clickid=664e0d7f8db741d9a8f356aae03b1394&sessionid=b283db537c30f88d74dde7a321440ddd");
////		String x = fetch(s);
//		HtmlParser a = new HtmlParser(s);
//        ArrayList<String> hrefList = a.getHrefList();
//        for (int i = 0; i < hrefList.size(); i++)
//            System.out.println(hrefList.get(i));
		    	downloadPicture("https://cbu01.alicdn.com/img/ibank/2018/821/002/9289200128_1878976598.jpg", "C:\\Users\\Administrator\\Desktop\\fff");
    }
    
    
}
