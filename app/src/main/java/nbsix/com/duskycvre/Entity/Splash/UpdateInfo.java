package nbsix.com.duskycvre.Entity.Splash;


/**
 * Name: UpdateInfo
 * Author: Dusky
 * QQ: 1042932843
 * Comment: Fir.im的版本bean
 * Date: 2017-04-11 22:15
 */

public class UpdateInfo {
    private String name; //应用名称
    private String version; //版本
    private String changelog; //更新日志
    private String versionShort; //版本编号(兼容旧版字段)
    private String build; //编译号
    private String updated_at;
    private String direct_install_url;
    private String installUrl; //安装地址（兼容旧版字段）
    private String install_url; //安装地址(新增字段)
    private String update_url; //更新地址(新增字段)
    private Binary binary; //更新文件的对象，仅有大小字段fsize

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(Binary binary) {
        this.binary = binary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }


    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }
}
