/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import com.sun.javafx.runtime.SystemProperties;
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

    /*
CREATE TABLE `unit` (  `id` bigint(20) NOT NULL,  `close` date DEFAULT NULL,  `maintitleid` varchar(4) DEFAULT NULL,  `title` text NOT NULL,  `creator` text NOT NULL,  `mtg` date DEFAULT NULL,  `cut` date DEFAULT NULL,  `etd` date DEFAULT NULL,  `remark` text,  `templateid` bigint(20) DEFAULT NULL,  `versionid` bigint(20) DEFAULT NULL,  `timestamp` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8
    DB側でタイムスタンプにデフォルト値を設定しているので注意する。

     */
    private long id;
    private Date close;
    private String maintitleId;
    private String title;
    private String creator;
    private Date mtg;
    private Date cut;
    private Date etd;
    private String remark;
    private long templateId;
    private long versionId;
    private Timestamp timestamp;

    @Override
    public String toString() {
        String ls = System.lineSeparator();
        //String ls = SystemProperties.getProperty("line.separator");
        return "id: " + String.valueOf(id)
                + ls
                //+ "close: " + close.toString()
                + ls
                + "maintitleId: " + maintitleId
                + ls
                + "title: " + title
                + ls
                + "creator: " + creator
                + ls
                //+ "mtg: " + mtg.toString()
                + ls
                //+ "cut: " + cut.toString()
                + ls
                //+ "etd: " + etd.toString()
                + ls
                + "remark: " + remark
                + ls
                + "templateId: " + String.valueOf(templateId)
                + ls
                + "versionId: " + String.valueOf(versionId)
                + ls
                + "timestamp: " + timestamp.toString();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the close
     */
    public Date getClose() {
        return close;
    }

    /**
     * @return the maintitleId
     */
    public String getMaintitleId() {
        return maintitleId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @return the mtg
     */
    public Date getMtg() {
        return mtg;
    }

    /**
     * @return the cut
     */
    public Date getCut() {
        return cut;
    }

    /**
     * @return the etd
     */
    public Date getEtd() {
        return etd;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @return the templateId
     */
    public long getTemplateId() {
        return templateId;
    }

    /**
     * @return the versionId
     */
    public long getVersionId() {
        return versionId;
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param close the close to set
     */
    public void setClose(Date close) {
        this.close = close;
    }

    /**
     * @param maintitleId the maintitleId to set
     */
    public void setMaintitleId(String maintitleId) {
        this.maintitleId = maintitleId;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @param mtg the mtg to set
     */
    public void setMtg(Date mtg) {
        this.mtg = mtg;
    }

    /**
     * @param cut the cut to set
     */
    public void setCut(Date cut) {
        this.cut = cut;
    }

    /**
     * @param etd the etd to set
     */
    public void setEtd(Date etd) {
        this.etd = etd;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    /**
     * @param versionId the versionId to set
     */
    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
