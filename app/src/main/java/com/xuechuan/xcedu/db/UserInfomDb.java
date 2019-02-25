package com.xuechuan.xcedu.db;

import com.xuechuan.xcedu.db.Converent.UserConverent;
import com.xuechuan.xcedu.db.Converent.UserLookConverent;
import com.xuechuan.xcedu.db.Converent.UserLookVideoConverent;
import com.xuechuan.xcedu.vo.Db.UserLookVideoVo;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.Db.UserLookVo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db
 * @Description: 用户信息
 * @author: L-BackPacker
 * @date: 2018/5/9 8:38
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
@Entity
public class UserInfomDb {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 用户信息
     */
    @Convert(converter = UserConverent.class, columnType = String.class)
    private UserInfomVo vo;
    /**
     * 用户标识
     */
    private String moid;
    /**
     * 技术实务 true 为购买，false为不购买
     */
    private boolean SkillBook;
    /**
     * 综合能力 true 为购买，false为不购买
     */
    private boolean ColligateBook;
    /**
     * 案例分析 true 为购买，false为不购买
     */
    private boolean CaseBook;
    /**
     * 用户选中展示的状态（夜间，白天）
     */
    private String ShowDayOrNight;
    /**
     * 用户是否选中自动跳转
     */
    private boolean userNextGo;
    /**
     * 用户token
     */
    private String token;
    /**
     * token 过期时间
     */
    private String tokenTime;
    /**
     * 几次移除错题数
     */
    private String delectQuestion;
    /**
     * 技术 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> skillData;
    /**
     * 综合 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> coloctData;
    /**
     * 案例 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> caseData;

    /**
     *错题 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> WrongDataSkill;

    /**
     *错题 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> WrongDataColoct;
    /**
     *错题 保存记录
     */
    @Convert(converter = UserLookConverent.class, columnType = String.class)
    private List<UserLookVo> WrongDataCase;

    /**
     *用户查看视频记录 保存记录
     */
    @Convert(converter = UserLookVideoConverent.class, columnType = String.class)
    private List<UserLookVideoVo> lookVideolist;

    @Generated(hash = 1943522123)
    public UserInfomDb(Long id, UserInfomVo vo, String moid, boolean SkillBook,
            boolean ColligateBook, boolean CaseBook, String ShowDayOrNight,
            boolean userNextGo, String token, String tokenTime,
            String delectQuestion, List<UserLookVo> skillData,
            List<UserLookVo> coloctData, List<UserLookVo> caseData,
            List<UserLookVo> WrongDataSkill, List<UserLookVo> WrongDataColoct,
            List<UserLookVo> WrongDataCase, List<UserLookVideoVo> lookVideolist) {
        this.id = id;
        this.vo = vo;
        this.moid = moid;
        this.SkillBook = SkillBook;
        this.ColligateBook = ColligateBook;
        this.CaseBook = CaseBook;
        this.ShowDayOrNight = ShowDayOrNight;
        this.userNextGo = userNextGo;
        this.token = token;
        this.tokenTime = tokenTime;
        this.delectQuestion = delectQuestion;
        this.skillData = skillData;
        this.coloctData = coloctData;
        this.caseData = caseData;
        this.WrongDataSkill = WrongDataSkill;
        this.WrongDataColoct = WrongDataColoct;
        this.WrongDataCase = WrongDataCase;
        this.lookVideolist = lookVideolist;
    }

    @Generated(hash = 1986169396)
    public UserInfomDb() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfomVo getVo() {
        return this.vo;
    }

    public void setVo(UserInfomVo vo) {
        this.vo = vo;
    }

    public String getMoid() {
        return this.moid;
    }

    public void setMoid(String moid) {
        this.moid = moid;
    }

    public boolean getSkillBook() {
        return this.SkillBook;
    }

    public void setSkillBook(boolean SkillBook) {
        this.SkillBook = SkillBook;
    }

    public boolean getColligateBook() {
        return this.ColligateBook;
    }

    public void setColligateBook(boolean ColligateBook) {
        this.ColligateBook = ColligateBook;
    }

    public boolean getCaseBook() {
        return this.CaseBook;
    }

    public void setCaseBook(boolean CaseBook) {
        this.CaseBook = CaseBook;
    }

    public String getShowDayOrNight() {
        return this.ShowDayOrNight;
    }

    public void setShowDayOrNight(String ShowDayOrNight) {
        this.ShowDayOrNight = ShowDayOrNight;
    }

    public boolean getUserNextGo() {
        return this.userNextGo;
    }

    public void setUserNextGo(boolean userNextGo) {
        this.userNextGo = userNextGo;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenTime() {
        return this.tokenTime;
    }

    public void setTokenTime(String tokenTime) {
        this.tokenTime = tokenTime;
    }

    public String getDelectQuestion() {
        return this.delectQuestion;
    }

    public void setDelectQuestion(String delectQuestion) {
        this.delectQuestion = delectQuestion;
    }

    public List<UserLookVo> getSkillData() {
        return this.skillData;
    }

    public void setSkillData(List<UserLookVo> skillData) {
        this.skillData = skillData;
    }

    public List<UserLookVo> getColoctData() {
        return this.coloctData;
    }

    public void setColoctData(List<UserLookVo> coloctData) {
        this.coloctData = coloctData;
    }

    public List<UserLookVo> getCaseData() {
        return this.caseData;
    }

    public void setCaseData(List<UserLookVo> caseData) {
        this.caseData = caseData;
    }

    public List<UserLookVo> getWrongDataSkill() {
        return this.WrongDataSkill;
    }

    public void setWrongDataSkill(List<UserLookVo> WrongDataSkill) {
        this.WrongDataSkill = WrongDataSkill;
    }

    public List<UserLookVo> getWrongDataColoct() {
        return this.WrongDataColoct;
    }

    public void setWrongDataColoct(List<UserLookVo> WrongDataColoct) {
        this.WrongDataColoct = WrongDataColoct;
    }

    public List<UserLookVo> getWrongDataCase() {
        return this.WrongDataCase;
    }

    public void setWrongDataCase(List<UserLookVo> WrongDataCase) {
        this.WrongDataCase = WrongDataCase;
    }

    public List<UserLookVideoVo> getLookVideolist() {
        return this.lookVideolist;
    }

    public void setLookVideolist(List<UserLookVideoVo> lookVideolist) {
        this.lookVideolist = lookVideolist;
    }


}
