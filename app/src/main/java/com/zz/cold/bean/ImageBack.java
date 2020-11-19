package com.zz.cold.bean;

import com.zz.cold.BuildConfig;

public class ImageBack {
    String deptId;// 112,
    String id;// 1,
    String oldName;// 1603874736736.jpg,
    String newName;// null,
    String savePath;// /home/WisdomSupervise/uploadPath/upload/coldchain/image/2020/11/19/c1ee1562-4f8c-4f84-aeaf-691384a773a7.jpg,
    String downloadUrl;// /profile/upload/coldchain/image/2020/11/19/c1ee1562-4f8c-4f84-aeaf-691384a773a7.jpg,
    String size;// 191727,
    String suffix;// jpg,
    String sha1;// null
    String path;// null

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getDownloadUrl() {
        return BuildConfig.API_HOST+downloadUrl;
    }

    public String getSize() {
        return size;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getSha1() {
        return sha1;
    }

    public String getPath() {
        return path;
    }
}
