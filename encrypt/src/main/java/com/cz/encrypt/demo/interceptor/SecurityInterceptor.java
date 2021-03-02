package com.cz.encrypt.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cz.encrypt.SecurityUtils;
import com.cz.encrypt.demo.constant.SecurityConstants;
import com.cz.encrypt.demo.exception.ServiceException;
import com.cz.encrypt.demo.service.SecurityService;
import com.cz.encrypt.sign.MapToUrlSign;
import com.cz.encrypt.url.StringUrlParser;
import com.cz.encrypt.url.UrlParser;
import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collection;

/**
 * @author jiaozi<liaomin @ gvt861.com>
 * @since JDK8
 * Creation time：2019/8/13 16:30
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {
    public static final String VERSION_FIELD = "version";
    public static final String SIGN_FIELD = "sign";
    public static final String APPID_FIELD = "appid";
    public static final String APPLICATION_TYPE = "application/json";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String REQUEST_GET = "GET";
    public static final String APPLICATION_ZIP = "application/zip";
    @Autowired
    private SecurityService securityService;
    private String md5(byte[] bt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest =MessageDigest.getInstance("MD5");
        return Base64.getEncoder().encodeToString(messageDigest.digest(bt));
    }
    /**
     * 检测传输数据是否正确
     * @param request
     * @throws ServiceException
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     * @throws InvalidKeyException
     */
    public void checkTransferData(HttpServletRequest request) throws ServiceException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String contentType= request.getHeader(CONTENT_TYPE_KEY);
        String version=null;
        String sign=null;
        String appid=null;
        String jsonData=null;
        String queryString=null;
        boolean urlSign=false;
        if(APPLICATION_TYPE.equalsIgnoreCase(contentType)
                || (contentType != null && contentType.toLowerCase().contains(APPLICATION_TYPE))){
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String body = IOUtils.toString(reader);
            JSONObject jsonObject= JSONObject.parseObject(body);
            version=jsonObject.getString(VERSION_FIELD);
            sign=jsonObject.getString(SIGN_FIELD);
            appid=jsonObject.getString(APPID_FIELD);
            jsonData=body;
        }else{
            if(REQUEST_GET.equalsIgnoreCase(request.getMethod())) {
                version = request.getParameter(VERSION_FIELD);
                sign = request.getParameter(SIGN_FIELD);
                appid = request.getParameter(APPID_FIELD);
                queryString=request.getQueryString();
                urlSign=true;
            }else{
                //檢測是否有文件上傳
                if(ServletFileUpload.isMultipartContent(request)) {
                    StringBuffer paramUrl=new StringBuffer();
//                    DiskFileItemFactory factory = new DiskFileItemFactory();
//                    factory.setSizeThreshold(1024*10);
//                    ServletFileUpload fileUpload = new ServletFileUpload(factory);
//                    fileUpload.setSizeMax(10*1024*1024);
                    try {
                        Collection<Part> parts = request.getParts();
//                        List<FileItem> items = fileUpload.parseRequest(request);
                        for(Part part : parts) {
                            String key= part.getName();
                            String value=null;
                            //是不是一个文件上传组件
                            ApplicationPart applicationPart = (ApplicationPart)part;
                            Field field = applicationPart.getClass().getDeclaredField("fileItem");
                            field.setAccessible(true);
                            FileItem fileItem = (FileItem)field.get(applicationPart);
                            if(!fileItem.isFormField()) {
                                byte[] bytes = IOUtils.toByteArray(applicationPart.getInputStream());
                                value=md5(bytes);
                                contentType = applicationPart.getContentType();
                            } else {
                                value = applicationPart.getString("UTF-8");
                                if("version".equalsIgnoreCase(key)){
                                    version = value;
                                }else if("appid".equalsIgnoreCase(key)){
                                    appid = value;
                                }else if("sign".equalsIgnoreCase(key)){
                                    sign = value;
                                }
                            }
                            paramUrl.append(key+ "=" + value + "&");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(paramUrl.toString());
                    urlSign = true;
                    StringUrlParser stringUrlParser=new StringUrlParser(paramUrl.toString());
                    queryString = stringUrlParser.parse();
                    Object signObj = stringUrlParser.getDataMap().get(MapToUrlSign.SIGN_KEY);
                    sign =(signObj!=null?signObj.toString():"");
                }

            }
        }
        //是否带版本号
        if(StringUtils.isEmpty(version)) {
            throw new ServiceException(SecurityConstants.VERSION_NULL);
        }
        if(StringUtils.isEmpty(sign)) {
            throw new ServiceException(SecurityConstants.SIGN_NULL);
        }
        if(StringUtils.isEmpty(appid)) {
            throw new ServiceException(SecurityConstants.APPID_NULL);
        }
        checkAppId(appid);
        checkVersion(version);
        checkSign(urlSign,sign.replaceAll(" ","+"),queryString,jsonData,appid,contentType);

    }
    public void checkAppId(String appid){
        if(!securityService.checkAppId(appid)){
            throw new ServiceException(SecurityConstants.APPID_ERROR);
        }
    }
    public void checkVersion(String version){
        if(!securityService.checkVersion(version)){
            throw new ServiceException(SecurityConstants.VERSION_ERROR);
        }
    }
    public void checkUrlSign(String sign,String url,String appid) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            UrlParser urlParser=new StringUrlParser(url);
            if (!SecurityUtils.validateSign(SecurityUtils.getPubKey(securityService.getPublicKey(appid)),
                    urlParser.parse().getBytes(),
                    Base64.getDecoder().decode(sign.toString()))
            ) {
                throw new ServiceException(SecurityConstants.SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new ServiceException(SecurityConstants.SIGN_ERROR);
        }
    }

    public void checkJsonSign(String sign,String jsonData,String appid) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        try {
            boolean ifSign = securityService.checkSign(securityService.getPublicKey(appid), jsonData);
            if(!ifSign){
                throw new ServiceException(SecurityConstants.SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new ServiceException(SecurityConstants.SIGN_ERROR);
        }
    }
    public void checkSign(boolean urlSign,String sign,String oriData,String jsonData,String appid,String contentType) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if(urlSign){
            checkUrlSign(sign,oriData,appid);
        }else{
            if(APPLICATION_TYPE.equalsIgnoreCase(contentType)) {
                checkJsonSign(sign, jsonData, appid);
            }
         }
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().endsWith("status/VERSION.txt")){
            return true;
        }
        checkTransferData(request);
        return true;
    }
}
