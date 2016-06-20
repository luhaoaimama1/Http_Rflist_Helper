package com.zone.http2rflist;
import com.zone.http2rflist.entity.HttpTypeNet;
import com.zone.http2rflist.utils.StringUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by Administrator on 2016/3/24.
 * OK inside the requestparams to remove the initialization of public parameters and network types
 */
public class NetworkParams {
    protected Map<String, String> headerAddMap;
    protected Map<String, String> headerReplaceMap;
    protected Map<String, String> paramsMap;
    protected Map<String, File> fileMap;
    protected Map<String, String> fileNameMap;
    protected String jsonStr;
    protected HttpTypeNet httpTypeNet =HttpTypeNet.GET;
    protected String encoding;
    public NetworkParams() {
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public NetworkParams setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
        return this;
    }

    private void file2NameMapChecked(){
        if (fileMap == null)
            fileMap = new ConcurrentHashMap<>();
        if (fileNameMap == null)
            fileNameMap = new ConcurrentHashMap<>();
    }

    public NetworkParams put(String key, File file) {
        return  put(key,null,file);
    }

    public NetworkParams put(String key, String value, File file) {
        file2NameMapChecked();
        fileMap.put(key, file);
        fileNameMap.put(key, value == null ? file.getName() : value);
        return this;
    }
    public NetworkParams setFileMap(Map<String, File> fileMap) {
        file2NameMapChecked();
        this.fileMap.putAll(fileMap);
        for (Map.Entry<String, File> stringFileEntry : fileMap.entrySet())
            fileNameMap.put(stringFileEntry.getKey(),stringFileEntry.getValue().getName());
        return this;
    }
    public Map<String, File> getFileMap() {
        return fileMap;
    }
    public Map<String, String> getFileNameMap() {
        return fileNameMap;
    }

    private void paramsMapChecked(){
        if (paramsMap == null)
            paramsMap = new ConcurrentHashMap<>();
    }
    public NetworkParams put(String key, String value) {
        paramsMapChecked();
        paramsMap.put(key, value);
        return this;
    }
    public NetworkParams setParamsMap(Map<String, String> paramsMap) {
        paramsMapChecked();
        this.paramsMap.putAll(paramsMap);
        return this;
    }
    public Map<String, String> getParamsMap() {
        return paramsMap;
    }



    private void headerReplaceMapChecked(){
        if (headerReplaceMap == null)
            headerReplaceMap = new ConcurrentHashMap<>();
    }
    public NetworkParams headsReplace(String key, String value) {
        headerReplaceMapChecked();
        headerReplaceMap.put(key, String.valueOf(value));
        return this;
    }

    public NetworkParams setHeaderReplaceMap(Map<String, String> headerReplaceMap) {
        headerReplaceMapChecked();
        this.headerReplaceMap.putAll(headerReplaceMap);
        return this;
    }
    public Map<String, String> getHeaderReplaceMap() {
        return headerReplaceMap;
    }
    private void  headerAddMapChecked(){
        if (headerAddMap == null)
            headerAddMap = new ConcurrentHashMap<>();
    }
    public NetworkParams headsAdd(String key, String value) {
        headerAddMapChecked();
        headerAddMap.put(key, String.valueOf(value));
        return this;
    }

    public NetworkParams setHeaderAddMap(Map<String, String> headerAddMap) {
        headerAddMapChecked();
        this.headerAddMap.putAll(headerAddMap);
        return this;
    }
    public Map<String, String> getHeaderAddMap() {
        return headerAddMap;
    }

    public  String getEncoding() {
        return encoding;
    }

    public NetworkParams setEncoding(String encoding) {
        if (!StringUtils.isEmptyTrim(encoding)) {
            Charset charset = Charset.forName(encoding);
            if (charset!=null) {
                this.encoding = encoding;
            }
            return this;
        }else
            return this;
    }

    public HttpTypeNet getHttpTypeNet() {
        return httpTypeNet;
    }

     void setHttpTypeNet(HttpTypeNet httpTypeNet) {
        this.httpTypeNet = httpTypeNet;
    }


    private boolean isDownLoad;
    private File target;
    public NetworkParams isDownLoad(File target){
        isDownLoad=true;
        this.target=target;
        return this;
    }

    public boolean isDownLoad() {
        return isDownLoad;
    }


    public File getTarget() {
        return target;
    }

    private boolean isPostJson;

    public boolean isPostJson() {
        return isPostJson;
    }

    public void setPostJson(boolean postJson) {
        isPostJson = postJson;
    }
}
