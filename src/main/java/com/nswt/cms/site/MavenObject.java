package com.nswt.cms.site;

/**
 * @User SimonKing
 * @Date 16/10/27.
 * @Time 14:35.
 * @Mail yuhongkai@nswt.com.cn
 */
public class MavenObject {
    /**
     * Maven ��������
     * @param  shellpath //shell�ļ�·��
     * @param  appName  //Ӧ��������maven�б�����ArtifactId
     * @param  workPath //Ҫ����Ӧ�����ɵ�Ŀ���ַ
     * @param  archetype //maven ��������generate ͨ��ģ�幹��Ӧ��
     * @param  archetypeCatalog //archetypeCatalogĬ����maven������archetype-catalog.xml��ַ�ٶȻ�������ԭ�������������ָ��Ϊlocal
     * @param  groupId //��ǰ����Ӧ�õ���֯ID ����:groupId=com.nswt
     * @param  version //��ǰ����Ӧ�õİ汾�� ����:version=1.0-SNAPSHOT
     * @param  archetypeGroupId //������ǰӦ����Ҫʹ�õ�ģ��Ϊ�Ǹ���֯��ID ����: archetypeGroupId=com.nswt
     * @param  archetypeArtifactId //������ǰӦ��ʹ�õ�ģ�������Ҳ����ģ���ArtifactId ����: archetypeArtifactId=nswt-web-archetype
     * @param  archetypeVersion //������ǰӦ��ʹ�õ�ģ��汾�� ����:archetypeVersion="1.0
     * @param  archetypeRepository //������ǰӦ��ʹ�õĻ���ģ�����Ǹ��ֿ��ṩ��,����:archetypeRepository="http://192.168.2.86:9091/nexus/content/groups/public/archetype-catalog.xml
     * @param  packagename //��ǰ����Ӧ�õİ���,����:package=com.test.xxxx
     * @param  interactiveMode //false
     */

    public String shellpath;

    public String appName;

    public String workPath;

    public String archetype;

    public String archetypeCatalog;

    public String groupId;

    public String version;

    public String archetypeGroupId;

    public String archetypeArtifactId;

    public String archetypeVersion;

    public String archetypeRepository;

    public String packagename;

    public String interactiveMode;


    public String getShellpath() {
        return shellpath;
    }

    public void setShellpath(String shellpath) {
        this.shellpath = shellpath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getWorkPath() {
        return workPath;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getArchetypeCatalog() {
        return archetypeCatalog;
    }

    public void setArchetypeCatalog(String archetypeCatalog) {
        this.archetypeCatalog = archetypeCatalog;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArchetypeGroupId() {
        return archetypeGroupId;
    }

    public void setArchetypeGroupId(String archetypeGroupId) {
        this.archetypeGroupId = archetypeGroupId;
    }

    public String getArchetypeArtifactId() {
        return archetypeArtifactId;
    }

    public void setArchetypeArtifactId(String archetypeArtifactId) {
        this.archetypeArtifactId = archetypeArtifactId;
    }

    public String getArchetypeVersion() {
        return archetypeVersion;
    }

    public void setArchetypeVersion(String archetypeVersion) {
        this.archetypeVersion = archetypeVersion;
    }

    public String getArchetypeRepository() {
        return archetypeRepository;
    }

    public void setArchetypeRepository(String archetypeRepository) {
        this.archetypeRepository = archetypeRepository;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getInteractiveMode() {
        return interactiveMode;
    }

    public void setInteractiveMode(String interactiveMode) {
        this.interactiveMode = interactiveMode;
    }
}
