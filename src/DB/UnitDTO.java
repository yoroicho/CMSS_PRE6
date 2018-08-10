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
CREATE TABLE `unit` (
  `id` bigint(20) NOT NULL,
  `close` datetime DEFAULT NULL,
  `maintitleid` varchar(4) DEFAULT NULL,
  `title` text NOT NULL,
  `mtg` datetime DEFAULT NULL,
  `cut` datetime DEFAULT NULL,
  `etd` datetime DEFAULT NULL,
  `remark` text,
  `templateId` bigint(20) DEFAULT NULL,
  `versionId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
     *
     */
    private long id;
    private Date close;
    private String maintitleId;
    private String title;
    private Date mtg;
    private Date cut;
    private Date etd;
    private String remark;
    private long templateId;
    private long versionId;

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
    public Date getClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(Date close) {
        this.close = close;
    }

    /**
     * @return the maintitleid
     */
    public String getMaintitleId() {
        return maintitleId;
    }

    /**
     * @param maintitleid the maintitleid to set
     */
    public void setMaintitleId(String maintitleId) {
        this.maintitleId = maintitleId;
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
    public Date getMtg() {
        return mtg;
    }

    /**
     * @param mtg the mtg to set
     */
    public void setMtg(Date mtg) {
        this.mtg = mtg;
    }

    /**
     * @return the cut
     */
    public Date getCut() {
        return cut;
    }

    /**
     * @param cut the cut to set
     */
    public void setCut(Date cut) {
        this.cut = cut;
    }

    /**
     * @return the etd
     */
    public Date getEtd() {
        return etd;
    }

    /**
     * @param etd the etd to set
     */
    public void setEtd(Date etd) {
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
     * @return the templateId
     */
    public long getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the versionId
     */
    public long getVersionId() {
        return versionId;
    }

    /**
     * @param versionId the versionId to set
     */
    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    
}
