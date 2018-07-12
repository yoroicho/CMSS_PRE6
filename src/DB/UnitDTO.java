/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import common.TimestampUtil;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.sql.Date;

/**
 *
 * @author tokyo
 */
public class UnitDTO {

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the close
     */
    public Timestamp getClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(Timestamp close) {
        this.close = close;
    }

    /**
     * @return the maintitleid
     */
    public String getMaintitleid() {
        return maintitleid;
    }

    /**
     * @param maintitleid the maintitleid to set
     */
    public void setMaintitleid(String maintitleid) {
        this.maintitleid = maintitleid;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the mtg
     */
    public Timestamp getMtg() {
        return mtg;
    }

    /**
     * @param mtg the mtg to set
     */
    public void setMtg(Timestamp mtg) {
        this.mtg = mtg;
    }

    /**
     * @return the cut
     */
    public Timestamp getCut() {
        return cut;
    }

    /**
     * @param cut the cut to set
     */
    public void setCut(Timestamp cut) {
        this.cut = cut;
    }

    /**
     * @return the etd
     */
    public Timestamp getEtd() {
        return etd;
    }

    /**
     * @param etd the etd to set
     */
    public void setEtd(Timestamp etd) {
        this.etd = etd;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the templateid
     */
    public long getTemplateid() {
        return templateid;
    }

    /**
     * @param templateid the templateid to set
     */
    public void setTemplateid(long templateid) {
        this.templateid = templateid;
    }

    /**
     * @return the versionid
     */
    public long getVersionid() {
        return versionid;
    }

    /**
     * @param versionid the versionid to set
     */
    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }

    
    /**
     * CREATE TABLE `unit` ( `id` bigint(20) NOT NULL, `close` datetime DEFAULT
     * NULL, `maintitleid` varchar(4) DEFAULT NULL, `title` text NOT NULL, `mtg`
     * datetime DEFAULT NULL, `cut` datetime DEFAULT NULL, `etd` datetime
     * DEFAULT NULL, `remark` text, PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT
     * CHARSET=utf8 フィールド変数
     *
     */
    private long id;
    private Timestamp close;
    private String maintitleid;
    private String title;
    private Timestamp mtg;
    private Timestamp cut;
    private Timestamp etd;
    private String remark;
    private long templateid;
    private long versionid;

    
}
