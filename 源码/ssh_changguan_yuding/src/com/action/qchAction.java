package com.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.dao.TYudingshijianDao;
import com.model.TYudingShijian;
import org.apache.struts2.ServletActionContext;
import com.dao.TCatelogDAO;
import com.dao.TQchDAO;
import com.dao.TPinglunDAO;
import com.model.TQch;
import com.model.TPinglun;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �����Ϣ����
 *
 * @author Administrator
 */
public class qchAction extends ActionSupport {
    private int id;
    private int catelogId;
    private String qcbh;
    private String area;
    private String jieshao;
    private String fujian;
    private int qianshu;
    private String yudingtiaojian;
    private String message;
    private String path;
    private TCatelogDAO catelogDAO;
    private TQchDAO qchDAO;
    private TPinglunDAO pinglunDAO;
    private int qianshu1;
    private int qianshu2;
    private TYudingshijianDao tYudingshijianDao;

    public TYudingshijianDao gettYudingshijianDao() {
        return tYudingshijianDao;
    }

    public void settYudingshijianDao(TYudingshijianDao tYudingshijianDao) {
        this.tYudingshijianDao = tYudingshijianDao;
    }

    /**
     * �����Ϣ���
     *
     * @author Administrator
     */
    public String qchAdd() {
        TQch qch = new TQch();

        qch.setQcbh(qcbh);//��ݱ��
        qch.setArea(area);
        qch.setJieshao(jieshao);
        qch.setFujian(fujian);
        qch.setQianshu(qianshu);
        qch.setCatelogId(catelogId);
        qch.setDel("no");

        qchDAO.save(qch);
        this.setMessage("�����ɹ�");
        this.setPath("qchMana.action");
        return "succeed";
    }

    /**
     * �����Ϣ�����б�
     *
     * @author Administrator
     */
    public String qchMana() {
        String sql = "from TQch where del='no' order by catelogId";
        List qchList = qchDAO.getHibernateTemplate().find(sql);
        for (int i = 0; i < qchList.size(); i++) {
            TQch qch = (TQch) qchList.get(i);
            qch.setCatelog(catelogDAO.findById(qch.getCatelogId()));
        }
        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("qchList", qchList);
        return ActionSupport.SUCCESS;
    }


    /**
     * �����Ϣɾ��
     *
     * @author Administrator
     */
    public String qchDel() {

        TQch qch = qchDAO.findById(id);
        qch.setDel("yes");
        qchDAO.attachDirty(qch);

        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("msg", "�����ɹ�");
        return "msg";
    }


    /**
     * �����Ϣ�༭
     *
     * @author Administrator
     */
    public String qchEditPre() {

        TQch qch = qchDAO.findById(id);
        qch.setCatelog(catelogDAO.findById(qch.getCatelogId()));

        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("qch", qch);
        return ActionSupport.SUCCESS;

    }

    public String qchEdit() {
        TQch qch = qchDAO.findById(id);

        qch.setQcbh(qcbh);
        qch.setArea(area);
        qch.setJieshao(jieshao);
        qch.setFujian(fujian);
        qch.setQianshu(qianshu);
        qch.setCatelogId(catelogId);


        qchDAO.getHibernateTemplate().update(qch);
        this.setMessage("�����ɹ�");
        this.setPath("qchMana.action");
        return "succeed";


    }

    /**
     * ������ݷ����ѯ��Ϣ
     *
     * @author Administrator
     */
    public String qchByCatelog() {
        String sql = "from TQch where del='no' and catelogId=" + catelogId;
        List qchList = qchDAO.getHibernateTemplate().find(sql);
        for (int i = 0; i < qchList.size(); i++) {
            TQch qch = (TQch) qchList.get(i);
            qch.setCatelog(catelogDAO.findById(qch.getCatelogId()));
        }
        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("qchList", qchList);
        return ActionSupport.SUCCESS;
    }

