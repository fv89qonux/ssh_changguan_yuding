package com.action;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dao.TYudingshijianDao;
import com.model.*;
import org.apache.struts2.ServletActionContext;

import com.dao.TQchDAO;
import com.dao.TUserDAO;
import com.dao.TYudingDAO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.liuService;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;

/**
 * Ԥ������
 *
 * @author Administrator
 */
public class yudingAction extends ActionSupport {
    private int id;
    private int qchId;
    private int tianshu;
    private String yudingzheTel;
    private TYudingshijianDao yudingshijianDAO;
    private TYudingDAO yudingDAO;
    private TQchDAO qchDAO;
    private TUserDAO userDAO;
    // Ԥ����ʱ���
    private String ydsj;
    private String yldh;
    // Ԥ������
    private String shijian;
    private String field1;

    private String field2;
    private String field3;

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public TYudingshijianDao getYudingshijianDAO() {
        return yudingshijianDAO;
    }

    public void setYudingshijianDAO(TYudingshijianDao yudingshijianDAO) {
        this.yudingshijianDAO = yudingshijianDAO;
    }

    public String getYdsj() {
        return ydsj;
    }

    public void setYdsj(String ydsj) {
        this.ydsj = ydsj;
    }

    public String getYldh() {
        return yldh;
    }

    public void setYldh(String yldh) {
        this.yldh = yldh;
    }

    public String getRequestBody(ActionContext ctx, HttpServletRequest request) {
        try {
            InputStream inputStream = request.getInputStream();
            String strMessage = "";
            StringBuffer buff = new StringBuffer();
            BufferedReader bufferReader = new BufferedReader(new
                    InputStreamReader(inputStream, "utf-8"));
            while ((strMessage = bufferReader.readLine()) != null) {
                buff.append(strMessage);
            }
            bufferReader.close();
            inputStream.close();
            return buff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void heyan() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            TYuding yuding = yudingDAO.findById(id);
            if(yuding.getPaystatus().equals("0")){
                out.write("nopay");
                return;
            }
            yuding.setHystatus("1");
            yuding.setJingdu("1");
            yudingDAO.attachDirty(yuding);
            out.write("success");
        } catch (IOException e) {
            e.printStackTrace();
            out.write("failed");
        }
    }

    /**
     * Ԥ��
     *
     * @author Administrator
     */
    public void yudingAdd() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Map session = (Map) ActionContext.getContext().getSession();
        TUser user = (TUser) session.get("user");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (isIn30days(shijian)) {
            out.write("failed0");
            return;
        }
        // ����Ԥ��ʱ��
        JSONObject shijianJSONObject = JSONObject.parseObject(ydsj);
        // ������ͬһ��ʱ���Ԥ���������
        List<TYuding> list = yudingDAO.findByPropertys(user.getUserId(), shijian);
        if (list != null && !list.isEmpty()) {
            for (TYuding t : list) {
                String sjd = t.getSjdlist();
                JSONObject hasydSjd = JSONObject.parseObject(sjd);
                for (String key : hasydSjd.keySet()) {
                    if (shijianJSONObject.containsKey(key)) {
                        out.write("failed1");
                        return;
                    }
                }
            }
        }

