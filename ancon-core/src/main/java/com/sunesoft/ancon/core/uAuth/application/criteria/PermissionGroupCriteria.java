package com.sunesoft.ancon.core.uAuth.application.criteria;

import com.sunesoft.ancon.fr.results.PagedCriteria;

/**
 * Created by jiangkefan on 2016/5/26.
 */
public class PermissionGroupCriteria extends PagedCriteria {

     private String permissionGroupName;

    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }
}