    /**
     * ǰ̨�ͻ��鿴�����Ϣ
     *
     * @author Administrator
     */
    public String qchDetailQian() {
        String shijian;
        HttpServletRequest request1 = ServletActionContext.getRequest();
        if (request1.getParameter("shijian") != null) {
            shijian = request1.getParameter("shijian");
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            shijian = sdf.format(date);
        }

        Map request = (Map) ServletActionContext.getContext().get("request");
        TQch qch = qchDAO.findById(id);
        String qchId = qch.getQcbh();
        String sql = "from TYudingShijian where   qchId = '" + id + "' and shijian = '" + shijian + "'";
        List yudingList = tYudingshijianDao.getHibernateTemplate().find(sql);
        if(yudingList==null || yudingList.isEmpty()){
            TYudingShijian t = new TYudingShijian(id);
            yudingList = new ArrayList();
            yudingList.add(t);
        }
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
        Date nowTime = null;
        try {
            nowTime = df.parse(df.format(new Date()));

            for (Object t : yudingList) {
                TYudingShijian tysj = (TYudingShijian) t;
                Date endTime1 = df.parse(shijian + " 10:00");
                if (nowAfterEndTime(nowTime, endTime1)) {
                    map.put("time1", "ygq");
                } else if (tysj.getTime1() != null) {
                    map.put("time1", "yyd");
                }

                Date endTime2 = df.parse(shijian + " 11:00");
                if (nowAfterEndTime(nowTime, endTime2)) {
                    map.put("time2", "ygq");
                } else if (tysj.getTime2() != null) {
                    map.put("time2", "yyd");
                }

                Date endTime3= df.parse(shijian + " 12:00");
                if (nowAfterEndTime(nowTime, endTime3)) {
                    map.put("time3", "ygq");
                }else
                if (tysj.getTime3() != null) {
                    map.put("time3", "yyd");
                }

                Date endTime4= df.parse(shijian + " 13:00");
                if (nowAfterEndTime(nowTime, endTime4)) {
                    map.put("time4", "ygq");
                }else
                if (tysj.getTime4() != null) {
                    map.put("time4", "yyd");
                }

                Date endTime5= df.parse(shijian + " 14:00");
                if (nowAfterEndTime(nowTime, endTime5)) {
                    map.put("time5", "ygq");
                }else
                if (tysj.getTime5() != null) {
                    map.put("time5", "yyd");
                }

                Date endTime6= df.parse(shijian + " 15:00");
                if (nowAfterEndTime(nowTime, endTime6)) {
                    map.put("time6", "ygq");
                }else
                if (tysj.getTime6() != null) {
                    map.put("time6", "yyd");
                }

                Date endTime7= df.parse(shijian + " 16:00");
                if (nowAfterEndTime(nowTime, endTime7)) {
                    map.put("time7", "ygq");
                }else
                if (tysj.getTime7() != null) {
                    map.put("time7", "yyd");
                }

                Date endTime8= df.parse(shijian + " 17:00");
                if (nowAfterEndTime(nowTime, endTime8)) {
                    map.put("time8", "ygq");
                }else
                if (tysj.getTime8() != null) {
                    map.put("time8", "yyd");
                }

                Date endTime9= df.parse(shijian + " 18:00");
                if (nowAfterEndTime(nowTime, endTime9)) {
                    map.put("time9", "ygq");
                }else
                if (tysj.getTime9() != null) {
                    map.put("time9", "yyd");
                }

                Date endTime10= df.parse(shijian + " 19:00");
                if (nowAfterEndTime(nowTime, endTime10)) {
                    map.put("time10", "ygq");
                }else
                if (tysj.getTime10() != null) {
                    map.put("time10", "yyd");
                }

                Date endTime11= df.parse(shijian + " 20:00");
                if (nowAfterEndTime(nowTime, endTime11)) {
                    map.put("time11", "ygq");
                }else
                if (tysj.getTime11() != null) {
                    map.put("time11", "yyd");
                }

                Date endTime12= df.parse(shijian + " 21:00");
                if (nowAfterEndTime(nowTime, endTime12)) {
                    map.put("time12", "ygq");
                }else
                if (tysj.getTime12() != null) {
                    map.put("time12", "yyd");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.put("ydstatus", map);
        request.put("sjlist", yudingList);
        request.put("qch", qch);
        request.put("shijian", shijian);
        request.put("id", id);
        return ActionSupport.SUCCESS;
    }

    /**
     * ���ݼ۸��ѯ���
     *
     * @author Administrator
     */
    public String qchSearch() {
        String sql = "from TQch where del='no' and qianshu>? and qianshu<? order by catelogId";
        Object[] c = {qianshu1, qianshu2};
        List qchList = qchDAO.getHibernateTemplate().find(sql, c);
        for (int i = 0; i < qchList.size(); i++) {
            TQch qch = (TQch) qchList.get(i);
            qch.setCatelog(catelogDAO.findById(qch.getCatelogId()));
        }
        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("qchList", qchList);
        return ActionSupport.SUCCESS;
    }


    /**
     * �ͻ�����
     *
     * @author Administrator
     */
    public String pinglunAdd() {
        HttpServletRequest request = ServletActionContext.getRequest();

        TPinglun pinglun = new TPinglun();
        pinglun.setContent(request.getParameter("content"));
        pinglun.setShijian(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        pinglun.setQchId(Integer.parseInt(request.getParameter("qchId")));

        pinglunDAO.save(pinglun);
        request.setAttribute("msg", "���۳ɹ�");
        return "msg";
    }

    /**
     * ����������Ϣ
     *
     * @author Administrator
     */
    public String pinglunAll() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String s = "from TPinglun where qchId=" + Integer.parseInt(request.getParameter("qchId"));
        List pinglunList = pinglunDAO.getHibernateTemplate().find(s);
        request.setAttribute("pinglunList", pinglunList);
        return ActionSupport.SUCCESS;
    }

    /**
     * ������Ϣ����
     *
     * @author Administrator
     */
    public String pinglunMana() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String s = "from TPinglun where qchId=" + Integer.parseInt(request.getParameter("qchId"));
        List pinglunList = pinglunDAO.getHibernateTemplate().find(s);
        request.setAttribute("pinglunList", pinglunList);
        return ActionSupport.SUCCESS;
    }

    /**
     * ɾ������
     *
     * @author Administrator
     */
    public String pinglunDel() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String s = "delete from TPinglun where id=" + Integer.parseInt(request.getParameter("id"));
        pinglunDAO.getHibernateTemplate().bulkUpdate(s);
        request.setAttribute("msg", "ɾ���ɹ�");
        return "msg";
    }

    /**
     * �鿴���������Ϣ����
     *
     * @author Administrator
     */
    public String qchAll() {
        String sql = "from TQch where del='no'";
        List qchList = qchDAO.getHibernateTemplate().find(sql);
        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("qchList", qchList);
        return ActionSupport.SUCCESS;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public TCatelogDAO getCatelogDAO() {
        return catelogDAO;
    }

    public void setCatelogDAO(TCatelogDAO catelogDAO) {
        this.catelogDAO = catelogDAO;
    }

    public int getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(int catelogId) {
        this.catelogId = catelogId;
    }

    public String getQcbh() {
        return qcbh;
    }

    public TPinglunDAO getPinglunDAO() {
        return pinglunDAO;
    }

    public void setPinglunDAO(TPinglunDAO pinglunDAO) {
        this.pinglunDAO = pinglunDAO;
    }

    public void setQcbh(String qcbh) {
        this.qcbh = qcbh;
    }

    public String getFujian() {
        return fujian;
    }

    public void setFujian(String fujian) {
        this.fujian = fujian;
    }

    public int getQianshu1() {
        return qianshu1;
    }

    public void setQianshu1(int qianshu1) {
        this.qianshu1 = qianshu1;
    }

    public int getQianshu2() {
        return qianshu2;
    }

    public void setQianshu2(int qianshu2) {
        this.qianshu2 = qianshu2;
    }

    public String getJieshao() {
        return jieshao;
    }

    public void setJieshao(String jieshao) {
        this.jieshao = jieshao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TQchDAO getQchDAO() {
        return qchDAO;
    }

    public void setQchDAO(TQchDAO qchDAO) {
        this.qchDAO = qchDAO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getQianshu() {
        return qianshu;
    }

    public void setQianshu(int qianshu) {
        this.qianshu = qianshu;
    }

    public String getYudingtiaojian() {
        return yudingtiaojian;
    }

    public void setYudingtiaojian(String yudingtiaojian) {
        this.yudingtiaojian = yudingtiaojian;
    }

    /**
     * �ж�ʱ���Ƿ���ʱ�����
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        //���õ�ǰʱ��
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //���ÿ�ʼʱ��
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        //���ý���ʱ��
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        //���ڿ�ʼʱ��֮�󣬺ͽ���ʱ��֮ǰ���ж�
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �ж�ʱ���Ƿ���ʱ�����
     *
     * @param nowTime
     * @param endTime
     * @return
     */
    public static boolean nowAfterEndTime(Date nowTime, Date endTime) {
        //���õ�ǰʱ��
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //���ý���ʱ��
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        //���ڿ�ʼʱ��֮�󣬺ͽ���ʱ��֮ǰ���ж�
        if (date.after(end)) {
            return true;
        } else {
            return false;
        }
    }

}
