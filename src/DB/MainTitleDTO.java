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
import java.util.Optional;

/**
 *
 * @author tokyo
 */
public class MainTitleDTO {

/*
CREATE TABLE `maintitle` (
    `id` varchar(8) NOT NULL,
    `close` date DEFAULT NULL,
    `seriesid` varchar(8) DEFAULT NULL,
    `maintitle` text NOT NULL,
    `mainkana` text NOT NULL,
    `lang` text DEFAULT NULL,
    `creator` text NOT NULL,
    `mtg` date DEFAULT NULL,
    `cut` date DEFAULT NULL,
    `etd` date DEFAULT NULL,
    `remark` textDEFAULT NULL,
    `timestamp` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
    
    private String id; 
    private Date close; 
    private String seriesid; 
    private String maintitle; 
    private String mainkana; 
    private String lang; 
    private String creator; 
    private Date mtg; 
    private Date cut;
     private Date etd;
     private String remark; 
     private Timestamp timestamp;

    /* 
    @Override
    public String toString() {
        String ls = System.lineSeparator();
        //String ls = SystemProperties.getProperty("line.separator");

        return "id: " + String.valueOf(id)
                + ls
                + "close: " + String.valueOf(close)
                //+ "close: " + close.toString()
                + ls
                + "maintitleId: " + maintitleId
                + ls
                + "title: " + title
                + ls
                + "creator: " + creator
                + ls
                + "mtg: " + String.valueOf(mtg)
                //+ "mtg: " + mtg.toString()
                + ls
                + "cut: " + String.valueOf(cut)
                //+ "cut: " + cut.toString()
                + ls
                + "etd: " + String.valueOf(etd)
                //+ "etd: " + etd.toString()
                + ls
                + "remark: " + remark
                + ls
                + "timestamp: " + timestamp.toString();
    }
*/

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the close
     */
    public Date getClose() {
        return close;
    }

    /**
     * @return the seriesid
     */
    public String getSeriesid() {
        return seriesid;
    }

    /**
     * @return the maintitle
     */
    public String getMaintitle() {
        return maintitle;
    }

    /**
     * @return the mainkana
     */
    public String getMainkana() {
        return mainkana;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
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
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param close the close to set
     */
    public void setClose(Date close) {
        this.close = close;
    }

    /**
     * @param seriesid the seriesid to set
     */
    public void setSeriesid(String seriesid) {
        this.seriesid = seriesid;
    }

    /**
     * @param maintitle the maintitle to set
     */
    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    /**
     * @param mainkana the mainkana to set
     */
    public void setMainkana(String mainkana) {
        this.mainkana = mainkana;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
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
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