        // Ԥ����ĳ��  ĳ��û��ȥ�����첻���ٷ���Ԥ��
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowStr = sdf.format(nowdate);
        // ��ѯ���û��Ľ��ն���
        List<TYuding> list2 = yudingDAO.findByPropertys(user.getUserId(), nowStr);
        if (null != list2 && !list2.isEmpty()) {
            for (TYuding yuding : list2) {
                if (yuding.getJingdu().equals("1")) {
                    continue;
                } else {
                    String sjd = yuding.getSjdlist();
                    JSONObject jsdJSONObject = JSONObject.parseObject(sjd);
                    for (String key : jsdJSONObject.keySet()) {
                        String value = jsdJSONObject.getString(key);
                        String valueArray[] = value.split("-");
                        String endTime = valueArray[1];
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//�������ڸ�ʽ
                        Date endTime1 = null;
                        Date nowTime = null;
                        try {
                            endTime1 = df.parse(endTime);
                            nowTime = df.parse(df.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // ��ǰʱ���Ѿ������Ѿ�Ԥ��������ʱ��
                        if (nowAfterEndTime(nowTime, endTime1)) {
                            out.write("failed2");
                            return;
                        }
                    }
                }
            }
        }
        TYudingShijian yudingshijian = JSON.toJavaObject(shijianJSONObject, TYudingShijian.class);
        yudingshijian.setQchId(qchId);
        yudingshijian.setShijian(shijian);
        TYudingShijian tt = yudingshijianDAO.findByQchid(qchId, shijian);
        if (null == tt) {
            yudingshijianDAO.save(yudingshijian);
        } else {
            yudingshijian.setId(tt.getId());
            // update
            yudingshijianDAO.updateshijian(yudingshijian);
        }
        ActionContext ctx = ActionContext.getContext();
        TYuding yuding = new TYuding();
        yuding.setUserId(user.getUserId());
        yuding.setQchId(qchId);
        yuding.setYudingzheTel(yudingzheTel);
        yuding.setShijian(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        yuding.setDel("no");
        yuding.setYdrq(shijian);
        yuding.setSjdlist(shijianJSONObject.toJSONString());
        yuding.setPaystatus("0");
        yuding.setHystatus("0");
        yuding.setJingdu("0");
        yudingDAO.save(yuding);
        out.write("success");
    }


    // ֧������
    public void payOrder() {
        TYuding yuding = yudingDAO.findById(id);
        yuding.setPaystatus("1");
        yudingDAO.attachDirty(yuding);

        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("msg", "�����ɹ�");

    }

    /**
     * ����Ա����Ԥ����Ϣ
     *
     * @author Administrator
     */
    public String yudingMana() {
        String sql = "from TYuding where del='no'";
        List yudingList = yudingDAO.getHibernateTemplate().find(sql);
        for (int i = 0; i < yudingList.size(); i++) {
            TYuding yuding = (TYuding) yudingList.get(i);
            yuding.setUser(userDAO.findById(yuding.getUserId()));
            yuding.setQch(qchDAO.findById(yuding.getQchId()));
        }
        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("yudingList", yudingList);
        return ActionSupport.SUCCESS;
    }

    /**
     * ��Ա�����Լ���Ԥ����Ϣ
     *
     * @author Administrator
     */
    public String myYudingMana() {

        Map request = (Map) ServletActionContext.getContext().get("request");
        Map session = (Map) ActionContext.getContext().getSession();
        TUser user;
        if (null != field2 && !field2.isEmpty()) {
            // ����Ԥ����
            TYuding yuding = yudingDAO.findById(Integer.parseInt(field2));
            yuding.setPaystatus("1");
            yuding.setHycode(field3);
            yudingDAO.attachDirty(yuding);
        }
        user = (TUser) session.get("user");
        int userId = user.getUserId();
        String sql = "from TYuding where del='no' and userId=" + userId;
        List yudingList = yudingDAO.getHibernateTemplate().find(sql);
        for (int i = 0; i < yudingList.size(); i++) {
            TYuding yuding = (TYuding) yudingList.get(i);
            yuding.setUser(userDAO.findById(yuding.getUserId()));
            yuding.setQch(qchDAO.findById(yuding.getQchId()));
        }
        request.put("yudingList", yudingList);
        return ActionSupport.SUCCESS;
    }

    /**
     * ɾ��Ԥ��
     *
     * @author Administrator
     */
    public String yudingDel() {
        TYuding yuding = yudingDAO.findById(id);
        yuding.setDel("yes");
        Integer qchId = yuding.getQchId();
        String shijian = yuding.getSjdlist();
        String ydrq = yuding.getYdrq();
        TYudingShijian tYudingShijian = yudingshijianDAO.findByQchid(qchId,ydrq);
        if(null != tYudingShijian){
            JSONObject shijianObject = JSONObject.parseObject(shijian);
            for(String key:shijianObject.keySet()){
                if(key.equals("time1")){
                    tYudingShijian.setTime1(null);
                    continue;
                }
                if(key.equals("time2")){
                    tYudingShijian.setTime2(null);
                    continue;
                }
                if(key.equals("time3")){
                    tYudingShijian.setTime3(null);
                    continue;
                }
                if(key.equals("time4")){
                    tYudingShijian.setTime4(null);
                    continue;
                }
                if(key.equals("time5")){
                    tYudingShijian.setTime5(null);
                    continue;
                }
                if(key.equals("time6")){
                    tYudingShijian.setTime6(null);
                    continue;
                }
                if(key.equals("time7")){
                    tYudingShijian.setTime7(null);
                    continue;
                }
                if(key.equals("time8")){
                    tYudingShijian.setTime8(null);
                    continue;
                }
                if(key.equals("time9")){
                    tYudingShijian.setTime9(null);
                    continue;
                }
                if(key.equals("time10")){
                    tYudingShijian.setTime10(null);
                    continue;
                }
                if(key.equals("time11")){
                    tYudingShijian.setTime11(null);
                    continue;
                }
                if(key.equals("time12")){
                    tYudingShijian.setTime12(null);
                    continue;
                }
            }
            yudingshijianDAO.updateshijianTOnull(tYudingShijian);
        }
        yudingDAO.attachDirty(yuding);

        Map request = (Map) ServletActionContext.getContext().get("request");
        request.put("msg", "�����ɹ�");
        return "msg";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQchId() {
        return qchId;
    }

    public void setQchId(int qchId) {
        this.qchId = qchId;
    }

    public int getTianshu() {
        return tianshu;
    }

    public void setTianshu(int tianshu) {
        this.tianshu = tianshu;
    }

    public TQchDAO getQchDAO() {
        return qchDAO;
    }

    public TUserDAO getUserDAO() {
        return userDAO;
    }


    public void setUserDAO(TUserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public void setQchDAO(TQchDAO qchDAO) {
        this.qchDAO = qchDAO;
    }

    public TYudingDAO getYudingDAO() {
        return yudingDAO;
    }

    public void setYudingDAO(TYudingDAO yudingDAO) {
        this.yudingDAO = yudingDAO;
    }


    public String getYudingzheTel() {
        return yudingzheTel;
    }


    public void setYudingzheTel(String yudingzheTel) {
        this.yudingzheTel = yudingzheTel;
    }


    /**
     * ��֤�ַ���ʱ�䣬�Ƿ���30����
     *
     * @param str
     * @return
     */
    public static boolean isIn30days(String str) {
        boolean convertSuccess = true;
        //ʱ���ʽ����
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //��ȡ��ǰʱ������--nowDate
        String nowDate = format.format(new Date());
        //��ȡ30����ʱ������--minDate
        Calendar calc = Calendar.getInstance();
        calc.add(Calendar.DAY_OF_MONTH, +30);
        String maxDate = format.format(calc.getTime());
        try {
            //����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
            format.setLenient(false);
            //��ȡ�ַ���ת�����ʱ��--strDate
            String strDate = format.format(format.parse(str));
            //�жϴ���STRʱ�䣬�Ƿ��ڵ�ǰʱ��֮ǰ������30������֮��-----���Ե�ʱ���ӡ������
//			System.out.println("nowDate.compareTo(strDate):"+ nowDate.compareTo(strDate));
//			System.out.println("strDate.compareTo(minDate):"+ strDate.compareTo(minDate));
            if (strDate.compareTo(maxDate) >= 0) {
                convertSuccess = true;
            } else {
                convertSuccess = false;
            }
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
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
        // ��������ʱ��
        if (date.after(end)) {
            return true;
        } else {
            return false;
        }
    }
}
